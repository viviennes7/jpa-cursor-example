package com.ms.jpacursorexample;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private static final int DEFAULT_SIZE = 10;

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public CursorResult<Board> getBoards(Long cursorId, Integer size) {
        if (size == null) size = DEFAULT_SIZE;
        return this.boardService.get(cursorId, PageRequest.of(0, size));
    }

}
