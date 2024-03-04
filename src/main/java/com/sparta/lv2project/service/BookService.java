package com.sparta.lv2project.service;

import com.sparta.lv2project.dto.BookRequestDto;
import com.sparta.lv2project.dto.BookResponseDto;
import com.sparta.lv2project.dto.MemberRequestDto;
import com.sparta.lv2project.dto.MemberResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    
    public BookResponseDto findBook(Long id) {
    }

    public List<BookResponseDto> getBooks() {
    }

    public BookResponseDto createBook(BookRequestDto bookRequestDto) {
    }

    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
    }

    public ResponseEntity<?> availableBook(Long bookId, Long memberId) {
    }
}
