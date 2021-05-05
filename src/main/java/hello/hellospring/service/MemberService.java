package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service //spring container에 memberService를 등록시켜줌.
@Transactional //jpa는 모든 데이터 변경이 transaction 안에서 실행되야함.
public class MemberService {

    private final MemberRepository memberRepository;
    //Test 파일과 통일 시켜주기 위해 Constructor을 씀.

//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     *
     * 회원 가입
     */

    public Long join(Member member) {

        long start = System.currentTimeMillis();

        try {
            validateDuplicateMember(member); //중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        } finally{ //예외가 터져도 항상 들어옴.
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs + "ms");
        }



          // OPTION 2)
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

    }

    private void validateDuplicateMember(Member member) {
        // OPTION 1)
        //같은 이름이 있는 중복 회원X (optional : null 방지)
        //Member member1 = result.get(); //optional 값을 직접 꺼내는건 좋지 않음.
        memberRepository.findByName(member.getName()) // = optional member
            .ifPresent(m -> { //member에 값이 있으면 출력함.
                throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers = " + timeMs + "ms");
        }
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
