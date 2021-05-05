package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest //스프링 컨테이너와 test를 함께 실행함.
@Transactional //Test 완료후에 항상 롤백을 해줌.(DB에 데이터가 남지 않음.)
//beforeeach, aftereach(del문) 필요 없음.

class MemberServiceIntegrationTest {

    //스프링 컨테이너에게 MemberService를 달라고 해야함(객체 직접 생성성
    //테스트는 간단하게 autowired로 받아도 됨.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    //AfterEach문 대신 Spring의 Transactional 사용
//    @AfterEach
//    public void afterEach(){
//        memberRepository.deleteAll();
//    }


    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    
    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        
        //when
        memberService.join(member1);
            //검증
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

/*
        try{
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/

//        memberService.join(member2);

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}