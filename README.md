# 1. 스프링 웹 개발 기초

## 1-1. 정적 콘텐츠

- 설명
    - 정적 콘텐츠를 자동으로 그대로 제공해준다.
    - 수정을 못한다.
- 과정
    - 웹 브라우저에서 [localhost:8080/hello-static.html](http://localhost:8080/hello-static.html) 을 날리면, 내장 톰캣 서버가 요청을 받고, 스프링으로 넘긴다. 스프링은 Controller에서 hello-static을 찾고 없으면 정적파일로 가서 찾는다.
    **⇒ 컨트롤러가 정적리소스보다 우선순위를 먼저 가진다.**
- `static/hello-static.html`

## 1-2. MVC와 템플릿 엔진

- 템플릿 엔진을 모델, 뷰, 컨트롤러로 쪼갠다.
- 뷰를 찾고, 템플릿 엔진을 통해 화면을 렌더링해서 **html**을 웹브라우저에 넘겨주는 방식

## 1-3. API

- 설명
    - 뷰가 없고, json으로 데이터를 그대로 내려줌 (xml도 가능)
- 과정
    - 내장 톰캣 서버가 요청을 받으면, 컨트롤러를 찾아서 객체를 받고, HttpMessageConverter에서 JsonConverter이나 StringConverter로 바꿔서 응답한다.

> **자바 빈 표준 방식**
> 

```java
static class Hello{
        private String name;

        public String getName() { // 프로퍼티 접근 방식
            return name;
        }

        public void setName(String name) {
            this.name = name;

        }
    }
```

> **객체를 json으로 바꿔주는 대표적인 라이브러리**

Jackson 라이브러리 → 스프링이 기본적으로 탑재<br/>
Gson 라이브러리

> **Optional 처리방법**
> 

Optional 안에 객체가 있는지 확인할 때

```java
Optional<Member> result = memberRepository.findByName(member.getName());
result.ifPresent(n -> {
		throw new IllegalStateException("이미 존재하는 회원입니다.");
```

> **Dependency Injection (DI)**
> 

직접 만들어주지 않고, 생성자를 통해 외부에서 주입해주는 것 

# 2. 스프링 빈과 의존 관계

Controller 어노테이션이 있으면 그 컨트롤러는 **스프링 컨테이너**가 관리하게 된다.

```java
@Controller
public class MemberController {

    private final MemberService memberService; //생성자 주입

    @Autowired // 연결시킬때!! memberService를 스프링이 가져다 연결시켜줌.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
```

⇒ 스프링 컨테이너가 뜰 때 MemberController를 생성해주면서 생성자를 호출한다. 이 때 Autowired 어노테이션이 있으면, 스프링 컨테이너에 있는 MemberService를 연결시켜준다.

Controller는 Service가 필요하고, Service는 Repository의 주입이 필요하다.

## 2-1. 스프링 빈을 등록하는 2가지 방법

1. 컴포넌트 스캔과 자동 의존관계 설정
    - Controller, Service, Repository 어노테이션 → 컴포넌트 스캔
    - `Component` 어노테이션이 있으면 스프링 빈으로 자동 등록된다.
2. 자바 코드로 직접 스프링 빈 등록하기

### 2-1-1. 자바 코드로 직접 스프링 빈 등록하기

Service와 Repository 어노테이션을 지우고 직접 스프링 빈을 등록해보자.

(Controller는 직접 넣어줄 수 없다. 어노테이션 필수)

```java
@Configuration
public class SpringConfig {

    @Bean //MemberService 빈에 등록
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

		@Bean
    public MemberRepository memberRepository(){
        return new MemorymemberRepository();
    }
}
```

> **스프링 컨테이너에 스프링 빈을 등록할 때…**
> 

스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다.(하나만 등록해서 공유한다.) 따라서 같은 스프링 빈이면 모두 같은 인스턴스이다. 싱글톤이 아니게 설정할 수 있지만, 특별한 경우를 제외하면 대부분 싱클톤을 사용한다.

> **DI(의존성 주입) 에는…**
> 

필드 주입, setter 주입, 생성자 주입. 이렇게 3가지 방식이 있다.
실행 중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입을 권장한다.

1. 생성자 주입 (권장)

```java
private final MemberService memberService;

@Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
```

2. 필드 주입 (테스트 시에 간단하게 사용 권장)

```java
@Autowired private MemberService memberService;
```

3. setter 주입 (얘가 public하게 노출되므로 비권장)

```java
private MemberService memberService;

@Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
```

> **Autowired를 통한 DI는…**
> 

Controller, Service 등과 같이 스프링이 관리하는 객체에서만 동작한다. 스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않는다.

# 3. 스프링 DB 접근 기술

## 3-1. JDBC

Java가 DB랑 붙으려면 꼭 필요한 JDBC 드라이버

h2는 db가 제공하는 클라이언트!

> **개방-폐쇄 원칙(OCP, Open-Closed Principal)**
> 

확장에는 열려 있고, 수정변경에는 닫혀있다.

스프링의 DI(Depemdemcies Injection)을 사용하면 기존 코드를 전혀 손대지 않고, 설정만으로 구현 클래스를 변경할 수 있다.

> **Transactional**
> 

**설명**

- 테스트 시에 DB에 반영이 되지 않도록 해주는 어노테이션, 테스트의 반복을 도움
- 다음 테스트에 영향응 주지 않는다.

**과정**

- 각 테스트 시작 시에 트랜잭션을 걸고, 각 테스트가 끝나면 항상 롤백 → DB에 반영이 안됨.

## 3-2. 테스트 주의사항

실제로 10시간 중 6~7은 테스트 코드를 작성한다.

테스트 코드를 꼼꼼히 작성하는 습관을 들이자.

>💡 단위테스트: 순수한 자바 코드로 최소한의 단위로 테스트하는 것
통합테스트: 스프링 컨테이너로 DB 연동하고 테스트하는 것
단위테스트가 통합테스트보다 대부분 훨씬 좋은 테스트일 확률이 높다.
가급적이면 단위로 쪼개서 스프링 컨테이너 없이 테스트 할 수 있도록 테스트하자.


## 3-3. JdbcTemplate

JDBC API의 반복 코드를 대부분 제거해준다. 하지만, SQL은 직접 작성해야 한다.

## 3-4. JPA

설명

- 기존의 반복 코드는 물론이고, 기본적인 SQL 쿼리까지 자동으로 처리해주는 자바 인터페이스 모음이다.
- JPA 인터페이스의 Hibernate 구현체를 주로 쓴다.
- 객체 + ORM (Object Relation Mapping)

장점

- JPA를 사용하면, SQL과 데이터 중심의 설계에서 **객체 중심**의 설계로 패러다임을 전환할 수 있다.
- 개발 생산성을 크게 높일 수 있다.

### 3-4-1. EntityManager

 JPA는 EntityManager로 모든 것이 동작한다. 스프링 부트가 자동으로 em을 생성해주고, 만들어진 것을 인젝션 받기만 하면 된다.

```java
private final EntityManager em;

    @Override
    public Member save(Member member) {
        em.persist(member); //insert query 다 만들어주고 setId까지 모든걸 다 해줌.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);

    }

    @Override
    public Optional<Member> findByName(String name) { //jpql 객체지향언어 사용
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() { // 객체를 대상으로 쿼리를 날리면 sql로 변환이 된다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
```

### 3-4-2. Transactional 어노테이션

데이터를 저장하거나 변경할 때는 항상 Transactional 어노테이션을 붙이자.

## 3-5. 스프링 데이터 JPA

설명

- 리포지토리에 구현 클래스 없이 인터페이스만으로 개발을 완료할 수 있게 한다.
- 인터페이스를 통한 기본적인 CRUD 제공
- 페이징 기능 자동 제공

장점

- 스프링 부트와 JPA만 사용해도 개발 생산성이 정말 많이 증가하고, 개발해야할 코드도 확연히 줄어든다. 여기에 스프링 데이터 JPA를 사용하면, 단순하고 반복적인 개발 코드들이 확연히 감소한다.
- 개발자는 핵심 비즈니스 로직을 개발하는데 집중할 수 있다.

주의

- 스프링 데이터 JPA는 JPA를 편리하게 사용하도록 도와주는 기술이다. 따라서 JPA의 동작 방식을 먼저 이해하고 JPA를 사용하는 것을 추천한다.

```java
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Override
    Optional<Member> findByName(String name);
}
```

스프링 데이터 JPA가 스프링 빈에 직접 등록을 해준다. 그냥 인젝션 받아서 가져다 쓰기만 하면 된다.

```java
private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
```

>💡 실무에서는 **JPA**와 **스프링 데이터 JPA**를 기본으로 사용하고, 복잡한 동적 쿼리는 **Querydsl** 라이브러리를 사용한다. Querydsl을 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적 쿼리도 편리하게 작성할 수 있다.
이 조함으로 해결하기 어려운 쿼리는 JPA가 제공하는 **네이티브 쿼리**를 사용하거나, 앞서 학습한 스프링 **JdbcTemplate**를 사용하면 된다.

# 4. AOP

시간을 측정하는 로직은 공통 관심 사항이다. AOP가 없다면 시간을 측정하는 로직과 핵심 비즈니스 로직이 모두 섞여 유지보수가 어려울 것이다. <br/>
따라서, 공통 관심 사항과 핵심 관심 사항을 분리하여 적용하자. <br/>
예를 들어, 회원가입시간, 회원 조회시간을 측정하고 싶다면? AOP를 쓰자. <br/>

AOP

- Aspect Oriented Programming
- 메소드의 호출 시간을 측정하고 싶을 때
