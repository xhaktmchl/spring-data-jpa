package study.springdatajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.springdatajpa.dto.MemberDto;
import study.springdatajpa.entity.Member;
import study.springdatajpa.entity.Team;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest { // springdataJpa 기반 테스트

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    public void testMember() {
        Member member = new Member("name1");

        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(member.getId()).get();

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); //JPA 엔티티 동일성보장
    }


    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);
        //단건 조회 검증
        Member findMember1 =
                memberRepository.findById(member1.getId()).get();
        Member findMember2 =
                memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
        //카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);
        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    // 멤버 조회: 유저 이름과, 나이 조건검색으로 조회
    @Test
    public void findByUsernameAndAgeGreaterThan(){
        Member m1 = new Member("name1", 10);
        Member m2 = new Member("name1", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("name1", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("name1"); // 이름 비교
        assertThat(result.get(0).getAge()).isEqualTo(20); // 나이비교
        assertThat(result.size()).isEqualTo(1); // 갯수 비교
    }

    // 멤버 조회: 상위 3개 조회
    @Test
    public void findTop3(){
        Member m1 = new Member("name1", 10);
        Member m2 = new Member("name1", 20);
        Member m3 = new Member("name1", 30);
        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);

        List<Member> result = memberRepository.findTop3MemberBy(); // 상위 3개 조회

//        assertThat(result.get(0).getUsername()).isEqualTo("name1"); // 이름 비교
//        assertThat(result.get(0).getAge()).isEqualTo(10); // 나이비교
//        assertThat(result.size()).isEqualTo(2); // 갯수 비교
    }

    // 조회: 멤버 이름으로 , 네임드 쿼리 테스트
    @Test
    public void testNamedQuery(){
        Member m1 = new Member("name1", 10);
        Member m2 = new Member("name1", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("name1");

        assertThat(result.get(0).getUsername()).isEqualTo("name1"); // 이름 비교
        assertThat(result.size()).isEqualTo(2); // 갯수 비교
    }

    // 조회: 멤버 이름으로, 나이로
    @Test
    public void findByUsernameAndAgeTest(){
        Member m1 = new Member("name1", 10);
        Member m2 = new Member("name1", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAge("name1",10);

        assertThat(result.get(0).getUsername()).isEqualTo("name1"); // 이름 비교
        assertThat(result.get(0).getAge()).isEqualTo(10); // 나이비교
        assertThat(result.size()).isEqualTo(1); // 갯수 비교
    }

    // 조회: 멤버 이름리스트 전체조회
    @Test
    public void findUsernameListTest(){
        Member m1 = new Member("name1", 10);
        Member m2 = new Member("name1", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> result = memberRepository.findUsernameList();

        assertThat(result.get(0)).isEqualTo("name1"); // 이름 비교
        assertThat(result.size()).isEqualTo(2); // 갯수 비교
    }

    // 조회: 멤DTO로 직접 조
    @Test
    public void findMemberDtoTest(){
        Team t1 = new Team("team1");
        Team t2 = new Team("team2");
        teamRepository.save(t1);
        teamRepository.save(t2);

        Member m1 = new Member("name1", 10);
        Member m2 = new Member("name1", 20);
        m1.setTeam(t1);
        m2.setTeam(t2);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<MemberDto> result = memberRepository.findMemberDto();

        assertThat(result.get(0).getUsername()).isEqualTo("name1"); // 이름 비교
        assertThat(result.get(0).getTeamName()).isEqualTo("team1"); // 나이비교
        assertThat(result.size()).isEqualTo(2); // 갯수 비교
    }


    @Test
    public void returnType() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result1 = memberRepository.findListByUsername("AAA");
        Member result2 = memberRepository.findMemberByUsername("AAA");
        Optional<Member> result3 = memberRepository.findOptionalMemberByUsername("AAA");
    }
}