## 사용 기술
- Spring WebFlux
- Java 17
- Caffeine Cache

## 핵심 문제 해결 전략
- 동시에 여러 사이트를 조회
  - AsyncCache를 활용
  - 캐시를 조회했을 때 비어있을 경우, AsyncCache 내부적으로 별도의 쓰레드를 통해 캐시 값을 생성하고 채운다.
- 크롤링 데이터 정렬 및 중복 제거
  - 대문자, 소문자, 숫자를 나타내는 배열을 생성
  - 문자의 아스키 코드를 인덱스로 활용  
  - 크롤링 데이터에서 특정 문자가 나타났는지를 배열을 통해서 표시 -> 자연스럽게 중복 제거 & 정렬이 됨  

## API 인터페이스

크롤링 요청 API:

| 엔드포인트 | HTTP 메소드 | 요청 파라미터 | 응답 형식        |
| ---------- | ----------- | ------------- | ---------------- |
| /v1/crawl  | GET         | 없음          | application/json |

응답 코드:

| 응답 코드               | 설명                            |
| ----------------------- | ------------------------------- |
| 200 OK                  | 성공적으로 처리된 경우          |
| 503 SERVICE_UNAVAILABLE | 요청 처리 중 오류가 발생한 경우 |

응답 데이터:

| 필드명 | 데이터 타입 | 설명                               |
| ------ | ----------- | ---------------------------------- |
| ok     | boolean     | 성공 여부                    |
| result | string      | 크롤링한 데이터를 합친 결과 문자열 혹은 에러 메시지 |


성공 응답 예시:

```json code
HTTP/1.1 200 OK
Content-Type: application/json

{
    "ok": true,
    "result": "크롤링한 데이터를 합친 결과 문자열"
}
```

실패 응답 예시:

```json code
HTTP/1.1 503 SERVICE_UNAVAILABLE
Content-Type: application/json

{
    "ok": false,
    "result": "Failed to crawl a website(https://shop.hyundai.coms)"
}
```