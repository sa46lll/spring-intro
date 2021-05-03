package hello.hellospring.domain;

public class Member {

    private Long id; //시스템이 정해주는 아이디
    private String name; //고객이 등록하는 이름

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
