package com.example.k5_iot_springboot.controller;

import com.example.k5_iot_springboot.common.constants.APIMappingPattern;
import com.example.k5_iot_springboot.dto.E_Board.request.BoardRequestDto;
import com.example.k5_iot_springboot.dto.E_Board.response.BoardResponseDto;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.service.E_BoardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIMappingPattern.Boards.ROOT)
@RequiredArgsConstructor
@Validated
public class E_BoardController {
    private final E_BoardService boardService;

//    1) 게시글 생성
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto<BoardResponseDto.DetailResponse>> createBoard(
            @Valid @RequestBody BoardRequestDto.CreateRequest request
            ) {
        ResponseDto<BoardResponseDto.DetailResponse> response = boardService.createBoard(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
//    2) 게시글 조회 (전체 조회)
    @PreAuthorize("hasAnyRole('USER','MANAGER', 'ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<BoardResponseDto.SummaryResponse>>> getAllBoards() {
        ResponseDto<List<BoardResponseDto.SummaryResponse>> response = boardService.getAllBoards();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    2-1 게시글 조회 (페이지네이션 Offset 조회)
    @PreAuthorize("hasAnyRole('USER','MANAGER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<ResponseDto<BoardResponseDto.PageResponse>> getBoardsPage(
//            page: 0부터 시작, 필요 시 1부터 시작하는 정책도 가능
            @RequestParam(defaultValue = "0") @Min(0) int page,
//            size: 최대 100 제한, 과도한 요청 금지
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
//            sort: 여러 개 허용 - EX) sort=createAt,desc&sort=title,asc
            @RequestParam(required = false, value = "sort") List<String> sort

    ) {
        String[] sortParams = (sort != null && !sort.isEmpty()) ? sort.toArray(new String[0]) : null;

        ResponseDto<BoardResponseDto.PageResponse> response = boardService.getBoardsPage(page, size, sortParams);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


//    2-2) 게시글 조회 (페이지네이션 Cursor 조회)
    @PreAuthorize("hasAnyRole('USER','MANAGER', 'ADMIN')")
    @GetMapping("/cursor")
    public ResponseEntity<ResponseDto<BoardResponseDto.SliceResponse>> getBoardsByCursor(
//            처음 요청이면 null >> 가장 최신부터 시작을 의미
//            : 목록을 항상 하나의 정렬 기준으로 고정 (id DESC - 최신 글 먼저)
//            > 다음 페이지를 가져올 때는 기준 커서보다 더 오래된 (작은 id) 행만 가져오기
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        ResponseDto<BoardResponseDto.SliceResponse> response = boardService.getBoardByCursor(cursorId, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    3) 게시글 수정
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping(APIMappingPattern.Boards.ID_ONLY)
    public ResponseEntity<ResponseDto<BoardResponseDto.DetailResponse>> updateBoard(
            @PathVariable Long boardId,
            @Valid @RequestBody BoardRequestDto.UpdateRequest request
    ) {
        ResponseDto<BoardResponseDto.DetailResponse> response = boardService.updateBoard(boardId, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
//    4) 게시글 삭제
//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping(APIMappingPattern.Boards.ID_ONLY)
}
