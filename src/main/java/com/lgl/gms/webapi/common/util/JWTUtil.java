package com.lgl.gms.webapi.common.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgl.gms.config.PropConfig;
import com.lgl.gms.webapi.common.dto.TokenInfo;
import com.lgl.gms.webapi.sys.persistence.model.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 토큰사용 유틸
 */
@Slf4j
@Component
public class JWTUtil {

    // public final static long TOKEN_VALIDATION_SECOND = 1000L * 10; // 실제 사용은
    // application.yml 에 설정된 값
    // public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    @Autowired
    public PropConfig prop;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 토큰 정보 중 userId 정보만 취득
     */
    public String getUserId(String token) {
        return extractAllClaims(token).get("userId", String.class);
    }

    /**
     * 토큰 정보 취득(사용자 id, authCd, compId, boId)
     */
    public TokenInfo getTokenInfo(String token) {
        Claims claims = extractAllClaims(token);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUserId(claims.get("userId", String.class));
        tokenInfo.setCompId(claims.get("compId", Integer.class));
        tokenInfo.setBoId(claims.get("boId", Integer.class));
        tokenInfo.setAuthCd(claims.get("authCd", String.class));
        tokenInfo.setWorkIp(claims.get("workIp", String.class));
        tokenInfo.setToken(token);
        return tokenInfo;
    }

    public TokenInfo getRefreshTokenInfo(String token) {
        Claims claims = extractAllClaims(token);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUserId(claims.get("userId", String.class));
        tokenInfo.setCompId(claims.get("compId", Integer.class));
        return tokenInfo;
    }
    
    /**
     * claim 추출 시 에러가 발생하면 ExpiredJwtException 발생
     * @param token
     * @return
     * @throws ExpiredJwtException
     */
    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(prop.getJwtKey()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰 만료여부 확인
     */
    public boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    /**
     * 사용자 정보를 포함한 Access Token 토큰을 생성
     */
    public String generateToken(UserModel user) {
        Claims claims = Jwts.claims();
        // 사용자 정보를 토큰에 저장
        claims.put("userId", user.getUserId());
//      claims.put("userTyp", user.getUserTyp());
        claims.put("compId", user.getCompId());
        claims.put("boId", user.getBoId());
        claims.put("authCd", user.getAuthCd());
        claims.put("workIp", user.getWorkIp());
        
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // AT 만료기한을 10분으로 설정 ( 60초(60000) * yml파일에 설정된 값(분 단위))
                .setExpiration(new Date(System.currentTimeMillis() + (60000L * prop.getJwtAccessTime())))
                .signWith(getSigningKey(prop.getJwtKey()), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    /**
     * refreshToken 생성으로 현재는 userId 정보만 사용
     * (필요한 경우 다른 정보를 포함)
     * @param user
     * @return
     */
    public String generateRefreshToken(UserModel user) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getUserId());
        claims.put("compId", user.getCompId());
//        claims.put("authCd", user.getAuthCd());

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // AT 만료기한을 30분으로 설정 ( 60초(60000) * yml파일에 설정된 값(분단위))
                .setExpiration(new Date(System.currentTimeMillis() + (60000L * prop.getJwtRefreshTime())))
                .signWith(getSigningKey(prop.getJwtKey()), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    /**
     * 토큰 유효성 체크
     *
     * @param token
     * @return
     * @throws ExpiredJwtException
     */
    public boolean validateToken(String token) throws ExpiredJwtException {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey(prop.getJwtKey())).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

}
