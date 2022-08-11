package study.springdatajpa.dto;

import lombok.Data;
import study.springdatajpa.entity.Member;

@Data
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

    // 생성자
    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    public MemberDto(Member m) {
        this.id = m.getId();
        this.username = m.getUsername();
    }
}
