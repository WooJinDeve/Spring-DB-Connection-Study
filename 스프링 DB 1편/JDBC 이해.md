# JDBC 이해

## JDBC 이해

![Untitled](https://user-images.githubusercontent.com/106054507/190153235-f379194b-d0db-4823-be45-7c24ac5f16b4.png)

![Untitled 1](https://user-images.githubusercontent.com/106054507/190153245-75583461-20ae-48e3-a60f-0ca2fb74ba92.png)


1. 커넥션 연결: 주로 `TCP/IP`를 사용해서 커넥션을 연결한다.
2. SQL 전달: 애플리케이션 서버는 DB가 이해할 수 있는 `SQL`을 연결된 커넥션을 통해 DB에 전달한다.
3. 결과 응답: `DB`는 전달된 `SQL`을 수행하고 그 결과를 응답한다. 애플리케이션 서버는 응답 결과를 활용한다

![Untitled 2](https://user-images.githubusercontent.com/106054507/190153258-62325931-838a-4294-90c6-322d6379c6d3.png)


- 문제는 각각의 데이터베이스마다 커넥션을 연결하는 방법, SQL을 전달하는 방법, 그리고 결과를 **응답 받는 방법이 모두 다르다는** 점이다.
- **여기에는 2가지 큰 문제가 있다**
    1. 데이터베이스를 다른 종류의 데이터베이스로 변경하면 애플리케이션 서버에 개발된 데이터베이스 사용 코드도 함께 변경해야 한다.
    2. 개발자가 각각의 데이터베이스마다 커넥션 연결, SQL 전달, 그리고 그 결과를 응답 받는 방법을 새로 학습해야 한다.

## **JDBC 표준 인터페이스**

- `JDBC(Java Database Connectivity)`는 자바에서 데이터베이스에 접속할 수 있도록 하는 자바 `API`다.

![Untitled 3](https://user-images.githubusercontent.com/106054507/190153276-669de677-9e79-4709-bc05-beb3e9a3e901.png)


- `java.sql.Connection` - 연결
- `java.sql.Statement` - SQL을 담은 내용
- `java.sql.ResultSet` - SQL 요청 응답

![Untitled 4](https://user-images.githubusercontent.com/106054507/190153291-962eb066-45fe-4711-942f-b94a23e9c43f.png)


- **JDBC의 등장으로 다음 2가지 문제가 해결되었다.**
    - 데이터베이스를 다른 종류의 데이터베이스로 변경하면 애플리케이션 서버의 데이터베이스 사용 코드도 함께 변경해야하는 문제
        - 애플리케이션 로직은 이제 `JDBC 표준 인터페이스`에만 의존한다. 따라서 데이터베이스를 다른 종류의 데이터베이스로 변경하고 싶으면 `JDBC 구현 라이브러리`만 변경하면 된다. 따라서 다른 종류의 데이터베이스로 변경해도 애플리케이션 서버의 사용 코드를 그대로 유지할 수 있다.
    - 개발자가 각각의 데이터베이스마다 `커넥션 연결`, `SQL 전달`, 그리고 그 결과를 `응답 받는 방법을 새로 학습`해야하는 문제
        - 개발자는 JDBC 표준 인터페이스 사용법만 학습하면 된다. 한번 배워두면 수십개의 데이터베이스에 모두 동일하게 적용할 수 있다.
        
- **JDBC와 최신 데이터 접근 기술**
    - JDBC는 1997년에 출시될 정도로 오래된 기술이고, 사용하는 방법도 복잡하다. 그래서 최근에는 `JDBC`를 직접 사용하기 보다는 `JDBC`를 편리하게 사용하는 다양한 기술이 존재한다. 대표적으로 `SQL Mapper`와 `ORM` 기술`로 나눌 수 있다.

![Untitled 5](https://user-images.githubusercontent.com/106054507/190153308-47fe2d4f-311d-4e82-b69f-7b6cc3648d2c.png)


- **SQL Mapper**
    - **장점**
        - `JDBC`를 편리하게 사용하도록 도와준다.
        - `SQL` 응답 결과를 객체로 편리하게 변환해준다.
        - `JDBC`의 반복 코드를 제거해준다.
    - **단점**
        - 개발자가 `SQL`을 직접 작성해야한다.
        - 대표 기술: 스프링 `JdbcTemplate`, `MyBatis`

![Untitled 6](https://user-images.githubusercontent.com/106054507/190153322-170557b8-85a2-4265-b5e7-6eeda35b5ec3.png)


- **ORM 기술**
    - `ORM`은 객체를 관계형 데이터베이스 테이블과 매핑해주는 기술이다. 이 기술 덕분에 개발자는 반복적인 `SQL`을 직접 작성하지 않고, `ORM` 기술이 개발자 대신에 `SQL`을 동적으로 만들어 실행해준다. 추가로 각각의 데이터베이스마다 다른 `SQL`을 사용하는 문제도 중간에서 해결해준다.
    - 대표 기술: `JPA`, `하이버네이트`, `이클립스링크`
    - `JPA`는 자바 진영의 `ORM` 표준 인터페이스이고, 이것을 구현한 것으로 하이버네이트와 이클립스 링크 등의 구현 기술이 있다.
    

## 데이터 베이스 연결

```java
public abstract class ConnectionConst {
    public static final String URL = "jdbc:h2:tcp://localhost/~/test/";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}

@Slf4j
public class DBConnectionUtil {

    public static Connection getConnection(){
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connection = {}, class = {}", connection, connection.getClass());
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
```

- 데이터베이스에 연결하려면 `JDBC`가 제공하는 `DriverManager.getConnection(..)` 를 사용하면 된다. 이렇게 하면 라이브러리에 있는 데이터베이스 드라이버를 찾아서 해당 드라이버가 제공하는 커넥션을 반환해준다.
- 여기서는 H2 데이터베이스 드라이버가 작동해서 실제 데이터베이스와 커넥션을 맺고 그 결과를 반환해준다.

```java
@Slf4j
public class DBConnectionUtilTest {

    @Test
    void connection(){
        Connection connection = DBConnectionUtil.getConnection();
        assertThat(connection).isNotNull();
    }
}
```

- 실행 결과를 보면 `class=class org.h2.jdbc.JdbcConnection` 부분을 확인할 수 있다. 이것이 바로
H2 데이터베이스 드라이버가 제공하는 `H2 **전용 커넥션**`이다. 물론 이 커넥션은 `**JDBC 표준 커넥션 인터페이스**`인 `java.sql.Connection` 인터페이스를 구현하고 있다.

## JDBC DriverManager 연결 이해

- `JDBC`는 `java.sql.Connection` 표준 커넥션 인터페이스를 정의한다.
- H2 데이터베이스 드라이버는 `JDBC Connection` 인터페이스를 구현한 `org.h2.jdbc.JdbcConnection` 구현체를 제공한다.

![Untitled 7](https://user-images.githubusercontent.com/106054507/190153352-921e3aae-a2f2-4b18-beb2-51ea150992c8.png)


- `JDBC`가 제공하는 `DriverManager` 는 라이브러리에 등록된 DB 드라이버들을 관리하고, 커넥션을 획득하는 기능을 제공한다.
1. 애플리케이션 로직에서 커넥션이 필요하면 `DriverManager.getConnection()` 을 호출한다.
2. `DriverManager` 는 라이브러리에 등록된 드라이버 목록을 자동으로 인식한다. 이 드라이버들에게 순서대로 다음 정보를 넘겨서 커넥션을 획득할 수 있는지 확인한다.
    - 여기서 각각의 드라이버는 URL 정보를 체크해서 본인이 처리할 수 있는 요청인지 확인한다.
    - 예를 들어서 URL이 jdbc:h2 로 시작하면 이것은 h2 데이터베이스에 접근하기 위한 규칙이다.
    - 따라서 H2 드라이버는 본인이 처리할 수 있으므로 실제 데이터베이스에 연결해서 커넥션을 획득하고 이 커넥션을 클라이언트에 반환한다.
    - 반면에 URL이 jdbc:h2 로 시작했는데 MySQL 드라이버가 먼저 실행되면 이 경우 본인이 처리할 수 없다는 결과를 반환하게 되고, 다음 드라이버에게 순서가 넘어간다.
3. 이렇게 찾은 커넥션 구현체가 클라이언트에 반환된다.

## JDBC 개발

```java
/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) value (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
						pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error",e);
            throw e;
        }finally {
            close(con, pstmt, null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error",e);
            }
        }
        
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error",e);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error",e);
            }
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
```

- **save() - SQL 전달**
    - `sql`
    - `con.prepareStatement(sql)`
        - 데이터베이스에 전달할 SQL과 파라미터로 전달할 데이터들을 준비한다.
        - `sql : insert into member(member_id, money) values(?, ?)"`
        - `pstmt.setString(1, member.getMemberId())` : SQL의 첫번째 ? 에 값을 지정한다. 문자이므로 `setString` 을 사용한다.
        - `pstmt.setInt(2, member.getMoney())` : SQL의 두번째 ? 에 값을 지정한다. Int 형 숫자이므로 `setInt` 를 지정한다.
    - `pstmt.executeUpdate()`
        - Statement 를 통해 준비된 SQL을 커넥션을 통해 실제 데이터베이스에
        전달한다. 참고로 `executeUpdate()` 은 int 를 반환하는데 영향받은 DB row 수를 반환한다. 여기서는 하나의 row를 등록했으므로 1을 반환한다.
- 리소스 정리는 꼭! 해주어야 한다. 예외가 발생하든, 하지 않든 항상 수행되어야 하므로 `finally 구문`에 주의해서 작성해야한다. 만약 이 부분을 놓치게 되면 커넥션이 끊어지지 않고 계속 유지되는 문제가 발생할 수 있다. 이런 것을 **리소스 누수라고 하는데, 결과적으로 커넥션 부족으로 장애가 발생할 수 있다.**
- `SQL Injection` 공격을 예방하려면 `PreparedStatement` 를 통한 파라미터 바인딩 방식을
사용해야 한다

```java
@Slf4j
public class MemberRepositoryV0 {

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }else {
                throw new NoSuchElementException("member not found memberId = "+memberId);
            }
            
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con,pstmt,rs);
        }
    }
}
```

- **findById() - 쿼리 실행**
    - sql
    - `rs = pstmt.executeQuery()` 데이터를 변경할 때는 `executeUpdate()` 를 사용하지만, 데이터를 조회할 때는 `executeQuery()` 를 사용한다. `executeQuery()` 는 결과를 `ResultSet` 에 담아서 반환한다.
    

```java
@Slf4j
public class MemberRepositoryV0 {

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {} ", resultSize);
        } catch (SQLException e) {
            log.error("db error",e);
            throw e;
        }finally {
            close(con, pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error",e);
            throw e;
        }finally {
            close(con, pstmt, null);
        }
    }
}
```
