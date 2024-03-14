package hello.membership.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizedDTO {
    private boolean authorized;

    public AuthorizedDTO(boolean authorized) {
        this.authorized = authorized;
    }
}
