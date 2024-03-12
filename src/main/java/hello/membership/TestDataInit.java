package hello.membership;

import hello.membership.domain.Board;
import hello.membership.domain.Member;
import hello.membership.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        Member member = new Member();
        member.setUsername("test");
        member.setPassword("test!");
        memberRepository.save(member);

        Board board = new Board();
        board.setTitle("board-title");
        board.setContent("board-content");
    }
}
