package com.sparta.lv2project.service;

import com.sparta.lv2project.dto.*;
import com.sparta.lv2project.entity.Book;
import com.sparta.lv2project.entity.Loan;
import com.sparta.lv2project.entity.Member;
import com.sparta.lv2project.repository.BookRepository;
import com.sparta.lv2project.repository.LoanRepository;
import com.sparta.lv2project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final LoanRepository loanRepository;

    @Autowired
    private final MemberRepository memberRepository;
    public BookService(BookRepository bookRepository, LoanRepository loanRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
    }



    public BookResponseDto findBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 책은 존재하지 않습니다.")
        );
        return new BookResponseDto(book);
    }

    public List<BookResponseDto> getBooks() {
        return bookRepository.findAll(Sort.by("id")).stream().map(BookResponseDto::new).toList();
    }

    @Transactional
    public BookResponseDto createBook(BookRequestDto bookRequestDto) {
        Book book = new Book(bookRequestDto);
        Book saveBook = bookRepository.save(book);

        BookResponseDto bookResponseDto = new BookResponseDto(book);
        return bookResponseDto;

    }

    @Transactional
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
        Member member = new Member(memberRequestDto);
        Member saveMember = memberRepository.save(member);

        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return memberResponseDto;

    }

    @Transactional
    public ResponseEntity<?> availableBook(Long bookId, Long memberId) {
        if (!bookRepository.findById(bookId).isPresent()) {
            return ResponseEntity.badRequest().body("책을 찾을 수 없습니다.");
        }
        Book book = bookRepository.findById(bookId).get();

        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if(!memberOptional.isPresent()) {
            return ResponseEntity.badRequest().body("회원을 찾을 수 없습니다.");
        }
        Member member = memberOptional.get();

        Optional<Loan> existingLoan = loanRepository.findByMemberIdAndReturnStatus(memberId, false);
        if(existingLoan.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("기존에 대출한 책이 미반납 상태입니다.");
        } else {
            Loan loan = new Loan();
            loan.setBookId(bookId);
            loan.setMemberId(memberId);
            loan.setMemberName(member.getName());
            loan.setTelephone(member.getTelephone());
            loan.setTitle(book.getTitle());
            loan.setAuthor(book.getAuthor());
            loan.setReturnStatus(false);
            loan.setLoanDate();

            loanRepository.save(loan);

            return ResponseEntity.ok().body("대출 하였습니다.");
        }
    }

    @Transactional
    public BookLoanResponseDto returnBook(Long id) {
        if (loanRepository.findById(id).isPresent()) {
            Loan loan = loanRepository.findById(id).get();
            if (loan.getReturnStatus(loan)) {
                throw new IllegalArgumentException("해당 책은 대출되지 않았습니다.");
            }
            loan.setReturnStatus(true);
            loan.setReturnDate();
            BookLoanResponseDto bookLoanResponseDto = new BookLoanResponseDto(loan);
            return bookLoanResponseDto;

        } else {
            throw new IllegalArgumentException("해당 책은 대출되지 않았습니다.");
        }
    }

    public List<BookLoanResponseDto> loanBook() {
        return loanRepository.findAll(Sort.by("loanDate")).stream().map(BookLoanResponseDto::new).toList();
    }

}
