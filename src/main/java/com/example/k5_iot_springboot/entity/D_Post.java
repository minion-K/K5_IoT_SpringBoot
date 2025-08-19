package com.example.k5_iot_springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// access = AccessLevel.PROTECTED
// JPA 프록시 생성을 위한 기본 생성자
// : 외부에서 무분별하게 생성하지 못하도록 접근 수준을 PROTECTED로 제한
@AllArgsConstructor
@ToString(exclude = "comments")
// exclude = "comments"
// : 해당 속성 값의 필드를 제외하고 ToString 메서드 내에서 필드값 출력
public class D_Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("게시글 제목") // RDBMS 컬럼에 대한 주석 첨부
    @Column(nullable = false, length = 200)
    private String title;

    @Comment("게시글 내용")
    @Lob // 대용량 텍스트 저장 - RDBMS 에서 자동으로 TEXT(CLOB)으로 매핑
    @Column(nullable = false)
    private String content;

    @Comment("작성자 표시명 또는 ID")
    @Column(nullable = false, length = 100)
    private String author;

    //    OneToMany
//    - Post : Comment - 1 : N 관계에서 '1'쪽 매핑임을 지정
//    - 컬렉션 타입은 기본 LAZY 설정
//    >> 해당 어노테이션 내에서 세부옵션을 지정
    @OneToMany(
            mappedBy = "post",
//            : 관계의 주인은 D_Comment.post 필드임을 지정
//            : Post 내부에서는 읽기 전용 매핑, FK는 D_Comment과 연결된 테이블이 가짐
//            >> DBMS 에서 FK 설정은 Comment 에서 post의 PK를 참조하는 형식(주도권은 Comment에 있음)
            cascade = CascadeType.ALL,
//            : 부모(D_Post)에 대한 persist/merge/remove 등이 자식(D_Comment)로 전이
//            - 게시글 저장/삭제 시 댓글도 같이 처리
            orphanRemoval = true,
//            : 컬렉션에서 제거된 댓글은 고아 객체로 간주되어, DB 에서도 자동 삭제 (실제 DELETE 수행)
            fetch = FetchType.LAZY
//            : 컬렉션을 지연 로딩
//            - 댓글이 필요할 때만 실제 SELECT를 수행하여 불필요한 로딩을 방지
    )
    private List<D_Comment> comments = new ArrayList<>();
//    - 1 : N 관계 시 컬렉션은 NPE 방지를 위해 즉시 초기화
//    - JPA가 내부적으로 컬렉션 프록시로 교체 가능
//    cf) 프록시(중개자)

    //    === 생성/수정 메서드 ===
    private D_Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public static D_Post create(String title, String content, String author) {
        return new D_Post(title, content, author);
    }

    public void changeTitle(String title) { this.title = title; }
    public void changeContent(String title) { this.content = content; }
}
