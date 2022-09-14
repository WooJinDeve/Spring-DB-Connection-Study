# 스프링 JdbcTemplate

## JdbcTemplate 소개

```java
/**
 * JdbcTemplate
 */
@Slf4j
public class JdbcTemplateItemRepositoryV1 implements ItemRepository {

    private final JdbcTemplate template;

    public JdbcTemplateItemRepositoryV1(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        String sql = "insert into item(item_name, price, quantity) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            // 자동 증가 키
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, item.getItemName());
            ps.setInt(2, item.getPrice());
            ps.setInt(3, item.getQuantity());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        item.setId(key);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "update item set item_name=?, price=?m quantity=? where id=?";
        template.update(sql,
                updateParam.getItemName(),
                updateParam.getPrice(),
                updateParam.getQuantity(), itemId);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name, price, quantity from item where id= ?";
        try {
            Item item = template.queryForObject(sql, itemRowMapper(), id);
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        String sql = "select id, item_name, price, quantity from item";

        //동적 쿼리
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where";
        }
        boolean andFlag = false;
        List<Object> param = new ArrayList<>();
        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%',?,'%')";
            param.add(itemName);
            andFlag = true;
        }
        if (maxPrice != null) {
            if (andFlag) {
                sql += " and";
            }
            sql += " price <= ?";
            param.add(maxPrice);
        }
        log.info("sql={}", sql);

				return template.query(sql, itemRowMapper(), param.toArray());
    }

    private RowMapper<Item> itemRowMapper() {
        return ((rs,rowNum) -> {
            Item item = new Item();
            item.setId(rs.getLong("id"));
            item.setItemName(rs.getString("item_name"));
            item.setPrice(rs.getInt("price"));
            item.setQuantity(rs.getInt("quantity"));
            return item;
        });
    }
}
```

- **save()**
    - 데이터를 저장한다.
        - `template.update()` : 데이터를 변경할 때는 `update()` 를 사용하면 된다.
        - `INSERT` , `UPDATE` , `DELETE SQL`에 사용한다.
    - `template.update()` 의 반환 값은 `int` 인데, 영향 받은 로우 수를 반환한다.
    데이터를 저장할 때 `PK` 생성에 `identity (auto increment)` 방식을 사용하기 때문에, 개발자가 직접 지정하는 것이 아니라 비워두고 저장해야 한다.
    - 데이터베이스에 `INSERT`가 완료 되어야 생성된 `PK ID` 값을 확인할 수 있다.
    - `KeyHolder` 와 `connection.prepareStatement(sql, new String[]{"id"})` 를 사용해서 `id` 를 지정해주면 `INSERT` 쿼리 실행 이후에 데이터베이스에서 생성된 `ID` 값을 조회할 수 있다.
    물론 데이터베이스에서 생성된 `ID` 값을 조회하는 것은 순수 `JDBC`로도 가능하지만, 코드가 훨씬 더 복잡하다.
- **update()**
    - 데이터를 업데이트 한다.
    - `template.update()` : 데이터를 변경할 때는 `update()` 를 사용하면 된다.
        - `?` 에 바인딩할 파라미터를 순서대로 전달하면 된다.
        - 반환 값은 해당 쿼리의 영향을 받은 로우 수 이다.
- **findById()**
    - 데이터를 하나 조회한다.
    - `template.queryForObject()`
        - 결과 로우가 하나일 때 사용한다.
        - `RowMapper` 는 데이터베이스의 반환 결과인 `ResultSet` 을 객체로 변환한다.
        - 결과가 없으면 `EmptyResultDataAccessException` 예외가 발생한다.
        - 결과가 둘 이상이면 `IncorrectResultSizeDataAccessException` 예외가 발생한다.
    - `ItemRepository.findById() 인터페이스`는 결과가 없을 때 `Optional` 을 반환해야 한다. 따라서 결과가 없으면 예외를 잡아서 `Optional.empty` 를 대신 반환하면 된다
- **findAll()**
    - 데이터를 리스트로 조회한다. 그리고 검색 조건으로 적절한 데이터를 찾는다.
    - `template.query()`
        - 결과가 하나 이상일 때 사용한다.
        - `RowMapper` 는 데이터베이스의 반환 결과인 `ResultSet` 을 객체로 변환한다.
        - 결과가 없으면 빈 컬렉션을 반환한다.
- **itemRowMapper()**
    - 데이터베이스의 조회 결과를 객체로 변환할 때 사용한다.
    - `JdbcTemplate`이 다음과 같은 루프를 돌려주고, 개발자는 `RowMapper` 를 구현하면 된다.
    
- **동적 쿼리 적용 문제**
    - 동적 쿼리가 언듯 보면 쉬워 보이지만,  생각보다 다양한 상황을 고민해야 한다. 예를 들어서 어떤 경우에는 `where` 를 앞에 넣고 어떤 경우에는 `and` 를 넣어야 하는지 등을 모두 계산해야 한다

## JdbcTemplate 구성

```java
@Configuration
@RequiredArgsConstructor
public class JdbcTemplateV1Config {

    private final DataSource dataSource;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTemplateItemRepositoryV1(dataSource);
    }
}

spring.profiles.active=local
spring.datasource.url=jdbc:h2:tcp://localhost/~/test
spring.datasource.username=sa
```

## JdbcTemplate 이름 지정 파라미터

