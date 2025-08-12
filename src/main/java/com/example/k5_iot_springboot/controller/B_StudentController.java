package com.example.k5_iot_springboot.controller;

import com.example.k5_iot_springboot.entity.B_Student;
import com.example.k5_iot_springboot.service.B_StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

// cf) RESTful API : REST API를 잘 따르는 아키텍처 스타일
// cf) RequestMapping 
//      : 주로 버저닝(/api/v1) + 복수형태의 명사(/students) 같이 사용
@RestController // controller + ResponseBody (RESTful 웹 서비스의 컨트롤러 임을 명시)
@RequestMapping("/api/v1/students") // 해당 클래스 파일의 공동 URL prefix (아래 메서드 경로는 모두 /students로 시작)
@RequiredArgsConstructor // 생성자 주입 어노테이션
public class B_StudentController {
//    비즈니스 로직을 처리하는 service 객체 주입 (생성자 주입)
    private final B_StudentService studentService;
    
//    응답과 요청에 대한 데이터 정의 (Request & Response)

//    1) 새로운 학생 등록(Post)
//    - 성공 201 Created + Location 헤더(/student/{id} + 생성 데이터
//    cf) 리소스 생성 성공은 201 Created가 표준
    @PostMapping
    public B_Student createStudent(@RequestBody B_Student student
            , UriComponentsBuilder uriComponentsBuilder) {
        B_Student created = studentService.createStudent(student);

//        Location 헤더 설정
//        : 서버의 응답이 다른 곳에 있음을 알려주고 해당 위치(URI)를 지정
//        - 리다이렉트 할 페이지의 URL을 나타냄
//        - 201 (Created), 3XX (redirection) 응답 상태와 주로 사용
        URI location = uriComponentsBuilder // 현재의 HTTP 요청의 정보를 바탕으로 설정
                
                .path("/{id}")// 현재 경로 + /{id} 
                .buildAndExpand(created.getId())// 템플릿 변수 치환, 동적 데이터 처리
                .toUri();

        return ResponseEntity.created(location).body(created);
    }


}
