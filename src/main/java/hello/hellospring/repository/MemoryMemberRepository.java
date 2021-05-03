package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); //저장하는 기능
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence); //id 세팅
        store.put(member.getId(), member); //store에 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
//        return store.get(id); //null을 방지하기 위해 optional을 씀.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
        //루프를 돌면서 찾아지면 바로 반환 but, 끝까지 찾았는데 없으면 optional이 null을 반환함.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        //store.values = 멤버들
    }

    public void clearStore(){
        store.clear();
    }
}
