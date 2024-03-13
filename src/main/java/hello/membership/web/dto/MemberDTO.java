package hello.membership.web.dto;

import hello.membership.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private final boolean success = true;
    private final String message = "회원가입 성공";
}
