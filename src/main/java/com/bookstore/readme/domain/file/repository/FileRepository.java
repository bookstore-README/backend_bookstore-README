package com.bookstore.readme.domain.file.repository;

import com.bookstore.readme.domain.file.model.Files;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<Files, Long> {

}
