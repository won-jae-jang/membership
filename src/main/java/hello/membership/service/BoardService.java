package hello.membership.service;

import hello.membership.domain.Board;
import hello.membership.domain.Member;
import hello.membership.exception.exception.CustomDatabaseException;
import hello.membership.exception.exception.NotAuthorizedUserException;
import hello.membership.repository.BoardRepository;
import hello.membership.repository.CommentRepository;
import hello.membership.repository.MemberRepository;
import hello.membership.web.form.BoardForm;
import hello.membership.web.form.UpdateBoardForm;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.data.crossstore.ChangeSetPersister.*;

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

        if (member != null){
            Board board = Board.createBoard(member);
            board.setTitle(boardForm.getTitle());
            board.setContent(boardForm.getContent());
            board.setMember(member);
            board.setDate(LocalDateTime.now());
            boardRepository.save(board);
            return board;
        } else {
            throw new EntityNotFoundException("회원을 찾을 수 없음");
        }
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
        return memberId.equals(board.getMember().getId());
    }

    @Transactional
    public void upView(Long boardId) {
        try {
            Board board = boardRepository.findById(boardId);
            if (board != null) {
                board.upView();
            } else {
                throw new EntityNotFoundException("해당 게시판을 찾을 수 없습니다");
            }
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public void updateBoard(UpdateBoardForm form, Long boardId) {

        log.info("---update board---");
        Board board = boardRepository.findById(boardId);

        if (board != null) {
            board.setTitle(form.getTitle());
            board.setContent(form.getContent());
        } else {
            throw new EntityNotFoundException("업데이트할 게시판이 없습니다");
        }
    }

    public void deleteBoard(Long boardId) {

        log.info("---deleteBoard---");
        try {
            boardRepository.deleteById(boardId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("삭제할 게시판이 없습니다");
        } catch (CustomDatabaseException e) {
            throw new CustomDatabaseException("서버측 데이터베이스에 문제 발생");
        }
    }

    public String formatDate(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(formatter);
    }
}
