package com.example.k5_iot_springboot.service;

import com.example.k5_iot_springboot.dto.E_Board.request.BoardRequestDto;
import com.example.k5_iot_springboot.dto.E_Board.response.BoardResponseDto;
import com.example.k5_iot_springboot.dto.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

public interface E_BoardService {
    ResponseDto<BoardResponseDto.DetailResponse> createBoard(BoardRequestDto.@Valid CreateRequest request);

    ResponseDto<List<BoardResponseDto.SummaryResponse>> getAllBoards();

    ResponseDto<BoardResponseDto.DetailResponse> updateBoard(Long boardId, BoardRequestDto.@Valid UpdateRequest request);
}
