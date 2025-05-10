package intern.server.core.manage.intern.service;

import intern.server.core.common.base.ResponseObject;
import intern.server.core.manage.intern.dto.request.MACreateUpdateInternRequest;
import intern.server.core.manage.intern.dto.request.MAInternRequest;
import jakarta.validation.Valid;

public interface MAInternService {

    ResponseObject<?> getAllIntern(MAInternRequest request);

    ResponseObject<?> createIntern(@Valid MACreateUpdateInternRequest request);

    ResponseObject<?> updateIntern(String categoryId, @Valid MACreateUpdateInternRequest request);

    ResponseObject<?> changeInternStatus(String categoryId);

    ResponseObject<?> getInternById(String categoryId);

}
