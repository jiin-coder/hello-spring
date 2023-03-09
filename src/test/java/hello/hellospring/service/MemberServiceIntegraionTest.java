package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegraionTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {

        // given : 이런 상황이 주어졌는데
        Member member = new Member();
        member.setName("오대진");;

        // when : 이걸 실행했을 때
        Long saveId = memberService.join(member);

        // then : 이런 결과가 나와야 한다
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {

        // given
        Member member1 = new Member();
        member1.setName("hello2");

        Member member2 = new Member();
        member2.setName("hello2");

        // when
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        /*try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

        // then
    }

}