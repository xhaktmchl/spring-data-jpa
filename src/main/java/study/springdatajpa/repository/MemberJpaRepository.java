package study.springdatajpa.repository;

import org.springframework.stereotype.Repository;
import study.springdatajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberJpaRepository {// JPA기반 리파지토리

    @PersistenceContext
    private EntityManager em;

    // 회원가입
    public Member save(Member member){
        em.persist(member);
        return member;
    }

    // 회원 조회
    public Member find(Long id){
        return em.find(Member.class, id); // (찾는 클래스, PK)
    }
}
