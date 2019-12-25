package com.ms.jpacursorexample;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class JpaCursorExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaCursorExampleApplication.class, args);
    }

    @Component
    public static class DummyLoader implements ApplicationRunner {

        private final BoardRepository boardRepository;

        public DummyLoader(BoardRepository boardRepository) {
            this.boardRepository = boardRepository;
        }

        @Override
        public void run(ApplicationArguments args) {
            final List<Board> boards = IntStream.range(1, 30) // 1 ~ 29
                    .mapToObj(i ->
                            new Board("제목" + i, "컨텐트" + i)
                    )
                    .collect(Collectors.toList());

            this.boardRepository.saveAll(boards);
        }
    }
}
