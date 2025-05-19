package intern.server.core.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String userName;

    private String email;

    private String password;

    private String idRole;

}
