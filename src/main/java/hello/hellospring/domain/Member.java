package hello.hellospring.domain;

import javax.persistence.*;

@Entity //jpa가 관리하는 entity가 됨.
public class Member {

    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //db가 알아서 생성해줌.
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
