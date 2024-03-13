package hello.membership.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
    private final boolean success = true;
    private final String message = "글 작성 성공";
    private Long boardId;

    public BoardDTO(Long boardId) {
        this.boardId = boardId;
    }
}
