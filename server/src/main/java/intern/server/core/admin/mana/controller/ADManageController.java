package intern.server.core.admin.account_manage.controller;

import intern.server.core.admin.account_manage.service.ADManageService;
import intern.server.core.manage.intern.dto.request.MAInternRequest;
import intern.server.infrastructure.constant.MappingConstants;
import intern.server.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MappingConstants.API_ADMIN_ACCOUNT_MANAGE)
public class ADManageController {

    private final ADManageService adManageService;

    @GetMapping()
    public ResponseEntity<?> getAccountManage(MAInternRequest request) {
        return Helper.createResponseEntity(adManageService.getAllManage(request));
    }

    @PutMapping("/{internId}/change-status")
    public ResponseEntity<?> changeStatusAccountManage(@PathVariable String internId) {
        return Helper.createResponseEntity(adManageService.changeAccountManageStatus(internId));
    }

}
