package study.springdatajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.springdatajpa.dto.MemberDto;
import study.springdatajpa.entity.Member;
import study.springdatajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("userA"));
    }

    /*
    도메인 컨버터 사용 전
     */
    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    /*
    도메인 컨버터 사용 후
     */
//    @GetMapping("/members/{id}")
//    public String findMember(@PathVariable("id") Member member) {
//        return member.getUsername();
//    }

    /*
    페이징과 정렬 예제
     */
    @GetMapping("/members") // /members?page=0&size=3&sort=id,desc&sort=username,desc
    //@PageableDefault : 개별 페이징 설정
    public Page<MemberDto> list(@PageableDefault(size = 5,  sort = "username", direction =  Sort.Direction.DESC) Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        // DTO로 변환
        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        return map;
    }

    /*
    페이징과 최적화
     */
    @GetMapping("/members/paging") // /members?page=0&size=3&sort=id,desc&sort=username,desc
    //@PageableDefault : 개별 페이징 설정
    public Page<MemberDto> list2(@PageableDefault(size = 5,  sort = "username", direction =  Sort.Direction.DESC) Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        // DTO로 변환
        Page<MemberDto> pageDto = page.map(MemberDto::new);
        return pageDto;
    }
}
