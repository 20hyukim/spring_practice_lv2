package com.sparta.lv2project.dto;

import com.sparta.lv2project.entity.Loan;
import lombok.Getter;

@Getter
public class BookLoanResponseDto {
    private String name;
    private Long telephone;
    private String title;
    private String author;

    public BookLoanResponseDto(Loan loan) {
        this.name = loan.getMember().getName();
        this.telephone = loan.getMember().getTelephone();
        this.title = loan.getBook().getTitle();
        this.author = loan.getBook().getAuthor();
    }
}
