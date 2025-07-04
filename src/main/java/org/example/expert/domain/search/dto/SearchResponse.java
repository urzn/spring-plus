package org.example.expert.domain.search.dto;

import lombok.Getter;

@Getter
public class SearchResponse {
    private final Long id;
    private final String title;
    private final Long managerNum;
    private final Long commentNum;

    public SearchResponse(Long id, String title, Long managerNum, Long commentNum){
        this.id = id;
        this.title = title;
        this.managerNum = managerNum;
        this.commentNum = commentNum;
    }
}
