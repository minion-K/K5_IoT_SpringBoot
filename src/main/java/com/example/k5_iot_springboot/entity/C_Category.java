package com.example.k5_iot_springboot.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum C_Category {
//    각 Enum 상수
//    : DB에 저장될 값(DB Value)과 화면 표시용 값(label)을 함께 정의
    NOVEL("NOVEL", "소설"),
    ESSAY("ESSAY", "에세이"),
    POEM("POEM", "시"),
    MAGAZINE("MAGAZINE", "잡지");

    private final String dbValue;
    private final String label;

    /*
        1) JSON 직렬화 시 동작할 메서드
        : Enum >> String 
        : Enum 객체를 JSON 응답으로 변환할 때, dbValue 값을 그대로 전달
        EX) C_Category.NOVER >>> "NOVEL"
    */
    @JsonValue
    public String toJson() {
        return dbValue;
    }
    /*
        2) JSON 역직렬화 시 동작할 메서드
        : String >> Enum
        : JSON 요청 값을 Enum으로 변환할 때, Enum 이름(name)과 DB 값(dbValue) 모두 인식
        - 대소문자 구분 X, 값이 없거나 잘못된 경우 예외 발생
    */
    

}
