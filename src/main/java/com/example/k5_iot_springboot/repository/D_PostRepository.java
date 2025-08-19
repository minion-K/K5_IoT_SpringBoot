package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.entity.D_Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    Post와 Comment의 관계가 1 : N의 관계

    D_Post post = postRepository.findById(id).get();
    post.getComments.forEach(...); // 댓글 접근
    
    == 코드 풀이 ==
    1) 첫 번째 쿼리: SELECT * FROM posts WHERE id=?
    2) 두 번째 쿼리: LAZY 설정 코드를 "여러 번" 실행할 때마다 초기화를 위한 SELECT문이 별도로 실행

    ## 상황 1) 단일 Post만 조회하는 경우 ##
    -- 첫 번째 쿼리
        SELECT * FROM posts WHERE id=?
    -- 두 번째 쿼리: 이후 post.getComments() 처음 호출 시
        댓글 컬렉션 초기화용으로 딱 한 번 실행
        SELECT * FROM comments where post_id=?

    ## 상황 2) Post를 N개 먼저 가져온 뒤 각 Post마다 getComments()를 호출 ##
    -- 첫 번째 쿼리
        SELECT * FROM posts LIMIT 20;
    -- 두 번째 쿼리
        SELECT * FROM comments WHERE post_id=? (총 20번 실행)
    
    첫 번째 쿼리(1) + 두 번째 쿼리(N)
    >> 1 + 문제 발생
*/

@Repository
public interface D_PostRepository extends JpaRepository<D_Post, Long> {
//    게시글 조회 + 댓글까지 즉시 로딩
    
}
