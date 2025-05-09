package intern.server.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import intern.server.entity.User;
import intern.server.infrastructure.constant.EntityStatus;
import intern.server.infrastructure.security.repository.RoleAuthRepository;
import intern.server.infrastructure.security.repository.UserAuthRepository;
import intern.server.infrastructure.security.user.UserPrincipal;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;
    private final RoleAuthRepository roleAuthRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        System.out.println("EMAIL: " + email);
        Optional<User> existingUser = userAuthRepository.findByEmailAndStatus(email, EntityStatus.ACTIVE);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            List<String> roles = roleAuthRepository.findRoleByUserId(user.getId());
            return userAuthRepository.findById(user.getId())
                    .map(u -> UserPrincipal.create(u, roles))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + user.getId()));
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
