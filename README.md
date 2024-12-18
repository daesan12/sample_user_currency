# 환전 신청시스템
- 고객은 여러 통화를교환할 수있다.
- 사용자는 교환 요청을 보고 관리할 수 있다.
- 환율에 따라 자동으로 금액환산
  
# 기능
*사용자관리*
등록 조회 삭제

*통화 관리*
통화 세부정보 조회 및 ,환율 관리,교환요청상태 변경 ex:normal->cancelled or cancelled->nomal

# 기술 스택
언어: 자바 17+
프레임워크: 스프링 부트
데이터베이스: MySQL

#  API 명세

| 기능                                              | 메서드  | URL                         | 요청 데이터                                                                                             | 응답 데이터                                                                                                                                                                                                                  | 상태 코드                            | 비고                                                                                                                   |
|:-------------------------------------------------|:------:|:---------------------------|:-------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:------------------------------------|:---------------------------------------------------------------------------------------------------------------------|
| **회원가입**                                      | POST   | /users                     | `{ "email": "abcdefg@naver.com", "name": "장대산" }`                                                  | `{ "id": 1, "name": "장대산", "email": "abcdefg@naver.com", "createdAt": "2024-11-28T18:20:13.3778169", "modifiedAt": "2024-11-28T18:20:13.3778169" }`                                  | 201 Created<br>400 Bad Request       | - 성공 시 201 반환<br>- 실패 시 400 반환                                                                                       |
| **사용자 단건 조회**                              | GET    | /users/{id}                |                                                                                                        | `{ "id": 1, "name": "장대산", "email": "abcdefg@naver.com", "createdAt": "2024-11-28T18:20:13.3778169", "modifiedAt": "2024-11-28T18:20:13.3778169" }`                                  | 200 OK<br>400 Bad Request             | - 성공 시 200 반환<br>- 실패 시 400 반환                                                                                       |
| **사용자 삭제**                                   | DELETE | /users/{id}                |                                                                                                        |                                                                                                                                                                                                                            | 200 OK<br>400 Bad Request             | - 성공 시 200 반환<br>- 실패 시 400 반환                                                                                       |
| **통화 추가**                                     | POST   | /currencies                | `{ "currencyName": "CNY", "exchangeRate": 192.53, "symbol": "元" }`                                   | `{ "id": 5, "currencyName": "CNY", "exchangeRate": 192.53, "symbol": "元", "createdAt": "2024-11-28T18:58:58.0759391", "modifiedAt": "2024-11-28T18:58:58.0759391" }`                   | 201 Created<br>400 Bad Request       | - 성공 시 201 반환<br>- 실패 시 400 반환                                                                                       |
| **통화 단건 조회**                                | GET    | /currencies/{id}           |                                                                                                        | `{ "id": 5, "currencyName": "CNY", "exchangeRate": 192.53, "symbol": "元", "createdAt": "2024-11-28T18:58:58.075939", "modifiedAt": "2024-11-28T18:58:58.075939" }`                    | 200 OK<br>400 Bad Request             | - 성공 시 200 반환<br>- 실패 시 400 반환                                                                                       |
| **통화 전체 조회**                                | GET    | /currencies                |                                                                                                        | `[ { "id": 1, "currencyName": "USD", "exchangeRate": 900, "symbol": "$", "createdAt": "2024-11-28T17:12:36.004116", "modifiedAt": "2024-11-28T17:12:36.004116" }, ... ]`                | 200 OK                                | - 데이터 없을 시 빈 배열 반환                                                                                               |
| **환전 요청 생성**                               | POST   | /exchanges                 | `{ "amountInKrw": 10000, "toCurrencyId": 5, "toUserId": 5 }`                                          |                                                                                                                                                                                                                            | 200 OK<br>400 Bad Request             | - 성공 시 200 반환<br>- 실패 시 400 반환                                                                                       |
| **사용자 환전 요청 조회**                         | GET    | /exchanges/{userId}        |                                                                                                        | `[ { "id": 6, "toCurrencyId": 2, "toUserId": 5, "amountInKrw": 10000.00, "formattedAmountAfterExchange": "1086.96円", "status": "normal", "createdAt": "2024-11-28T18:54:59.468681", "updatedAt": "2024-11-28T18:54:59.468681" } ]` | 200 OK<br>400 Bad Request             | - 성공 시 200 반환<br>- 실패 시 400 반환                                                                                       |
| **환전 요청 상태 변경**                         | PATCH  | /exchanges/{id}            |                                                                                                        | `{ "id": 7, "toCurrencyId": 2, "toUserId": 5, "amountInKrw": 10000.00, "formattedAmountAfterExchange": "1086.96円", "status": "cancelled", "createdAt": "2024-11-28T18:55:00.284697", "updatedAt": "2024-11-28T19:01:12.556406" }` | 200 OK<br>400 Bad Request             | - `status`가 `cancelled`이면 `normal`로 변경, 반대의 경우도 동일                                                                 |
| **모든 사용자 환전 상태 그룹화 조회**            | GET    | /exchanges/group           |                                                                                                        | `[ { "userId": 4, "count": 5, "totalAmountInKrw": 50000.00 }, { "userId": 5, "count": 3, "totalAmountInKrw": 30000.00 } ]`                                                              | 200 OK<br>400 Bad Request             | - 성공 시 200 반환<br>- 실패 시 400 반환                                                                                       |

---
![환전ERD](https://github.com/user-attachments/assets/b8cd5e12-5407-4989-b46c-0094177ac536)


# 테이블 생성 쿼리 
- User 테이블 생성
```sql
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(10) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);
```
- Currency 테이블 생성
```sql
CREATE TABLE currency (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          currency_name VARCHAR(3) NOT NULL,
                          exchange_rate DECIMAL(19, 4) NOT NULL CHECK (exchange_rate > 0),
                          symbol CHAR(1) NOT NULL,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);
```
- Exchange 테이블 생성
```sql
CREATE TABLE exchange (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          to_user_id BIGINT NOT NULL,
                          to_currency_id BIGINT NOT NULL,
                          amount_in_krw DECIMAL(19, 4) NOT NULL CHECK (amount_in_krw >= 1),
                          amount_after_exchange DECIMAL(19, 4) NOT NULL,
                          status VARCHAR(255) DEFAULT 'normal',
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
                          CONSTRAINT fk_exchange_user FOREIGN KEY (to_user_id) REFERENCES user (id) ON DELETE CASCADE,
                          CONSTRAINT fk_exchange_currency FOREIGN KEY (to_currency_id) REFERENCES currency (id) ON DELETE CASCADE
);
```
