package study.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.springdatajpa.entity.Member;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsername(String username);

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age); // 멤버 조회: 유저 이름과, 나이 초과 조건검색으로 조회

    List<Member> findTop3MemberBy();
}
