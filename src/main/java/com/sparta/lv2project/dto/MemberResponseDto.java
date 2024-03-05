package com.sparta.lv2project.dto;

import com.sparta.lv2project.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String name;
    private Character gender;
    private Long telephone;
    private String address;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.gender = member.getGender();
        this.telephone = member.getTelephone();
        this.address = member.getAddress();

    }
}
