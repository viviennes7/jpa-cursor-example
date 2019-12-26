package com.ms.jpacursorexample;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    CursorResult<Board> get(Long cursorId, Pageable page) {
        final List<Board> boards = getBoards(cursorId, page);
        final Long lastIdOfList = boards.isEmpty() ?
                null : boards.get(boards.size() - 1).getId();

        return new CursorResult<>(boards, hasNext(lastIdOfList));
    }

    private List<Board> getBoards(Long id, Pageable page) {
        return id == null ?
                this.boardRepository.findAllByOrderByIdDesc(page) :
                this.boardRepository.findByIdLessThanOrderByIdDesc(id, page);
    }

    private Boolean hasNext(Long id) {
        if (id == null) return false;
        return this.boardRepository.existsByIdLessThan(id);
    }
}
