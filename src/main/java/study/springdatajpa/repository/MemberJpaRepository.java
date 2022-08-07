package study.springdatajpa.repository;

import org.springframework.stereotype.Repository;
import study.springdatajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {// JPA기반 리파지토리

    @PersistenceContext
    private EntityManager em;

    // 회원가입
    public Member save(Member member){
        em.persist(member);
        return member;
    }

    public void delete(Member member){
        em.remove(member);
    }

    // 회원 조회
    public Member find(Long id){
        return em.find(Member.class, id); // (찾는 클래스, PK)
    }

    // 회원 조회
    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id); // (찾는 클래스, PK)
        return Optional.ofNullable(member);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public long count(){
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }


}
