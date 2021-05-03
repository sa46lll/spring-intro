package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 밑에 모든 메서드 실행이 끝날때마다 데이터를 지워줌(콜백 메서드)
    public void afterEach(){
        repository.clearStore(); //findAll->findByName->save 데스트 순서 상관없이
        // 하나의 테스트를 끝낼때마다 데이터를 삭제함으로써 오류를 없애줌.
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring"); //이름을 지정함.

        repository.save(member); //멤버를 저장함. (id가 세팅됨)

//        repository.findById(member.getId()); //optional에서 값을 꺼낼때는 get()써야함.(좋은 방법은 아님.)
        Member result = repository.findById(member.getId()).get();
//        System.out.println("result = " + (result == member));
//        Assertions.assertEquals(result, member); //result와 member가 같은지 확인
        //얘를 주로 씀.
        assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName(){
        Member member1 = new Member(); //spring1 회원가입
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member(); //spring2 회원가입
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get(); //get()을 사용해 oprional에서 꺼내줌

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("sprint2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);

    }

    }
