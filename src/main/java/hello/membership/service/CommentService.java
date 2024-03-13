package hello.membership.service;

import hello.membership.domain.Comment;
import hello.membership.domain.Member;
import hello.membership.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Long save(Comment comment) {
        commentRepository.save(comment);
        return comment.getId();
    }

    public List<String> findBoardComments(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }
}
