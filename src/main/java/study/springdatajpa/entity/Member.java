package study.springdatajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 빈생성자 접근 protected로 설정
@ToString(of = {"id", "username", "age"}) // 객체 출력시 컬럼지정하는 어노테이션 ,
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

//    // pritected로 해야 하이버네이트가 나중에 변경하거나 참조할 수 있어. private은 안됌
//    protected Member() {
//    }

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team){
        this.username = username;
        this.age = age;
        if(team != null){ //TODO: 팀을 변결할 때는 반드시 메소드 만들어서 사용.
            changeTeam(team);
        }
    }

    /*
    연관관계 편의 메소드
     */
    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this); //TODO: 팀 수정하는 것인데add 인 이유 알아내기??
    }
}
