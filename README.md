# 개요

- [스프링 DB 1편 - 데이터 접근 핵심 원리](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-db-1)를 통해 배운것들을 정리한 Repository입니다.

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
<summary><h3>JDBC 이해</h3></summary>

- [트랜잭션 - 개념 이해](https://github.com/WooJinDeve/Spring-DB-Connection-Study/issues/3#issue-1352297482)
- JDBC 표준 인터페이스
- 데이터베이스 연결 구조와 DB 세션
- 트랜잭션 - DB
- DB 락 - 개념 이해
- 트랜잭션 적용

</details>