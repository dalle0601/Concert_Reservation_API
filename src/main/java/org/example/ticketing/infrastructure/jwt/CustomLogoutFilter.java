package org.example.ticketing.infrastructure.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ticketing.infrastructure.queue.QueueManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;
    private final QueueManager queueManager;


    public CustomLogoutFilter(JwtUtil jwtUtil, RedisTemplate<String, Object> redisTemplate, QueueManager queueManager) {

        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
        this.queueManager = queueManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!httpRequest.getRequestURI().equals("/logout") || !httpRequest.getMethod().equalsIgnoreCase("POST")) {
            chain.doFilter(request, response);
            return;
        }

        // 쿠키에서 refresh token 찾기
        String refreshToken = null;
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            // 토큰 만료 확인
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 토큰이 리프레시 토큰인지 확인
        if (!"refresh".equals(jwtUtil.getCategory(refreshToken))) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 사용자 ID 추출
        String userId = jwtUtil.getUsername(refreshToken); // JWT에서 사용자 ID 추출
        if (userId == null) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String key = "refreshToken:" + refreshToken;
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        redisTemplate.delete(key);

        queueManager.removeUserFromQueue(userId); // 대기열에서 사용자 삭제
        String tokenKey = "token:" + userId;
        redisTemplate.delete(tokenKey);

        Cookie deleteCookie = new Cookie("refresh", null);
        deleteCookie.setMaxAge(0);
        deleteCookie.setPath("/");
        httpResponse.addCookie(deleteCookie);

        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }
}