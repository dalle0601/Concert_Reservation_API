package org.example.ticketing.infrastructure.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ticketing.domain.user.repository.RefreshTokenRepository;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public CustomLogoutFilter(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {

        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
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

        if (!refreshTokenRepository.existsById(refreshToken)) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        refreshTokenRepository.deleteById(refreshToken);

        Cookie deleteCookie = new Cookie("refresh", null);
        deleteCookie.setMaxAge(0);
        deleteCookie.setPath("/");
        httpResponse.addCookie(deleteCookie);

        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }
}