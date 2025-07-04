package org.example.expert.domain.search.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.search.dto.SearchResponse;
import org.example.expert.domain.search.repository.SearchRepository;
import org.example.expert.domain.search.repository.SearchRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchRepository searchRepository;

    @GetMapping("/todos/search")
    public ResponseEntity<Page<SearchResponse>> getTodoByTitle(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ){

        if(startDate != null && endDate != null && startDate.isAfter(endDate)){
            throw new InvalidRequestException("검색 기간의 시작이 끝 이후일 수 없습니다.");
        }

        return ResponseEntity.ok(searchRepository.searchTodos(keyword, nickname, startDate, endDate, pageable));
    }
}
