package hello.membership.repository;

import hello.membership.domain.Board;
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
class BoardRepositoryTest {

    @Autowired BoardRepository boardRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    void 게시판_저장() throws Exception {

        //given
        Board board = new Board();
        board.setTitle("board1");
        board.setContent("content1");

        //when
        Long saveId = boardRepository.save(board);

        //then
        Board findBoard = boardRepository.findById(saveId);
        assertThat(board.getTitle()).isEqualTo(findBoard.getTitle());
        assertThat(board.getContent()).isEqualTo(findBoard.getContent());
    }

    @Test
    void 모든_게시판_조회() throws Exception {

        //given
        Board board1 = new Board();
        board1.setTitle("board1");
        board1.setContent("content1");

        Board board2 = new Board();
        board2.setTitle("board2");
        board2.setContent("content2");

        //when
        boardRepository.save(board1);
        boardRepository.save(board2);

        //then
        List<Board> boards = boardRepository.findAll();
        assertThat(boards.size()).isEqualTo(2);
        assertThat(boards).contains(board1, board2);
    }

    @Test
    void 게시판_회원_조회() throws Exception {

        //given
        Member member = new Member();
        member.setUsername("userA");
        member.setPassword("123");
        memberRepository.save(member);

        Board board = new Board();
        board.setTitle("board1");
        board.setContent("content1");
        board.setMember(member);

        //when
        Long saveId = boardRepository.save(board);

        //then
        Board findBoard = boardRepository.findById(saveId);
        assertThat(findBoard.getMember().getUsername()).isEqualTo("userA");
        assertThat(findBoard.getMember().getPassword()).isEqualTo("123");
    }

    @Test
    void 게시판_삭제() throws Exception {

        //given
        Board board = new Board();
        board.setTitle("board1");
        board.setContent("content1");

        //when
        Long saveId = boardRepository.save(board);

        //then
        List<Board> boards = boardRepository.findAll();
        assertThat(boards.size()).isEqualTo(2); //더미 데이터가 1개 있음
        boardRepository.deleteById(saveId);
        List<Board> deleteAfterBoards = boardRepository.findAll();
        assertThat(deleteAfterBoards.size()).isEqualTo(1);
    }
}