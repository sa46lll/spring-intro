package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//직접 빈을 스프링에 등록하기
@Configuration
public class SpringConfig {

    @Bean //MemberService 빈에 등록
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean //MemberRepository 빈에 등록
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();

    }

}
