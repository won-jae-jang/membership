package hello.membership.repository;

import hello.membership.domain.Board;
import hello.membership.domain.Comment;
import hello.membership.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired BoardRepository boardRepository;

    @Test
    void save() throws Exception {

        //given
        Member member = createMember();
        Board board = createBoard(member);
        Comment comment = createComment(member, board);

        //when
        Long saveId = commentRepository.save(comment);

        //then
        Comment findComment = commentRepository.findById(saveId);
        assertThat(findComment.getMember().getUsername()).isEqualTo("userA");
        assertThat(findComment.getBoard().getTitle()).isEqualTo("board-title");
    }

    @Test
    void findMember() throws Exception {

        //given
        Member member = createMember();
        Board board = createBoard(member);
        Comment comment = createComment(member, board);

        //when
        Long saveId = commentRepository.save(comment);

        //then
        Member findMember = commentRepository.findMember(member.getId());
        assertThat(findMember.getUsername()).isEqualTo("userA");
    }

    @Test
    void findByBoardId() throws Exception {

        //given
        Member member = createMember();
        Board board = createBoard(member);
        Comment comment = createComment(member, board);

        //when
        commentRepository.save(comment);
        List<Comment> comments = commentRepository.findByBoardId(board.getId());

        //then
//        System.out.println("comments = " + comments);
        assertThat(comments.size()).isEqualTo(1);
    }

    private Member createMember() {
        Member member = new Member();
        member.setUsername("userA");
        member.setPassword("123");
        memberRepository.save(member);
        return member;
    }

    private Board createBoard(Member member) {
        Board board = new Board();
        board.setTitle("board-title");
        board.setContent("board-content");
        board.setMember(member);
        boardRepository.save(board);
        return board;
    }

    private Comment createComment(Member member, Board board) {
        Comment comment = Comment.createComment(member, board);
        comment.setContent("content");
        board.setComment(comment);
        return comment;
    }
}