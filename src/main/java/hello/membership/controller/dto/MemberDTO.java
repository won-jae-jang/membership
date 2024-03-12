package hello.membership.controller.dto;

import hello.membership.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private final boolean success = true;
    private final String message = "OK";
    private Member member;

    public MemberDTO(Member member) {
        this.member = member;
    }
}
