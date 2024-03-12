package hello.membership.service;

import hello.membership.domain.Board;
import hello.membership.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> getAllBoard() {
        return boardRepository.findAll();
    }
}
