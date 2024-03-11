package hello.membership.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private Long member_id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    @NotBlank
    private String content;
}
