package com.sparta.lv2project.dto;

import lombok.Getter;

@Getter
public class MemberRequestDto {
    private String name;
    private Long registrationId;
    private Long telephone;
    private String address;
    private Character gender;
}
