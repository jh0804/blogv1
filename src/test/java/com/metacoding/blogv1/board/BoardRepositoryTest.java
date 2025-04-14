package com.metacoding.blogv1.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardRepository.class)
@DataJpaTest
public class BoardRepositoryTest {
    @Autowired // DI : JUnit에서는 생성자 주입이 불가능하다.
    private BoardRepository boardRepository;

    @Test
    public void findById_test() {
        //given
        Integer boardId = 1;
        //when
        Board board = boardRepository.findById(boardId);
        //eye
        System.out.println("board id : " + board.getId());
        System.out.println("board title : " + board.getTitle());
        System.out.println("board content : " + board.getContent());
        System.out.println("board createdAt : " + board.getCreatedAt());
        System.out.println("board nickname : " + board.getNickname());
    }

    @Test
    public void findAll_test() {
        //given

        //when
        List<Board> boardList = boardRepository.findAll();
        //eye
        for (Board board : boardList) {
            System.out.print(board.getId() + ", " + board.getTitle() + ", " + board.getContent() + ", " + board.getCreatedAt() + ", " + board.getNickname());
            System.out.println();
        }
    }

    @Test
    public void insert_test() {
        //given
        String title = "제목 test";
        String content = "내용 test";
        String nickname = "닉네임 test";

        // eye - insert 전
        System.out.println("insert 전");
        List<Board> boardList = boardRepository.findAll();
        for (Board board : boardList) {
            System.out.print(board.getId() + ", " + board.getTitle() + ", " + board.getContent() + ", " + board.getCreatedAt() + ", " + board.getNickname());
            System.out.println();
        }

        //when
        boardRepository.insert(title,content,nickname);

        //eye - insert 후
        System.out.println("insert 후");
        List<Board> boardList2 = boardRepository.findAll();
        System.out.println();
        for (Board board : boardList2) {
            System.out.print(board.getId() + ", " + board.getTitle() + ", " + board.getContent() + ", " + board.getCreatedAt() + ", " + board.getNickname());
            System.out.println();
        }
    }

    @Test
    public void deleteById_test() {
        //given
        Integer boardId = 1;

        // eye - delete 전
        System.out.println("delete 전");
        List<Board> boardList = boardRepository.findAll();
        for (Board board : boardList) {
            System.out.print(board.getId() + ", " + board.getTitle() + ", " + board.getContent() + ", " + board.getCreatedAt() + ", " + board.getNickname());
            System.out.println();
        }

        //when
        boardRepository.deleteById(boardId);

        //eye - delete 후
        System.out.println("delete 후");
        List<Board> boardList2 = boardRepository.findAll();
        System.out.println();
        for (Board board : boardList2) {
            System.out.print(board.getId() + ", " + board.getTitle() + ", " + board.getContent() + ", " + board.getCreatedAt() + ", " + board.getNickname());
            System.out.println();
        }
    }

    @Test
    public void update_test() {
        //given
        Integer boardId = 1;
        String title = "제목 update 됨";
        String content = "내용 update 됨";

        //when
        boardRepository.update(boardId, title, content);

        //eye - update 후
        List<Board> boardList2 = boardRepository.findAll();
        for (Board board : boardList2) {
            System.out.print(board.getId() + ", " + board.getTitle() + ", " + board.getContent() + ", " + board.getCreatedAt() + ", " + board.getNickname());
            System.out.println();
        }
    }

}