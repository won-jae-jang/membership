package hello.membership.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    private Long id;

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();
}
