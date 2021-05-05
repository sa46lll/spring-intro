package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
//스프링 jpa가 구현체를 자기가 만들어냄, 빈에 등록해놓음.

    // finByName => "select m from Member m where m.name = ?"
    // finByNameAndId => "select m from member m where m.name and m.id = ?"
    @Override
    Optional<Member> findByName(String name); //구현 끝(메서드 이름만으로 조회 기능 제공)

}
