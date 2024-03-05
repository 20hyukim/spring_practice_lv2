package com.sparta.lv2project.repository;

import com.sparta.lv2project.entity.Book;
import com.sparta.lv2project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {
}


