package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller //스프링이 관리함. 스프링에 등록하고 받아서 써야함.
public class MemberController {

    private final MemberService memberService; //생성자 주입

    @Autowired // 연결시킬때!! memberService를 스프링이 가져다 연결시켜줌.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new") //url로 들어옴.(주로 조회할 때)
    public String createForm(){
        return "/members/createMemberForm"; //templete에서 찾음.
    }

    @PostMapping("/members/new") //data를 form에 넣어 전달할 때 주로 씀.
    public String create(MemberForm form){
        //createMemberForm.html의 name과 MemberForm의 name을 연결해줌
        //MemberForm의 setName을 이용하여 스프링 값이 들어옴.
        Member member = new Member();
        member.setName(form.getName()); //set으로 넣은걸 get으로 꺼냄.

        memberService.join(member);

        System.out.println("member = " + member.getName());

        return "redirect:/"; //끝났으니 홈화면으로 돌려보냄.
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();//member을 다 꺼내 옴.
        model.addAttribute("members", members);
        return "members/memberList";

    }
}
