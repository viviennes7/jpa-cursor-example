package com.ms.jpacursorexample;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BoardServiceTest {

    private static final Pageable page = PageRequest.of(0, 10);

    @Autowired
    private BoardService boardService;

    /**
     * @see JpaCursorExampleApplication.DummyLoader#run(ApplicationArguments) 에서 게시글 생성함
     */
    @Test
    void cursor방식_조회() {
        final CursorResult<Board> firstSelection = this.boardService.get(null, page);
        final Long lastIndexOfFirst = firstSelection.getValues().get(9).getId();
        assertThat(lastIndexOfFirst).isEqualTo(20);
        assertThat(firstSelection.getValues().size()).isEqualTo(10);
        assertTrue(firstSelection.getHasNext());

        final CursorResult<Board> secondSelection = this.boardService.get(lastIndexOfFirst, page);
        final Long lastIndexOfSecond = secondSelection.getValues().get(9).getId();
        assertThat(lastIndexOfSecond).isEqualTo(10);
        assertThat(secondSelection.getValues().size()).isEqualTo(10);
        assertTrue(secondSelection.getHasNext());

        final CursorResult<Board> thirdSelection = this.boardService.get(lastIndexOfSecond, page);
        final Long lastIndexOfThird = thirdSelection.getValues().get(8).getId();
        assertThat(lastIndexOfThird).isEqualTo(1);
        assertThat(thirdSelection.getValues().size()).isEqualTo(9);
        assertFalse(thirdSelection.getHasNext());
    }

    @Test
    void 조회결과물이_없는_경우() {
        final CursorResult<Board> result = this.boardService.get(Long.MIN_VALUE, page);
        assertThat(result.getValues()).isEmpty();
        assertFalse(result.getHasNext());
    }

}