package hello.membership.service;

import hello.membership.domain.Board;
import hello.membership.domain.Member;
import hello.membership.repository.BoardRepository;
import hello.membership.repository.CommentRepository;
import hello.membership.repository.MemberRepository;
import hello.membership.web.form.BoardForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public List<Board> getAllBoard() {
        return boardRepository.findAll();
    }

    /**
     * @param memberId: 게시판을 작성하는 사용자
     * @return: 게시판 정보 반환
     */
    public Board save(BoardForm boardForm, Long memberId) {
        Member member = memberRepository.findById(memberId);
        Board board = Board.createBoard(member);
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        board.setMember(member);
        board.setDate(LocalDateTime.now());
        boardRepository.save(board);
        return board;
    }
}
