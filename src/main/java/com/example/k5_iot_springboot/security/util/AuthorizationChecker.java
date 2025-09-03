package com.example.k5_iot_springboot.security.util;

import com.example.k5_iot_springboot.common.enums.OrderStatus;
import com.example.k5_iot_springboot.entity.G_Article;
import com.example.k5_iot_springboot.repository.G_ArticleRepository;
import com.example.k5_iot_springboot.repository.H_OrderRepository;
import com.example.k5_iot_springboot.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

// cf) 역할 체크 VS 소유자 검사 및 리포지토리 접근 체크
// 1. 역할 체크
//      : @PreAuthorize("hasRole('ADMIN')")만으로 충분
// 2. 소유자 검사(게시글 작성자만 수정/삭제 가능)
//      , 리포지토리 접근이 필요한 조건(팀원 여부, 프로젝트 멤버쉽)이 있다면 
//      >> 컨트롤러/서비스에 비즈니스 로직을 섞지 않기 위해 빈(Bean)으로 분리 권장
@Component("authz")
@RequiredArgsConstructor
public class AuthorizationChecker {
    private final G_ArticleRepository articleRepository;
    private final H_OrderRepository orderRepository;

    /** principal(LoginId)이 해당 articleId의 작성자인지 검사 */
    public boolean isArticleAuthor(Long articleId, Authentication principal) {
        if (principal == null || articleId == null) return false;
        String loginId = principal.getName(); // JwtAuthenticationFilter 에서 username 으로 주입
        G_Article article = articleRepository.findById(articleId).orElse(null);
        if(article == null) return false;
        return article.getAuthor().getLoginId().equals(loginId);
//        loginId와 article의 작성자가 일치하면 true 반환, 아닐 경우 false 반환
    }

    /** USER가 본인의 주문만을 조회/검색할 수 있도록 체크 */
    public boolean isSelf(Long userId, Authentication authentication) {
        if(userId == null) return false;

        Long me = extractUserId(authentication);

        return userId.equals(me);
    }

    /** User가 해당 주문을 취소할 수 있는지 확인 (본인 & PENDING(대기) */
    public boolean canCancel(Long orderId, Authentication authentication) {
        Long me = extractUserId(authentication);
        return orderRepository.findById(orderId)
                .map(order -> order.getUser().getId().equals(me)
                        && order.getOrderStatus() == OrderStatus.PENDING)
                .orElse(false);
    }
    
//    == 프로젝트의 Principal 구조에 맞게 사용자 ID 추출 == //
    private Long extractUserId(Authentication authentication) {
        if(authentication == null) return null;
        Object principal = authentication.getPrincipal();

//        1) 커스텀 Principal 사용하는 경우
        if(principal instanceof UserPrincipal up) {
            return up.getId();
        }
//        2) 다운캐스팅 실패 시 null 반환 - UserPrincipal이 아닐 때 fallback (거의 안 씀)
        return null;
    }
}
