package hello.hellospring.controller;

public class MemberForm {
    public String name; //createMemberForm.html의 name이 들어옴.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
