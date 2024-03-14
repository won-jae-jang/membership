package hello.membership.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private final boolean success = true;
    private final String message = "댓글 작성 성공";

    public CommentDTO() {
    }
}
