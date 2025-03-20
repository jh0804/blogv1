package com.metacoding.blogv1.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// 책임 : DB와 소통하는 친구
@Repository // IoC 컬렉션에 뜬다. (BoardController도 IoC에 뜬다
public class BoardRepository {

    private EntityManager em;

    // DI -> IoC 순회해서 EntityManager 타입으로 찾아서 전달해준다.
    public BoardRepository(EntityManager em) {
        System.out.println("BoardRepository new 됨");
        this.em = em;
    }

    public void insert(String title, String content, String nickname) {
        Query query = em.createNativeQuery("insert into board_tb(title, content, created_at, nickname) values(?,?,now(),?)");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, nickname);
        query.executeUpdate(); // insert,update,delete
    }

    public List<Board> findAll() {
        // Board.class 추가 -> board class로 자동매핑
        Query query = em.createNativeQuery("select * from board_tb order by id desc", Board.class);
        List<Board> boardList = query.getResultList(); // 조회할 때

        return boardList;
    }

    public Board findById(int id) {
        Query query = em.createNativeQuery("select * from board_tb where id = ?", Board.class);
        query.setParameter(1, id);
        try {
            Board board = (Board) query.getSingleResult(); // getSingleResult의 return 타입이 Object이므로 다운캐스팅해야됨
            return board;
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id = ?"); // 조회하는 게 아니므로 매핑 필요 없음
        query.setParameter(1, id);
        query.executeUpdate();
    }

    public void update(int id, String title, String content) {
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ? where id = ?");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);
        query.executeUpdate();
    }
}