package intern.server.core.manage.phase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import intern.server.core.common.base.ResponseObject;
import intern.server.core.manage.phase.dto.request.LoginRequest;
import intern.server.infrastructure.security.service.CustomUserDetailsService;
import intern.server.infrastructure.security.service.TokenProvider;
import intern.server.infrastructure.security.user.UserPrincipal;
import intern.server.utils.Helper;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/api/v1/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = tokenProvider.createToken(authentication);
        return Helper.createResponseEntity(new ResponseObject<>(token, HttpStatus.OK,"Lấy token thành công"));
    }

}
