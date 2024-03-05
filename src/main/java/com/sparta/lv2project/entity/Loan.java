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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    @Column(name = "returnStatus")
    private boolean returnStatus;

    public Loan(Book book, Member member) {
        this.member = member;
        this.book = book;
        this.returnStatus = false;
        this.setLoanDate();
    }

    public boolean getReturnStatus() {
        return this.returnStatus;
    }
}