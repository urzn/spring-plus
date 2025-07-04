package org.example.expert.domain.search.repository;

import org.example.expert.domain.search.dto.SearchResponse;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface SearchRepository {
    Page<SearchResponse> searchTodos(
            String keyword,
            String nickname,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable);
}
