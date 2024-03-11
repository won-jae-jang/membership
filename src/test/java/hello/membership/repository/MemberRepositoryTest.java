package hello.membership.repository;

import hello.membership.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception {

        //given
        Member member = createMember();

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveId);

        //then
        assertThat(member.getUsername()).isEqualTo(findMember.getUsername());
        assertThat(member.getPassword()).isEqualTo(findMember.getPassword());
    }

    @Test
    void 유저이름_검색() throws Exception {

        //given
        Member member = createMember();

        //when
        Long saveId = memberRepository.save(member);

        //then
        List<Member> findMember = memberRepository.findByUsername(member.getUsername());
        assertThat(findMember.size()).isEqualTo(1);
    }

    @Test
    void 모든유저_조회() throws Exception {

        //given
        Member member1 = createMember();
        Member member2 = createMember();

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);
    }

    private Member createMember() {
        Member member = new Member();
        member.setUsername("userA");
        member.setPassword("1234");
        return member;
    }
}