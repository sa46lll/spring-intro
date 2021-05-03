package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    //optional : null을 방지하기 위함
    Optional<Member> findById(Long id); //해당하는 id를 찾음.
    Optional<Member> findByName(String name);//해당하는 name을 찾음.
    List<Member> findAll(); //모든 회원의 리스트를 반환해줌.
}
