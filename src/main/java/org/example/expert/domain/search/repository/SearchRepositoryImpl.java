package org.example.expert.domain.search.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.search.dto.SearchResponse;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import java.time.LocalDate;
import java.util.List;

@Repository
public class SearchRepositoryImpl implements SearchRepository{

    private final JPAQueryFactory queryFactory;

    public SearchRepositoryImpl(EntityManager em){ this.queryFactory = new JPAQueryFactory(em); }

    @Override
    public Page<SearchResponse> searchTodos(String keyword, String nickname, LocalDate startDate, LocalDate endDate, Pageable pageable) {

        QTodo todo = QTodo.todo;
        QManager manager = QManager.manager;
        QComment comment = QComment.comment;
        QUser managerUser = new QUser("managerUser");
        BooleanBuilder where = new BooleanBuilder();

        if (keyword != null && !keyword.isBlank()) {
            where.and(todo.title.contains(keyword));
        }

        if (startDate != null) {
            where.and(todo.createdAt.goe(startDate.atStartOfDay()));
        }

        if (endDate != null) {
            where.and(todo.createdAt.loe(endDate.plusDays(1).atStartOfDay()));
        }

        if (nickname != null && !nickname.isBlank()) {
            where.and(manager.user.nickname.contains(nickname));
        }

        // content 쿼리
        List<SearchResponse> content = queryFactory
                .select(Projections.constructor(
                        SearchResponse.class,
                        todo.id,
                        todo.title,
                        manager.id.countDistinct(),
                        comment.id.countDistinct()
                ))
                .from(todo)
                .leftJoin(manager).on(manager.todo.id.eq(todo.id))
                .leftJoin(manager.user, managerUser)
                .leftJoin(comment).on(comment.todo.id.eq(todo.id))
                .where(where)
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // count 쿼리
        long total = queryFactory
                .select(todo.id.countDistinct())
                .from(todo)
                .leftJoin(manager).on(manager.todo.id.eq(todo.id))
                .leftJoin(manager.user, managerUser)
                .where(where)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
