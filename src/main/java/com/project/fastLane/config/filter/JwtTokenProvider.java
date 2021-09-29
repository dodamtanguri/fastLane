package com.project.fastLane.config.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;
    @Value("${token.secret}")
    private String secretKey;

    @Value("${token.header}")
    private String HEADER;

    // 객체 초기화, Secret Key를 Base64 encoding
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * JWT 토큰 생성
     *
     * @param accountId 계정 ID
     * @return
     */
    public String generateToken(final String loginId, final Long accountId) {
        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("id", accountId);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMonths(1).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * JWT token 인증 정보 조회
     *
     * @param token
     * @return
     */
    public Authentication getAuthentication(final String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUser(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Get user info from JWT token
     *
     * @param token JWT token
     * @return
     */
    public String getUser(final String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Resolve token
     *
     * @param request
     * @return
     */
    public String resolveToken(final HttpServletRequest request) {
        return request.getHeader(HEADER);
    }

    /**
     * JWT token 만료일 확인
     *
     * @param token JWT token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            // 임시조치
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            // return !claims.getBody().getExpiration().before(new Date());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
