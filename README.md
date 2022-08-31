# 개요

- [스프링 DB 1편 - 데이터 접근 핵심 원리](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-db-1)
- [스프링 DB 2편 - 데이터 접근 활용 기술](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-db-2)
- 배운것들을 정리한 Repository입니다.

# 스프링 시작

- **스프링 시작**
    - [https://start.spring.io/](https://start.spring.io/)


- **Spring Package Setting**
    - `Java Version` : 11
    - `Project` : `Gradle Project`
    - `Packaging`
        - `Servlet` : `War`
        - `Spring Boot` : `Jar`
    - `Dependencies`
        - `JDBC` : `JDBC`, `H2`, `Lombok`


- **IDE Setting**
    - **Lombok Setting**
        - `setting` -> `Bulid, Execution, Depolyment` -> `Annotation Processors` -> `Enable annotation processing ✔`
    - **Encoding Setting**
        - `setting` -> `File Encodings` -> `Global Encoding` -> `UTF-8`
        - `setting` -> `File Encodings` -> `Properties Files` -> `Default encoding for properties files` -> `UTF-8`
    - **Dependencies**
      - `Lombok`
          - `testCompileOnly 'org.projectlombok:lombok'`
          - `testAnnotationProcessor 'org.projectlombok:lombok'`


- **H2 DataBase**
  - **Download**
    - [H2 DataBase Download URL](https://www.h2database.com)
  - **Setting**
    - `JDBC URL` : `jdbc:h2:tcp://localhost/~/test`
    - `User` : `sa`
    
# 공부

<details>
<summary><h3>JDBC 이해</h3></summary>

- [JDBC 이해](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/1#issue-1351985055)
- JDBC 표준 인터페이스
- 데이터 베이스 연결
- JDBC DriverManager 연결 이해
- JDBC 개발

</details>

<details>
<summary><h3>커넥션풀과 데이터소스 이해</h3></summary>

- [커넥션 풀 이해](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/2#issue-1352090583)
- DataSource 이해

</details>

<details>
<summary><h3>트랜잭션 이해</h3></summary>

- [트랜잭션 - 개념 이해](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/3#issue-1352297482)
- JDBC 표준 인터페이스
- 데이터베이스 연결 구조와 DB 세션
- 트랜잭션 - DB
- DB 락 - 개념 이해
- 트랜잭션 적용

</details>

<details>
<summary><h3>실전 트랜잭션</h3></summary>

- [트랜잭션 추상화](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/4#issue-1353325939)
- 트랜잭션 동기화
- 트랜잭션 매니저
- 트랜잭션 템플릿
- 트랜잭션 AOP 이해
- 스프링 부트 - 자동 리소스 등록

</details>

<details>
<summary><h3>예외계층</h3></summary>

- [예외계층](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/5#issue-1353326049)
- 예외 기본 규칙

- 체크 예외 기본 이해
- 언체크 예외 기본 이해
- 체크 예외 활용
- 언체크 예외 활용
- 예외 포함과 스택 트레이스

</details>

<details>
<summary><h3>예외처리, 반복</h3></summary>

- [체크 예외와 인터페이스](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/6#issue-1353326178)
- 예외 기본 규칙
- 데이터 접근 예외
- 스프링 예외 추상화 이해
- JDBC 반복 문제 해결 - JdbcTemplate

</details>

<details>
<summary><h3>데이터 접근 기술 진행 방식</h3></summary>

- [데이터 접근 기술 방식](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/7#issue-1355366972)
- 데이터 베이스 식별자

</details>

<details>
<summary><h3>스프링 JdbcTemplate</h3></summary>

- [JdbcTemplate 소개](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/8#issue-1355367615)
- JdbcTemplate 구성
- JdbcTemplate 이름 지정 파라미터
- SimpleJdbcInsert
- JdbcTeamplate 기능 정리

</details>

<details>
<summary><h3>테스트 - 데이터베이스 연동</h3></summary>

- [테스트 데이터 베이스](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/9#issue-1355368403)
- 테스트 - 데이터 롤백
- @Transactional
- 임베디드 모드 DB
- 스프링 부트와 임베디드 모드

</details>


<details>
<summary><h3>MyBatis</h3></summary>

- [MyBatis 소개](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/10#issue-1357032099)
- MyBatis 설정
- MyBatis 적용
- MyBatis 분석
- MyBatis 기능 - 동적 쿼리
- MyBatis 기능 - 기타 기능

</details>

<details>
<summary><h3>JPA</h3></summary>

- [ORM(Object-relational mapping : 객체 관계 매핑](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/11#issue-1357032562)
- JPA 소개
- JPA 적용
- JPA 예외 변환

</details>

<details>
<summary><h3>스프링 데이터 JPA, QueryDsl</h3></summary>

- [스프링 데이터 JPA 기능](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/12#issue-1357033490)
- 스프링 데이터 JPA 적용
- QueryDsl

</details>