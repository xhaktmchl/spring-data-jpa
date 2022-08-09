package study.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.springdatajpa.entity.Member;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {
    // 쿼리메소드1: 메소드 이름으로 쿼리
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age); // 멤버 조회: 유저 이름과, 나이 초과 조건검색으로 조회

    List<Member> findTop3MemberBy();

    //쿼리메소드2: 네임으 쿼리
    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username); // @Param( "JPQL파라미터 이름 ")

    // 쿼리메소드3: JPQL 직접작성햐
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findByUsernameAndAge(@Param("username") String username, @Param("age") int age);
}
