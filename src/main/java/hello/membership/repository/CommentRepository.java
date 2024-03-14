package hello.membership.repository;

import hello.membership.domain.Comment;
import hello.membership.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public Long save(Comment comment) {

        em.persist(comment);
        return comment.getId();
    }

    public Comment findById(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    public List<Comment> findByBoardId(Long boardId) {
        String jpql = "select c from Comment c inner join c.board b where b.id = :boardId";
        return em.createQuery(jpql, Comment.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public Member findMember(Long memberId) {
        String jpql = "select m from Comment c inner join c.member m where m.id = :memberId";
        return em.createQuery(jpql, Member.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}
