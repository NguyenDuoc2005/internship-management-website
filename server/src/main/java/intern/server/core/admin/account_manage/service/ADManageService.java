package intern.server.core.admin.account_manage.service;

import intern.server.core.common.base.ResponseObject;
import intern.server.core.manage.intern.dto.request.MAInternRequest;

public interface ADManageService {

    ResponseObject<?> getAllManage(MAInternRequest request);

    ResponseObject<?> changeAccountManageStatus(String categoryId);

}
