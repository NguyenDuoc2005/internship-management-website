package intern.server.core.auth.service.impl;

import intern.server.core.auth.dto.request.RegisterRequest;
import intern.server.core.auth.repository.AuthRoleRepository;
import intern.server.core.auth.repository.AuthUserRepository;
import intern.server.core.auth.repository.AuthUserRoleRepository;
import intern.server.core.auth.service.AuthService;
import intern.server.core.common.base.ResponseObject;
import intern.server.entity.Role;
import intern.server.entity.User;
import intern.server.entity.UserRole;
import intern.server.infrastructure.constant.EntityStatus;
import intern.server.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Repository
@Validated
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;

    private final AuthUserRoleRepository authUserRoleRepository;

    private final AuthRoleRepository authRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;

    @Override
    public ResponseObject<?> register(RegisterRequest request) {

        Optional<User> existingUser = authUserRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND,"Email đã tồn tại");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Optional<Role> role = authRoleRepository.findById(request.getIdRole());
        if (role.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND,"Vai trò không hợp lệ");
        }

        User newUser = new User();
        newUser.setUsername(request.getUserName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setStatus(EntityStatus.ACTIVE);
        authUserRepository.save(newUser);
        UserRole userRole = new UserRole();
        userRole.setRole(role.get());
        userRole.setUser(newUser);
        authUserRoleRepository.save(userRole);

        return new ResponseObject<>(null, HttpStatus.OK,"Đăng ký thành công");
    }

    @Override
    public ResponseObject<?> getAllRole() {
        return new ResponseObject<>(authRoleRepository.getRoles(),HttpStatus.OK,"lấy thành công");
    }

}
