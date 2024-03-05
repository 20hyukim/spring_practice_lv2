package com.sparta.lv2project.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BookTimeStamped {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registrationDate;
}
