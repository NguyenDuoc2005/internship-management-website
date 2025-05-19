package intern.server.core.auth.service;

import intern.server.core.auth.dto.request.RegisterRequest;
import intern.server.core.common.base.ResponseObject;
import jakarta.validation.Valid;

public interface AuthService {

    ResponseObject<?> register(@Valid RegisterRequest request);

    ResponseObject<?> getAllRole();

}
