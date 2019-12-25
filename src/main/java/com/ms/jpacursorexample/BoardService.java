package com.ms.jpacursorexample;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getBoards(@Nullable Long id, Pageable page) {
        final List<Board> boards;

        if (id == null) {
            boards = this.boardRepository.findAllByOrderByIdDesc(page);
        } else {
            boards = this.boardRepository.findByIdLessThanOrderByIdDesc(id, page);
        }

        return boards;
    }
}
