package com.sparta.lv2project.service;

import com.sparta.lv2project.dto.*;
import com.sparta.lv2project.entity.Book;
import com.sparta.lv2project.entity.Loan;
import com.sparta.lv2project.entity.Member;
import com.sparta.lv2project.repository.BookRepository;
import com.sparta.lv2project.repository.LoanRepository;
import com.sparta.lv2project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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
        if(member.getPenalty() != null){
            LocalDate penaltyDays = member.getPenalty().toLocalDate();
            if(passedDays(penaltyDays) <= 14){
                String message = String.format("연체 패날티 : %d일 후에 대출할 수 있습니다.", 14-passedDays(penaltyDays));
                return ResponseEntity.badRequest().body(message);
            }
        }

        Optional<Loan> returnStatusLoan = loanRepository.findByBookIdAndReturnStatus(bookId, false);
        if(returnStatusLoan.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이 책은 현재 대출 중입니다.");
        }



        Optional<Loan> existingLoan = loanRepository.findByMemberIdAndReturnStatus(memberId, false);
        if(existingLoan.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("기존에 대출한 책이 미반납 상태입니다.");
        } else {
            Loan loan = new Loan(book, member);
            loanRepository.save(loan);

            return ResponseEntity.ok().body("대출 하였습니다.");
        }
    }

    @Transactional
    public BookLoanResponseDto returnBook(Long id) {
        List<Loan> loans = loanRepository.findByBookId(id);
        if (loans.isEmpty()) {
            throw new IllegalArgumentException("해당 책은 대출되지 않았습니다.");
        }

        for (Loan loan : loans) {
            if (!loan.getReturnStatus()){
                loan.setReturnStatus(true);
                loan.setReturnDate(LocalDateTime.now());

                LocalDate loanDate = loan.getLoanDate().toLocalDate();
                if(passedDays(loanDate) > 7) {
                    Member member = loan.getMember();
                    member.setPenalty();
                    memberRepository.save(member);
                }
                BookLoanResponseDto bookLoanResponseDto = new BookLoanResponseDto(loan);
                return bookLoanResponseDto;
            }
        }
        throw new IllegalArgumentException("모든 대출 기록이 이미 반납되었습니다.");
    }

    public List<BookLoanResponseDto> loanBook() {
        return loanRepository.findAll(Sort.by("loanDate")).stream()
                .filter(loan -> !loan.getReturnStatus())
                .map(BookLoanResponseDto::new).toList();
    }


    public List<BookLoanResponseDto> loanAllBook() {
        return loanRepository.findAll(Sort.by("loanDate")).stream().map(BookLoanResponseDto::new).toList();
    }

    public ResponseEntity<?> availableLoanBook(Long id) {
        List<Loan> loans = loanRepository.findByBookId(id);
        if (loans.isEmpty()) {
            return ResponseEntity.ok().body("대출 가능합니다.");
        }
        for (Loan loan : loans) {
            if(!loan.getReturnStatus()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("대출 불가능합니다. 이미 대출 중입니다.");
            }
        }
        return ResponseEntity.ok().body("대출 가능합니다.");
    }

    public int passedDays(LocalDate loanDate){
        LocalDate today = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(loanDate, today);
        return (int) daysBetween;
    }


}
