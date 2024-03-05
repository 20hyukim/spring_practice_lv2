package com.sparta.lv2project.repository;

import com.sparta.lv2project.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {
}


