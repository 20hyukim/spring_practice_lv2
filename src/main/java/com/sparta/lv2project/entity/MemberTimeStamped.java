package com.sparta.lv2project.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class MemberTimeStamped {

    @Column(name = "penalty")
    private LocalDateTime penalty;

    public void setPenalty() {
        this.penalty = LocalDateTime.now();
    }
}
