package com.sparta.lv2project.repository;

import com.sparta.lv2project.entity.Book;
import com.sparta.lv2project.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByMemberIdAndReturnStatus(Long userId, boolean returnStatus);
    List<Loan> findByBookId(Long bookId);
    Optional<Loan> findByBookIdAndReturnStatus(Long bookId, boolean returnStatus);
}


