package com.metacoding.blogv1.board;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 책임: 트랜잭션 처리, 비지니스 로직 처리 (ex)송금 중 잔액 검사)
@Service // IoC
public class BoardService {

    private BoardRepository boardRepository;

    // DI : 의존성 주입 -> IoC로부터 들고옴 (BR는 반드시 IoC에 떠있어야 함)
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // boardRepository는 ioc에 떠있으니까 DI받아야됨
    @Transactional // 트랜잭션 시작 -> 함수 내부가 다 수행되면 commit, 실패 rollback
    public void 게시글쓰기(String title, String content, String nickname) {
        boardRepository.insert(title, content, nickname);
    }

    // write(insert,update,delete)만 commit/rollBack -> 트랜잭셔널 없어도 됨
    public List<Board> 게시글목록() {
        List<Board> boardList = boardRepository.findAll();

        return boardList; // 그냥 boardRepository.findAll() 바로 리턴해도 됨 할 일 없으니까 바로 위임 가능
    }

    public Board 게시글상세보기(int id) {
        return boardRepository.findById(id); // 할 일 없으니까 바로 위임
    }

    @Transactional
    public void 게시글삭제(int id) {
        // 1. 게시글이 존재하는지 확인
        Board board = boardRepository.findById(id);
        // 2. 삭제
        if (board == null) {
            throw new RuntimeException("게시글이 없는데 왜 삭제를 ㅠㅠ");
        }

        boardRepository.deleteById(id);
    } // commit (exception 터지면 rollback)

    // 삭제&수정 - 있어야 하지! => 있는지 체킹
    @Transactional
    public void 게시글수정하기(int id, String title, String content) {
        // 1. 게시글이 존재하는지 확인
        Board board = boardRepository.findById(id);
        // 2. (일단 터트리기만 하기 나중에 배웁니다~)
        if (board == null) {
            throw new RuntimeException("게시글이 없는데 왜 수정을 ㅠㅠ");
        }
        // 3. 수정
        boardRepository.update(id, title, content);
    } // commit
}
