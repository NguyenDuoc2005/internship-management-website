package intern.server.infrastructure.security.oauth2;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import intern.server.infrastructure.constant.OAuth2Constant;
import intern.server.utils.CookieUtils;

import java.io.IOException;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${frontend.url}")
    private String fontEndUrl;

    HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Autowired
    public void setHttpCookieOAuth2AuthorizationRequestRepository(HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        String screen = CookieUtils.getCookie(request, OAuth2Constant.SCREEN_FOR_ROLE_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(("/"));

        String targetUrl =  OAuth2Constant.REDIRECT_LOGIN_ADMIN;

        targetUrl = fontEndUrl + targetUrl;

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
