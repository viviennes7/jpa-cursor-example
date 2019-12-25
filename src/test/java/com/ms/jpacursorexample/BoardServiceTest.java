package com.ms.jpacursorexample;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BoardServiceTest {

    private static final Pageable page = PageRequest.of(0, 10);

    @Autowired
    private BoardService boardService;

    @Test
    void cursor방식_조회() {
        final List<Board> firstSelection = this.boardService.getBoards(null, page);
        final Long lastIndexOfFirst = firstSelection.get(9).getId();
        assertThat(lastIndexOfFirst).isEqualTo(20);
        assertThat(firstSelection.size()).isEqualTo(10);

        final List<Board> secondSelection = this.boardService.getBoards(lastIndexOfFirst, page);
        final Long lastIndexOfSecond = secondSelection.get(9).getId();
        assertThat(lastIndexOfSecond).isEqualTo(10);
        assertThat(secondSelection.size()).isEqualTo(10);

        final List<Board> thirdSelection = this.boardService.getBoards(lastIndexOfSecond, page);
        final Long lastIndexOfThird = thirdSelection.get(8).getId();
        assertThat(lastIndexOfThird).isEqualTo(1);
        assertThat(thirdSelection.size()).isEqualTo(9);
    }
}