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
public class LoanTimeStamped {

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime loanDate;

    @Column(name = "returnDate")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime returnDate;

    public void setReturnDate() {
        this.returnDate = LocalDateTime.now();
    }

    public void setLoanDate() {
        this.loanDate = LocalDateTime.now();
    }
}
