package com.example.k5_iot_springboot.service.impl;

import com.example.k5_iot_springboot.dto.H_Order.request.ProductRequest;
import com.example.k5_iot_springboot.dto.H_Order.response.ProductResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.entity.H_Product;
import com.example.k5_iot_springboot.repository.H_ProductRepository;
import com.example.k5_iot_springboot.security.UserPrincipal;
import com.example.k5_iot_springboot.service.H_ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class H_ProductServiceImpl implements H_ProductService {
    public final H_ProductRepository productRepository;

    @Override
    @PreAuthorize("hasrole('ADMIN')")
    @Transactional
    public ResponseDto<ProductResponse.DetailResponse> create(UserPrincipal principal, ProductRequest.@Valid Create request) {
        ProductResponse.DetailResponse data = null;

        H_Product product = H_Product.builder()
                .name(request.name())
                .price(request.price())
                .build();

        H_Product saved = productRepository.save(product);
        data = new ProductResponse.DetailResponse(saved.getId(), saved.getName(), saved.getPrice());

        return ResponseDto.setSuccess("제품이 성공적으로 등록되었습니다.", data);

    }

    @Override
    @PreAuthorize("hasrole('ADMIN')")
    @Transactional
    public ResponseDto<ProductResponse.DetailResponse> update(Long productId, UserPrincipal principal, ProductRequest.@Valid Update request) {
        ProductResponse.DetailResponse data = null;

        H_Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        if(request.name() != null && request.price() != null) {
            throw new IllegalArgumentException("제품을 수정할 데이터가 없습니다.");
        }

        if(request.name() != null) product.setName(request.name());
        if(request.price() != null) product.setPrice(request.price());

        data = new ProductResponse.DetailResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );

        return ResponseDto.setSuccess("제품이 성공적으로 수정되었습니다.", data);
    }

    @Override
    public ResponseDto<ProductResponse.DetailResponse> getProductById(Long productId) {
        ProductResponse.DetailResponse data = null;

        H_Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다"));

        data = new ProductResponse.DetailResponse(product.getId(), product.getName(), product.getPrice());

        return ResponseDto.setSuccess("제품이 성공적으로 조회되었습니다.", data);
    }
}
