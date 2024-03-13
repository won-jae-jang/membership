package hello.membership.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutDTO {
    private final boolean success = true;
    private final String message = "로그아웃 성공";

}
