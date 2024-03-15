package hello.membership.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteBoardDTO {
    private boolean success;
    private String message;

    public DeleteBoardDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
