package intern.server.infrastructure.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import intern.server.core.common.base.ResponseObject;
import intern.server.entity.User;
import intern.server.infrastructure.security.repository.RoleAuthRepository;
import intern.server.infrastructure.security.repository.UserAuthRepository;
import intern.server.infrastructure.security.user.UserPrincipal;

import java.util.*;

@Service
@Slf4j
public class TokenProvider {

    @Value("${jwt.secret}")
    private String tokenSecret;

    private final long TOKEN_EXP = System.currentTimeMillis() + 2 * 60 * 60 * 100000;  // 2 giờ

    @Setter(onMethod_ = @Autowired)
    private UserAuthRepository userAuthRepository;

    @Setter(onMethod_ = @Autowired)
    private RoleAuthRepository roleAuthRepository;

    @Setter(onMethod_ = @Autowired)
    private HttpServletRequest httpServletRequest;

    public String createToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String email = userPrincipal.getEmail();

        Optional<User> user = userAuthRepository.findByEmail(email);
        if (user.isEmpty()) {
            new ResponseObject<>(null, HttpStatus.NOT_FOUND,"Tài khoản khong ton tai");
        }

        List<String> rolesCode = roleAuthRepository.findRoleByUserId(user.get().getId());

        List<String> roleName = roleAuthRepository.findRoleNameByUserId(user.get().getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("rolesCode", rolesCode);
        claims.put("rolesName", roleName);
        claims.put("pictureUrl", user.get().getPicture());
        claims.put("fullName", user.get().getUsername());
        claims.put("userId", user.get().getId());
        claims.put("roleScreen", rolesCode.get(0));

        String jwt =  Jwts.builder()
                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000))
                .setIssuer("ecoit")
                .signWith(Keys.hmacShaKeyFor(tokenSecret.getBytes()))
                .compact();
        System.out.println("jwt :  "+jwt);
        return jwt;

    }

    // Phương thức xác thực token
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(tokenSecret.getBytes()))
                    .build()
                    .parseClaimsJws(authToken);  // Phân tích và xác thực JWT
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
        }
        return false;
    }

    // Lấy thông tin claims từ token
    private Claims getClaimsToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(tokenSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String getUserIdFromToken(String token) {
        Claims claims = getClaimsToken(token);
        return claims.getSubject();
    }

    public String getEmailFromToken(String token) {
        Claims claims = getClaimsToken(token);
        return claims.get("email", String.class);
    }

//    public List<Role> getRolesCodesFromToken(String token) {
//        Claims claims = getClaimsToken(token);
//        List<String> roles = claims.get("rolesCodes", List.class);
//        System.out.println("ra role code : "+roles.get(0));
//
//        // Nếu bạn muốn sử dụng Role entity trong cơ sở dữ liệu, bạn cần tìm kiếm các Role theo roleCode
//        return roles.stream()
//                .map(roleCode -> {
//                    Role role = new Role();
//                    role.setCode(roleCode);  // Chỉ set code role nếu cần
//                    return role;
//                })
//                .collect(Collectors.toList());
//    }

}
