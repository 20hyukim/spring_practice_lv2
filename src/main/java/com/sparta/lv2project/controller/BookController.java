package com.sparta.lv2project.controller;

import com.sparta.lv2project.dto.*;
import com.sparta.lv2project.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public BookResponseDto findBook(@PathVariable Long id){
        return bookService.findBook(id);
    }

    @GetMapping
    public List<BookResponseDto> getBooks(){
        return bookService.getBooks();
    }

    @PostMapping
    public BookResponseDto createBook(@RequestBody BookRequestDto bookRequestDto){
        return bookService.createBook(bookRequestDto);
    }

    @PostMapping("/member")
    public MemberResponseDto createMember(@RequestBody MemberRequestDto memberRequestDto) {
        return bookService.createMember(memberRequestDto);
    }

    @GetMapping("/{bookId}/{memberId}")
    public ResponseEntity<?> availableBook(@PathVariable Long bookId, @PathVariable Long memberId) {
        return bookService.availableBook(bookId, memberId);
    }

    @PutMapping("/{id}")
    public BookLoanResponseDto returnBook(@PathVariable Long id){
        return bookService.returnBook(id);
    }

    @GetMapping("/member")
    public List<BookLoanResponseDto> loanBook(){
        return bookService.loanBook();
    }

    @GetMapping("/members")
    public List<BookLoanResponseDto> loanAllBook() {
        return bookService.loanAllBook();
    }

    @GetMapping("/notloanedbook/{id}")
    public ResponseEntity<?> availableLoanBook(@PathVariable Long id) {
        return bookService.availableLoanBook(id);
    }
}
