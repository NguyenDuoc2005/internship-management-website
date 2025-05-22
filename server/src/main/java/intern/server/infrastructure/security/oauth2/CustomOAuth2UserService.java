package intern.server.infrastructure.security.oauth2;

import intern.server.entity.Role;
import intern.server.entity.UserRole;
import intern.server.infrastructure.constant.EntityAccountStatus;
import intern.server.infrastructure.security.repository.UserRoleAuthRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import intern.server.entity.User;
import intern.server.infrastructure.constant.CookieConstant;
import intern.server.infrastructure.constant.EntityStatus;
import intern.server.infrastructure.constant.OAuth2Constant;
import intern.server.infrastructure.exception.OAuth2AuthenticationProcessingException;
import intern.server.infrastructure.security.repository.RoleAuthRepository;
import intern.server.infrastructure.security.repository.UserAuthRepository;
import intern.server.infrastructure.security.oauth2.user.OAuth2UserInfo;
import intern.server.infrastructure.security.oauth2.user.OAuth2UserInfoFactory;
import intern.server.infrastructure.security.user.UserPrincipal;
import intern.server.utils.CookieUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final HttpServletRequest httpServletRequest;

    private final HttpServletResponse httpServletResponse;

    private final UserAuthRepository userAuthRepository;

    private final UserRoleAuthRepository userRoleAuthRepository;

    private final RoleAuthRepository roleAuthRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
                .getOAuth2UserInfo(
                        oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                        oAuth2User.getAttributes()
                );

        if (oAuth2UserInfo.getEmail() == null || oAuth2UserInfo.getEmail().isBlank()) {
            CookieUtils.addCookie(httpServletResponse, CookieConstant.ACCOUNT_NOT_EXIST, CookieConstant.ACCOUNT_NOT_EXIST);
            throw new OAuth2AuthenticationProcessingException(CookieConstant.ACCOUNT_NOT_EXIST);
        }

        Optional<Cookie> cookieOpRole = CookieUtils.getCookie(httpServletRequest, OAuth2Constant.SCREEN_FOR_ROLE_COOKIE_NAME);
        Optional<Cookie> cookieRegister = CookieUtils.getCookie(httpServletRequest, OAuth2Constant.REGISTER_PARAM_COOKIE_NAME);

        log.info("Received role: " + cookieOpRole.get().getValue());

        if (cookieOpRole.isPresent()) {
            String registerValue = cookieRegister.map(Cookie::getValue).orElse("false");
            boolean isRegister = "true".equalsIgnoreCase(registerValue);
            Cookie cookie = cookieOpRole.get();
            if(cookie.getValue().equals(OAuth2Constant.ROLE_ADMIN)){
                return this.processAdmin(oAuth2UserInfo,OAuth2Constant.ROLE_ADMIN,isRegister);
            }
            if(cookie.getValue().equals(OAuth2Constant.ROLE_MANAGE)){

                return this.processManage(oAuth2UserInfo,OAuth2Constant.ROLE_MANAGE,isRegister);
            }
            if(cookie.getValue().equals(OAuth2Constant.ROLE_MEMBER)){
                return this.processMember(oAuth2UserInfo,OAuth2Constant.ROLE_MEMBER,isRegister);
            }
        }
        CookieUtils.addCookie(httpServletResponse, CookieConstant.ACCOUNT_NOT_EXIST, CookieConstant.ACCOUNT_NOT_EXIST);
        throw new OAuth2AuthenticationProcessingException(CookieConstant.ACCOUNT_NOT_EXIST);
    }

    private OAuth2User processAdmin(OAuth2UserInfo oAuth2UserInfo, String role,Boolean register) {

        Optional<User> userOptional = userAuthRepository.findByEmailAndStatus(oAuth2UserInfo.getEmail(), EntityStatus.ACTIVE);

        if (
                userOptional.isPresent()
        ) {
            List<String> roleUser = roleAuthRepository.findRoleByUserId(userOptional.get().getId());
            if(  roleUser.contains(role)){
                String email = oAuth2UserInfo.getEmail();
                User staffExist = userOptional.get();
                staffExist.setCode(email.substring(0, email.indexOf("@")));
                staffExist.setPicture(oAuth2UserInfo.getImageUrl());

                userAuthRepository.save(staffExist);
                return UserPrincipal.create(userOptional.get(), oAuth2UserInfo.getAttributes(),roleUser);
            }else {
                CookieUtils.addCookie(httpServletResponse, CookieConstant.ACCOUNT_NOT_EXIST, CookieConstant.ACCOUNT_NOT_EXIST);
                throw new OAuth2AuthenticationProcessingException(CookieConstant.ACCOUNT_NOT_EXIST);
            }

        } else {
            CookieUtils.addCookie(httpServletResponse, CookieConstant.ACCOUNT_NOT_EXIST, CookieConstant.ACCOUNT_NOT_EXIST);
            throw new OAuth2AuthenticationProcessingException(CookieConstant.ACCOUNT_NOT_EXIST);
        }
    }

    private OAuth2User processManage(OAuth2UserInfo oAuth2UserInfo, String role,Boolean register) {

        if(register){
            // dang ky
            Optional<User> userUNVERIFIED = userAuthRepository.
                    findByEmailAndStatusAndConfirmationStatus(
                            oAuth2UserInfo.getEmail(),EntityStatus.ACTIVE, EntityAccountStatus.INACTIVE);

            if(userUNVERIFIED.isPresent()){
                CookieUtils.addCookie(httpServletResponse, CookieConstant.Unverified_Account, CookieConstant.Unverified_Account);
                throw new OAuth2AuthenticationProcessingException(CookieConstant.Unverified_Account);
            }
            Optional<User> userFind = userAuthRepository.findByEmailAndStatus(oAuth2UserInfo.getEmail(),EntityStatus.ACTIVE);

            if(userFind.isPresent()){
                CookieUtils.addCookie(httpServletResponse, CookieConstant.ACCOUNT_EXIST, CookieConstant.ACCOUNT_EXIST);
                throw new OAuth2AuthenticationProcessingException(CookieConstant.ACCOUNT_EXIST);
            }

            User user= new User();
            String email = oAuth2UserInfo.getEmail();
            user.setEmail(email);
            user.setUsername(oAuth2UserInfo.getName());
            user.setPicture(oAuth2UserInfo.getImageUrl());
            user.setCode(email.substring(0, email.indexOf("@")));
            user.setConfirmationStatus(EntityAccountStatus.INACTIVE);
            userAuthRepository.save(user);
            Role roleFind = roleAuthRepository.findRoleByCode("QUAN_LY");
            UserRole userRole = new UserRole();
            userRole.setRole(roleFind);
            userRole.setUser(user);
            userRoleAuthRepository.save(userRole);

        }
        if(register){
            // dang ky
            Optional<User> userUNVERIFIED = userAuthRepository.
                    findByEmailAndStatusAndConfirmationStatus(
                            oAuth2UserInfo.getEmail(),EntityStatus.ACTIVE, EntityAccountStatus.INACTIVE);

            if(userUNVERIFIED.isPresent()){
                CookieUtils.addCookie(httpServletResponse, CookieConstant.Unverified_Account, CookieConstant.Unverified_Account);
                throw new OAuth2AuthenticationProcessingException(CookieConstant.Unverified_Account);
            }
        }
        // đã đăng ky cho xac nhan
        Optional<User> userUNVERIFIED = userAuthRepository.
                findByEmailAndStatusAndConfirmationStatus(
                        oAuth2UserInfo.getEmail(),EntityStatus.ACTIVE, EntityAccountStatus.INACTIVE);

        if(userUNVERIFIED.isPresent()){
            CookieUtils.addCookie(httpServletResponse, CookieConstant.Registered_Awaiting_Confirmation, CookieConstant.Registered_Awaiting_Confirmation);
            throw new OAuth2AuthenticationProcessingException(CookieConstant.Registered_Awaiting_Confirmation);
        }

        Optional<User> userOptional = userAuthRepository.findByEmailAndStatus(oAuth2UserInfo.getEmail(), EntityStatus.ACTIVE);

        if (
                userOptional.isPresent()
        ) {
            List<String> roleUser = roleAuthRepository.findRoleByUserId(userOptional.get().getId());
            if(  roleUser.contains(role)){
                String email = oAuth2UserInfo.getEmail();
                User staffExist = userOptional.get();
                staffExist.setCode(email.substring(0, email.indexOf("@")));
                staffExist.setPicture(oAuth2UserInfo.getImageUrl());
                userAuthRepository.save(staffExist);
                return UserPrincipal.create(userOptional.get(), oAuth2UserInfo.getAttributes(),roleUser);
            }else {
                CookieUtils.addCookie(httpServletResponse, CookieConstant.ACCOUNT_NOT_EXIST, CookieConstant.ACCOUNT_NOT_EXIST);
                throw new OAuth2AuthenticationProcessingException(CookieConstant.ACCOUNT_NOT_EXIST);
            }

        } else {
            CookieUtils.addCookie(httpServletResponse, CookieConstant.ACCOUNT_NOT_EXIST, CookieConstant.ACCOUNT_NOT_EXIST);
            throw new OAuth2AuthenticationProcessingException(CookieConstant.ACCOUNT_NOT_EXIST);
        }
    }

    private OAuth2User processMember(OAuth2UserInfo oAuth2UserInfo, String role,Boolean register) {
        if(register){
            Optional<User> userFind = userAuthRepository.findByEmailAndStatus(oAuth2UserInfo.getEmail(),EntityStatus.ACTIVE);
            if(userFind.isPresent()){
                CookieUtils.addCookie(httpServletResponse, CookieConstant.ACCOUNT_EXIST, CookieConstant.ACCOUNT_EXIST);
                throw new OAuth2AuthenticationProcessingException(CookieConstant.ACCOUNT_EXIST);
            }
            User user= new User();
            String email = oAuth2UserInfo.getEmail();
            user.setEmail(email);
            user.setUsername(oAuth2UserInfo.getName());
            user.setPicture(oAuth2UserInfo.getImageUrl());
            user.setCode(email.substring(0, email.indexOf("@")));
            userAuthRepository.save(user);
            Role roleFind = roleAuthRepository.findRoleByCode("THANH_VIEN");
            UserRole userRole = new UserRole();
            userRole.setRole(roleFind);
            userRole.setUser(user);
            userRoleAuthRepository.save(userRole);
        }
        Optional<User> userOptional = userAuthRepository.findByEmailAndStatus(oAuth2UserInfo.getEmail(), EntityStatus.ACTIVE);

        if (
                userOptional.isPresent()
        ) {
            List<String> roleUser = roleAuthRepository.findRoleByUserId(userOptional.get().getId());
            if(  roleUser.contains(role)){
                String email = oAuth2UserInfo.getEmail();
                User staffExist = userOptional.get();
                staffExist.setCode(email.substring(0, email.indexOf("@")));
                staffExist.setPicture(oAuth2UserInfo.getImageUrl());
                userAuthRepository.save(staffExist);
                return UserPrincipal.create(userOptional.get(), oAuth2UserInfo.getAttributes(),roleUser);
            }else {
                CookieUtils.addCookie(httpServletResponse, CookieConstant.ACCOUNT_NOT_EXIST, CookieConstant.ACCOUNT_NOT_EXIST);
                throw new OAuth2AuthenticationProcessingException(CookieConstant.ACCOUNT_NOT_EXIST);
            }

        } else {
            CookieUtils.addCookie(httpServletResponse, CookieConstant.ACCOUNT_NOT_EXIST, CookieConstant.ACCOUNT_NOT_EXIST);
            throw new OAuth2AuthenticationProcessingException(CookieConstant.ACCOUNT_NOT_EXIST);
        }
    }

}
