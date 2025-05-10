package intern.server.core.auth.controller;

import intern.server.core.auth.dto.request.RegisterRequest;
import intern.server.core.auth.service.AuthService;
import intern.server.infrastructure.constant.MappingConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import intern.server.core.common.base.ResponseObject;
import intern.server.core.auth.dto.request.LoginRequest;
import intern.server.infrastructure.security.service.CustomUserDetailsService;
import intern.server.infrastructure.security.service.TokenProvider;
import intern.server.infrastructure.security.user.UserPrincipal;
import intern.server.utils.Helper;

@RestController
@RequiredArgsConstructor
@RequestMapping(MappingConstants.API_AUTH_PREFIX)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String token = tokenProvider.createToken(authentication);

            return Helper.createResponseEntity(
                    new ResponseObject<>(token, HttpStatus.OK, "Lấy token thành công")
            );
        } catch (BadCredentialsException ex) {
            return Helper.createResponseEntity(
                    new ResponseObject<>(null, HttpStatus.UNAUTHORIZED, "Email hoặc mật khẩu không đúng")
            );
        } catch (Exception ex) {
            return Helper.createResponseEntity(
                    new ResponseObject<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi: " + ex.getMessage())
            );
        }
    }


    @PutMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return Helper.createResponseEntity(authService.register(request));
    }

    @GetMapping("/all-role")
    public ResponseEntity<?> getAllRole() {
        return Helper.createResponseEntity(authService.getAllRole());
    }

}
