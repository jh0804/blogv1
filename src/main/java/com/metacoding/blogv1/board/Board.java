package com.metacoding.blogv1.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

// runtime 때 만들어줌
@Getter // (Lombok)
@AllArgsConstructor // full 생성자
@NoArgsConstructor // default 생성자 만들어줌 (Lombok)
@Table(name = "board_tb") // table명 설정
@Entity // jpa가 관리할 수 있게 설정
public class Board {

    @Id // pk 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 설정
    private Integer id;
    private String title;
    private String content;
    private Timestamp createdAt; // now
    private String nickname;
}
