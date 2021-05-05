package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; //jpa는 entityManager로 모두 동작함.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); //insert query 다 만들어주고 setId까지 모든걸 다 해줌.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member); //Null 방지

    }

    @Override
    public Optional<Member> findByName(String name) { //jpql 객체지향언어 사용
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() { //memberEntity 객체 자체를 검색(매핑이 완료되어있음)
        return em.createQuery("select m from Member as m", Member.class)
                .getResultList();
    }
}
