package com.metacoding.blogv1.board;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// 책임 : 요청 잘 받고 응답 잘 하고
@Controller // 컴퍼넌트 스캔 -> DS가 활용
public class BoardController {

    // DI
    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // update board_tb set title(body로 받음)=?, content(body로 받음)=? where id(주소로 받음) =?
    // 주소로 받는 데이터는 전부 where에 걸린다. (pk,unique는 /1로, queryString
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable("id") int id, String title, String content) {
        boardService.게시글수정하기(id, title, content); // 수정(update)은 select가 아니므로 model이 필요 없음
        return "redirect:/board/" + id;
    }

    @PostMapping("/board/{id}/delete") // 원래는 DeleteMapping이지만 JS를 안배워서 PostMapping으로
    public String delete(@PathVariable("id") int id) {
        // System.out.println("id : " + id); // id로 db 가서 삭제하면 됨 -> 이건 controller의 책임이 아님
        boardService.게시글삭제(id); // return 받을게 없음
        return "redirect:/";
    }

    @PostMapping("/board/save")
    public String save(String title, String content, String nickname) { // input 태그의 name값과 같아야 한다. // -> req.getParameter()와 같다
        // return "list"; -> 안되는 이유) db 연결 후 데이터 뿌릴때 문제 생김(db 데이터가 안들어감) - 모델이 데이터 안담고 간다
        boardService.게시글쓰기(title, content, nickname);
        return "redirect:/"; // page로 가는 주소가 만들어져 있으면 무조건 redirection (재활용 하세용)
    }

    // c - model - v
    @GetMapping("/")
    public String list(HttpServletRequest request) {
        // DB 조회해서 화면에 뿌려줘야 됨 -> service에게 요청
        List<Board> boardList = boardService.게시글목록(); // boardList는 model이다
        request.setAttribute("models", boardList); // request에 담기
        return "list"; // forward하기 // = req.getRequestDispatcher("list").forward(req,resp)
    }

    // model을 request에 담아야지 ->
    @GetMapping("/board/{id}") // 패턴 매칭 /board/1, /board/2
    public String detail(@PathVariable("id") int id, HttpServletRequest request) {
        Board board = boardService.게시글상세보기(id); // board는 model이다
        request.setAttribute("model", board);
        return "detail";
    }

    // c - v
    @GetMapping("/board/save-form") // 주소 (하이픈(-) 사용)
    public String saveForm() { // 자바는 카멜 표기법ㅇ, url에는 _,카멜 안씀
        return "save-form"; // 확장자 없어도 되는 이유 - 무조건 viewResolver를 탄다!
    }

    // ex)게시글 3번 수정하기
    @GetMapping("/board/{id}/update-form") // URI
    public String updateForm(@PathVariable("id") int id, HttpServletRequest request) {
        Board board = boardService.게시글상세보기(id); // 이거를 request에 담자
        request.setAttribute("model", board);
        return "update-form"; // 파일명
    }
}
