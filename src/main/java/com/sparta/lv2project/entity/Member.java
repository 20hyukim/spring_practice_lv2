package com.sparta.lv2project.entity;

import com.sparta.lv2project.dto.MemberRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="member")
public class Member extends BookTimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "gender", nullable = false)
    private Character gender;
    @Column(name = "registrationId", nullable = false)
    private Long registrationId;
    @Column(name = "telephone")
    private Long telephone;
    @Column(name = "address")
    private String address;

    public Member(MemberRequestDto memberRequestDto) {
        this.name = memberRequestDto.getName();
        this.gender = memberRequestDto.getGender();
        this.registrationId = memberRequestDto.getRegistrationId();
        this.telephone = memberRequestDto.getTelephone();
        this.address = memberRequestDto.getAddress();
    }

}