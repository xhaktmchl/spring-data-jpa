package study.springdatajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.springdatajpa.entity.Member;

import java.util.List;

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

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        //단건 조회 검증
        Member findMember1 =
                memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 =
                memberJpaRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
        //리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
        //카운트 검증
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);
        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan(){
        Member m1 = new Member("name1", 10);
        Member m2 = new Member("name1", 20);
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("name1", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("name1"); // 이름 비교
        assertThat(result.get(0).getAge()).isEqualTo(20); // 나이비교
        assertThat(result.size()).isEqualTo(1); // 갯수 비교
    }

    @Test
    public void testNamedQuery(){
        Member m1 = new Member("name1", 10);
        Member m2 = new Member("name1", 20);
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUsername("name1");

        assertThat(result.get(0).getUsername()).isEqualTo("name1"); // 이름 비교
        assertThat(result.size()).isEqualTo(2); // 갯수 비교
    }

    /*
    페이징 적용
     */
    @Test
    public void paging(){
        // given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));

        int age=10;
        int offset = 0;
        int limit = 3;

        //when
        List<Member> result = memberJpaRepository.findByPage(age, offset, limit);
        Long totalCount = memberJpaRepository.totalCount(age);

        //페이지 계산 공식 적용...
        // totalPage = totalCount / size ...
        // 마지막 페이지 ...
        // 최초 페이지 ..

        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5); // 갯수 비교
    }


    /*
    벌크성 수정쿼리
     */
    @Test
    public void bulkAgePlus(){
        // given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 4));

        //when
        int result = memberJpaRepository.bulkAgePlus(5);

        //then
        assertThat(result).isEqualTo(2);
    }
}