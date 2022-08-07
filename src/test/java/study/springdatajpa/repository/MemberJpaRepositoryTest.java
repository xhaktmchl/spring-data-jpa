package study.springdatajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.springdatajpa.entity.Member;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest // springBoot 테스트 입을 명시
@Transactional // 자동 롤백
@Rollback(value = false) // 기록을 보기위한 롤백 방지 설정
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("name1");

        Member savedMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.find(member.getId());

        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        assertThat(findMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(findMember).isEqualTo(savedMember); //JPA 엔티티 동일성 보장

    }
}