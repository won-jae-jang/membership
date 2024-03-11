package hello.membership.service;

import hello.membership.domain.Member;
import hello.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String username, String password) {

        //username이 데이터베이스에 있는지 확인
        List<Member> findMembers = memberRepository.findByUsername(username);
        if (findMembers.isEmpty()) {
            return null;
        }

        //password가 맞는지 확인
        Optional<Member> member = findMembers.stream().filter(m -> m.getPassword().equals(password))
                .findFirst();

        if (member.isPresent()) {
            return member.get();
        }else{
            return null;
        }
    }

    public Long save(Member member) {
        return memberRepository.save(member);
    }
}
