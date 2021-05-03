package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!!");
        return "hello"; // resources>templates>hello.html 찾음.
    }

    // mvc, 템플릿 엔진
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){ //required=true(default)
        model.addAttribute("name", name); //파라미터의 name을 받음.
        return "hello-template";
    }

    // API
    @GetMapping("hello-string")
    @ResponseBody //http의 body에 데이터를 직접 넣어주겠다.(string 반환, stringConverter 동작)
    public String helloString(@RequestParam("name") String name) {
        return "hello " +name; //view 가 없음. name에 적은 그대로 보여줌.
    }

    //API, json(key, value) 반환 -> 최근에 대부분 사용하는 추세
    @GetMapping("hello-api")
    @ResponseBody//http의 body에 데이터를 직접 넣어주겠다.(객체 반환, jsonConverter 동작)
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;

        }
    }

}