- `JdbcTemplate`은 이런 문제를 보완하기 위해 `NamedParameterJdbcTemplate` 라는 이름을 지정해서 파라미터를 바인딩 하는 기능을 제공한다.
- 파라미터를 전달하려면 `Map` 처럼 `key , value` 데이터 구조를 만들어서 전달해야 한다.
- `key` 는 `:`파리이터이름으로 지정한, 파라미터의 이름이고 , `value`는 해당 파라미터의 값이 된다.
- **이름 지정 바인딩에서 자주 사용하는 파라미터 3가지**
    - `Map`
    - `SqlParameterSource`
        - `MapSqlParameterSource`
            - `Map` 과 유사한데, `SQL` 타입을 지정할 수 있는 등 `SQL`에 좀 더 특화된 기능을 제공
            - `SqlParameterSource` 인터페이스의 구현체
            - `MapSqlParameterSource` 는 메서드 체인을 통해 편리한 사용법도 제공
        - `BeanPropertySqlParameterSource`
            - 자바빈 프로퍼티 규약을 통해서 자동으로 파라미터 객체를 생성한다.
            - 예)  `getXxx() -> xxx`, `getItemName() -> itemName`
                - `key=itemName, value=상품명 값`
                - `key=price, value=가격 값`
- **BeanPropertyRowMapper**
    - `ResultSet` 의 결과를 받아서 자바빈 규약에 맞추어 데이터를 변환한다.
- **별칭**
    - `select item_name` 의 경우 `setItem_name()` 이라는 메서드가 없을 경우 조회 `SQL`을 다음과 같이 고치면 된다.
        - `select item_name as itemName`

```java
/**
 * NamedParameterJdbcTemplate
 * SqlParameterSource
 * - BeanPropertySqlParameterSource
 * - MapSqlParameterSource
 * Map
 *
 * BeanPropertyRowMapper
 *
 */
@Slf4j
@Repository
public class JdbcTemplateItemRepositoryV2 implements ItemRepository {
//    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateItemRepositoryV2(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }
    @Override
    public Item save(Item item) {
        String sql = "insert into item (item_name, price, quantity) values (:itemName, :price, :quantity)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(item);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        long key = keyHolder.getKey().longValue();
        item.setId(key);
        return item;
    }
    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "update item set item_name=:itemName, price=:price, quantity=:quantity where id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("itemName", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", itemId);

        template.update(sql,param);
    }
    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name, price, quantity from item where id = :id";
        try {
            Map<String, Object> param = Map.of("id", id);
            Item item = template.queryForObject(sql, param,itemRowMapper());
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        SqlParameterSource param = new BeanPropertySqlParameterSource(cond);

        String sql = "select id, item_name, price, quantity from item";
        //동적 쿼리
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where";
        }
        boolean andFlag = false;
        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%',:itemNam,'%')";
            andFlag = true;
        }
        if (maxPrice != null) {
            if (andFlag) {
                sql += " and";
            }
            sql += " price <= maxPrice";
        }
        log.info("sql={}", sql);
        return template.query(sql, param, itemRowMapper());
    }
    private RowMapper<Item> itemRowMapper() {
        //camel 지원
        return BeanPropertyRowMapper.newInstance(Item.class);
    }
}
```

- `this.template = new NamedParameterJdbcTemplate(dataSource)`
- `NamedParameterJdbcTemplate` 도 내부에 `dataSource` 가 필요하다.
- `JdbcTemplateItemRepositoryV2` 생성자를 보면 의존관계 주입은 `dataSource` 를 받고 내부에서
`NamedParameterJdbcTemplate` 을 생성해서 가지고 있다.

## SimpleJdbcInsert

- `JdbcTemplate`은 `INSERT SQL`를 직접 작성하지 않아도 되도록 `SimpleJdbcInsert` 라는 편리한
기능을 제공

```java
/**
 * SinpleJdbcInsert
 */
@Slf4j
@Repository
public class JdbcTemplateItemRepositoryV3 implements ItemRepository {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateItemRepositoryV3(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("item")
                .usingGeneratedKeyColumns("id");
//                .usingColumns("item_name","price","quantity") 생략 가능

    }
    @Override
    public Item save(Item item) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);
        Number key = jdbcInsert.executeAndReturnKey(param);
        item.setId(key.longValue());
        return item;
    }
}
```

- `SimpleJdbcInsert` 는 생성 시점에 데이터베이스 테이블의 메타 데이터를 조회한다.
- `withTableName` : 데이터를 저장할 테이블 명을 지정한다.
- `usingGeneratedKeyColumns` : `key` 를 생성하는 `PK 컬럼 명`을 지정한다.
- `usingColumns` : `INSERT SQL`에 사용할 컬럼을 지정한다. 특정 값만 저장하고 싶을 때 사용한다. 생략할 수 있다.

## JdbcTeamplate 기능 정리

- `JdbcTemplate` : 순서 기반 파라미터 바인딩을 지원한다.
- `NamedParameterJdbcTemplate` : 이름 기반 파라미터 바인딩을 지원한다. (권장)
- `SimpleJdbcInsert` : INSERT SQL을 편리하게 사용할 수 있다.
- `SimpleJdbcCall` : 스토어드 프로시저를 편리하게 호출할 수 있다

```java
jdbcTemplate.update(
 "call SUPPORT.REFRESH_ACTORS_SUMMARY(?)",
 Long.valueOf(unionId));
```

- **기타 기능**
    - 임의의 `SQL`을 실행할 때는 `execute()` 를 사용하면 된다. 테이블을 생성하는 `DDL`에 사용할 수 있다

```java
jdbcTemplate.execute("create table mytable (id integer, name varchar(100))");
```