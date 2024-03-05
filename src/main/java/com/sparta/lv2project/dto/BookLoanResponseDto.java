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
        this.name = loan.getMemberName();
        this.telephone = loan.getTelephone();
        this.title = loan.getTitle();
        this.author = loan.getAuthor();
    }
}
