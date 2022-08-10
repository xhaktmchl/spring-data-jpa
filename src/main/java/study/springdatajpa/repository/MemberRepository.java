package study.springdatajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.springdatajpa.dto.MemberDto;
import study.springdatajpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


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

    // 갑 조회: 유저이름 전체 조회
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // DTO로 직접 조회
    @Query("select new study.springdatajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto>  findMemberDto();

    // 파라미터 바인딩1: 값
    @Query("select m from Member m where m.username = :username")
    List<Member> findByUsername2(@Param("username") String username); // @Param( "JPQL파라미터 이름 ")

    // 파라미터 바인딩2: 컬렉션 바인딩
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    /*
    반환타입은 스플링데이터JPA에서 알아서 인식
     */
    List<Member> findListByUsername(String username); // 컬렉션
    Member findMemberByUsername(String username); // 단건
    Optional<Member> findOptionalMemberByUsername(String username); // 단건 Optional


    /*
    페이징
     */
    Page<Member> findByAge(int age, Pageable pageable);

    /*
    벌크성 수정쿼리
     */
    @Modifying(clearAutomatically = true) // 어벧이트 쿼리문에는 꼭붙여줘야 업데이트가 된다. , clearAutomatically = true 벌크연산후 영속성 컨텍스트와 디비의 값이 다르기 때문에 자동 초기화 설정
    @Query("update Member m set m.age = m.age+1 where m.age >= 5")
    int bulkAgePlus(@Param("age") int age);
}
