package hello.membership.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
        this.goodNumber = 0;
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
    private int goodNumber;
    private LocalDateTime date;

    //연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getBoards().add(this);
    }

    public void setComment(Comment comment) {
        this.comments.add(comment);
        comment.setBoard(this);
    }

    //생성 메서드
    public static Board createBoard(Member member) {
        Board board = new Board();
        board.setMember(member);
        return board;
    }


    /**
     * 테스트용 equals, hashcode 오버라이드
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
