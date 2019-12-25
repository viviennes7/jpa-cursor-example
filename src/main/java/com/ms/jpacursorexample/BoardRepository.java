package com.ms.jpacursorexample;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    public List<Board> findAllByOrderByIdDesc(Pageable page);

    public List<Board> findByIdLessThanOrderByIdDesc(Long id, Pageable page);
}
