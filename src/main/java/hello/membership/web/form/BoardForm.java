package hello.membership.web.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
