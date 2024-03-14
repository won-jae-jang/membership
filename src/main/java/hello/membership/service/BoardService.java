package hello.membership.service;

import hello.membership.domain.Board;
import hello.membership.domain.Member;
import hello.membership.repository.BoardRepository;
import hello.membership.repository.CommentRepository;
import hello.membership.repository.MemberRepository;
import hello.membership.web.form.BoardForm;
import hello.membership.web.form.UpdateBoardForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
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

    public Board findById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    /**
     * @param memberId: 게시판을 수정하려고 시도하는 사용자
     * @param boardId: 수정할 게시판
     * @return: 게시판을 작성한 사람과 수정할 게시판의 작성자가 같은지 비교
     */
    public boolean isAuthorized(Long memberId, Long boardId) {
        Board board = boardRepository.findById(boardId);
        return board.getMember().getId() == memberId;
    }

    @Transactional
    public void upView(Long boardId) {
        Board board = boardRepository.findById(boardId);
        board.upView();
    }

    @Transactional
    public void updateBoard(UpdateBoardForm form, Long boardId) {
        log.info("---update board---");
        Board board = boardRepository.findById(boardId);
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
    }

    public String formatDate(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(formatter);
    }
}
