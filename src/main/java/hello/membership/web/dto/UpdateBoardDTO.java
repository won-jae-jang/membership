package hello.membership.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBoardDTO {
    private boolean success = true;
    private String message = "글 업데이트 완료";
}
