package gamza.project.backstudy.service.jwt;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gamza.project.backstudy.Error.ErrorCode;
import gamza.project.backstudy.Error.requestError.ExpiredRefreshTokenException;
import gamza.project.backstudy.entity.Enum.UserRole;
import gamza.project.backstudy.entity.UserEntity;
import gamza.project.backstudy.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessExpiration}")
    private long accessTokenValidTime;

    @Value("${jwt.refreshExpiration}")
    private long refreshTokenValidTime;

    @Value("${jwt.aesKey}")
    private String aesKey;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Long id) {
        try {
            return this.createToken(id, accessTokenValidTime, "access");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String createRefreshToken(Long id) {
        try {
            return this.createToken(id, refreshTokenValidTime, "refresh");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String createToken(Long id, long tokenValid, String tokenType) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("tokenType", tokenType);

        Claims claims = Jwts.claims().subject(encrypt(jsonObject.toString())).build();
        Date date = new Date();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(date)
                .expiration(new Date(date.getTime() + tokenValid))
                .signWith(getSigningKey())
                .compact();
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization",  accessToken);
    }

    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("RefreshToken",  refreshToken);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.extractUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "" , userDetails.getAuthorities());
    }

    public Long extractId(String token) { // -> id로 조회!
        JsonElement id = extractValue(token).get("id");
        return id.getAsLong();
    }

    public String extractUserEmail(String token) {
        Long id = extractId(token);
        UserEntity userId = userRepository.findById(id).orElseThrow();
        return userId.getEmail();
    }

    public String extractTokenType(String token){
        JsonElement tokenType = extractValue(token).get("tokenType");
        return tokenType.getAsString(); // 0  1  2
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7).trim();
            if (extractTokenType(token).equals("access")) {
                return token;
            }
        }
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("RefreshToken");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7).trim();
            if (extractTokenType(token).equals("refresh")) {
                return token;
            }
        }
        return null;
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Claims claims = extractAllClaims(refreshToken);
            return !claims.getExpiration().before(new Date());
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new ExpiredRefreshTokenException("5002", ErrorCode.EXPIRED_REFRESH_TOKEN);
//            throw new ExpiredJwtException(null,null,"ExpiredJwtToken");
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT claims string is empty");
        }
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Claims claims = extractAllClaims(accessToken);

            return !claims.getExpiration().before(new Date());
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
//            throw new ExpiredJwtException(null,null,"ExpiredJwtToken");
            throw new ExpiredRefreshTokenException("5001", ErrorCode.EXPIRED_ACCESS_TOKEN);
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT claims string is empty");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//
//    public String reissueAT(String refreshToken, HttpServletResponse response) {
//        try {
//            this.validateRefreshToken(refreshToken);
//            Long id = extractId(refreshToken);
//            Optional<UserEntity> user = userRepository.findById(id);
//            return createAccessToken(id, user.get().getUserRole());
//        } catch (ExpiredJwtException e) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return ErrorCode.EXPIRED_REFRESH_TOKEN.getMessage();
//        }
//    }

//    public String reissueRT(String refreshToken, HttpServletResponse response) {
//        try {
//            this.validateRefreshToken(refreshToken);
//            Long id = extractId(refreshToken);
//            Optional<UserEntity> user = userRepository.findById(id);
//            return createRefreshToken(id, user.get().getUserRole());
//        } catch (ExpiredJwtException e) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return ErrorCode.EXPIRED_REFRESH_TOKEN.getMessage();
//        }
//    }

    @SneakyThrows
    private String encrypt(String plainToken) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec IV = new IvParameterSpec(aesKey.substring(0, 16).getBytes());

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, secretKeySpec, IV);

        byte[] encryptionByte = c.doFinal(plainToken.getBytes(StandardCharsets.UTF_8));

        return Hex.encodeHexString(encryptionByte);
    }

    @SneakyThrows
    private String decrypt(String encodeText) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec IV = new IvParameterSpec(aesKey.substring(0, 16).getBytes());

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, secretKeySpec, IV);

        byte[] decodeByte = Hex.decodeHex(encodeText);

        return new String(c.doFinal(decodeByte), StandardCharsets.UTF_8);

    }

    private Claims extractAllClaims(String token) {
        return getParser()
                .parseSignedClaims(token)
                .getPayload();
    }

    private JwtParser getParser() {
        return Jwts.parser()
                .verifyWith(this.getSigningKey())
                .build();
    }

    private JsonObject extractValue(String token)  {
        String subject = extractAllClaims(token).getSubject();
        String decrypted = decrypt(subject);
        return new Gson().fromJson(decrypted, JsonObject.class);
    }



}
