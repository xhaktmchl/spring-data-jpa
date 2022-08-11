package study.springdatajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass // 상속이 아니라 각 엔티티마다 실제 필드처럼 상속하게 하는 것.
@Getter
public class JpaBaseEntity {

    @Column(updatable = false) // 수정 금지
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist // persist 되기전 작동
    public void prePresist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate // 업데이트 되기 전 작동
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }
}
