# SPRING PLUS

# Level 3 일정 검색 API 명세서
Request
GET /todos
Authorization: Bearer {access_token}
RequestParam

| 키          | 데이터 타입 | 설명                    | 필수 여부 |
|-------------|-------------|-------------------------|-----------|
| `weather`   | `String`    | 날씨                    | N         |
| `startDate` | `LocalDate` | 수정일 검색 시작 날짜   | N         |
| `finishDate`   | `LocalDate` | 수정일 검색 끝 날짜     | N         |

Request 예시 URL : /todos?weather=Light Drizzle&startDate=2025-05-01&finishDate=2025-06-02

Response

| 키            | 데이터 타입 | 설명             |
|---------------|-------------|------------------|
| `id`          | `Long`      | 일정 ID          |
| `title`       | `String`    | 일정 제목        |
| `contents`    | `String`    | 일정 내용        |
| `weather`     | `String`    | 날씨             |
| `user`        | `User`      | 생성 유저        |
| `createdAt`   | `LocalDate` | 생성일           |
| `modifiedAt`  | `LocalDate` | 수정일           |

예시 Response
```json
{
    "content": [
        {
            "id": 1,
            "title": "title",
            "contents": "contents",
            "weather": "Light Drizzle",
            "user": {
                "id": 1,
                "email": "batman1@naver.com"
            },
            "createdAt": "2025-06-26T11:21:36.740724",
            "modifiedAt": "2025-06-26T11:21:36.740724"
        }
    ],
    "page": {
        "size": 10,
        "number": 0,
        "totalElements": 1,
        "totalPages": 1
    }
}
```
# Level 10 검색 API 명세서

Request
GET /todos/search
Authorization: Bearer {access_token}
RequestParam

| 키         | 데이터 타입 | 설명                        | 필수 여부 |
|------------|-------------|-----------------------------|-----------|
| `keyword`  | `String`    | 제목 검색 키워드            | N         |
| `nickname` | `String`    | 담당자 닉네임 검색          | N         |
| `startDate`| `LocalDate` | 생성일 검색 시작 날짜       | N         |
| `endDate`  | `LocalDate` | 생성일 검색 끝 날짜         | N         |

Request 예시 URL : /todos/search?keyword=ti&nickname=bat&startDate=2025-05-01&endDate=2025-06-02

Response

| 키            | 데이터 타입 | 설명           |
|---------------|-------------|----------------|
| `id`          | `Long`      | 일정 ID        |
| `title`       | `String`    | 일정 제목      |
| `managerNum`  | `Long`      | 담당자 수      |
| `commentNum`  | `Long`      | 댓글 수        |

예시 Response
```json
{
  "id" : 1,
  "title" : "title",
  "managerNum" : 1,
  "commentNum" : 2
}
```