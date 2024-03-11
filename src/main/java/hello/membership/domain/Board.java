package hello.membership.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Board {

    /**
     * 좋아요 수와 조회수는 초기에 0으로 셋팅
     */
    public Board() {
        this.views = 0;
        this.good_number = 0;
    }

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private int views;
    private int good_number;

    //연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getBoards().add(this);
    }

    public void setComment(Comment comment) {
        this.comments.add(comment);
        comment.setBoard(this);
    }
}
