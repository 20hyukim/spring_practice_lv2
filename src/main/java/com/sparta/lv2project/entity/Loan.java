package com.sparta.lv2project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="loan")
public class Loan extends LoanTimeStamped {
    @Id
    private Long bookId;
    @Column(name = "memberId", nullable = false)
    private Long memberId;
    @Column(name = "returnStatus")
    private boolean returnStatus;
    @Column(name = "memberName")
    private String memberName;
    @Column(name = "telephone")
    private Long telephone;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;


    public boolean getReturnStatus(Loan loan) {
        return loan.returnStatus;
    }
}