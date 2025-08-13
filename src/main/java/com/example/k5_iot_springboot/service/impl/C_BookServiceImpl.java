package com.example.k5_iot_springboot.service.impl;

import com.example.k5_iot_springboot.dto.C_Book.BookCreateRequestDto;
import com.example.k5_iot_springboot.dto.C_Book.BookResponseDto;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.service.C_BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class C_BookServiceImpl implements C_BookService {
    @Override
    public ResponseDto<BookResponseDto> createBook(BookCreateRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<List<BookResponseDto>> getAllBooks() {
        return null;
    }

    @Override
    public ResponseDto<BookResponseDto> getBookById(Long id) {
        return null;
    }

    @Override
    public ResponseDto<BookResponseDto> updateBook(Long id) {
        return null;
    }

    @Override
    public ResponseDto<BookResponseDto> deleteBook(Long id) {
        return null;
    }
}
