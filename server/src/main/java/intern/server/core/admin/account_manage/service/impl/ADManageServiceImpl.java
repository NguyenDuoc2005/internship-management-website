package intern.server.core.admin.account_manage.service.impl;

import intern.server.core.admin.account_manage.repository.ADUserRepository;
import intern.server.core.admin.account_manage.service.ADManageService;
import intern.server.core.common.base.PageableObject;
import intern.server.core.common.base.ResponseObject;
import intern.server.core.manage.intern.dto.request.MAInternRequest;
import intern.server.entity.User;
import intern.server.infrastructure.constant.EntityAccountStatus;
import intern.server.infrastructure.constant.EntityStatus;
import intern.server.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Repository
@Validated
@RequiredArgsConstructor
public class ADManageServiceImpl implements ADManageService {

    private final ADUserRepository adUserRepository;

    @Override
    public ResponseObject<?> getAllManage(MAInternRequest request) {
        Pageable pageable = Helper.createPageable(request, "createdDate");
        return new ResponseObject<>(
                PageableObject.of(adUserRepository.getAllAccount_Manage(pageable,request)),
                HttpStatus.OK,
                "Get all category successfully"
        );
    }

    @Override
    public ResponseObject<?> changeAccountManageStatus(String categoryId) {
        Optional<User> userOptional = adUserRepository.findById(categoryId);

        userOptional.map(user -> {
            user.setConfirmationStatus(user.getConfirmationStatus() != EntityAccountStatus.ACTIVE ? EntityAccountStatus.ACTIVE : EntityAccountStatus.INACTIVE);
            return adUserRepository.save(user);
        });

        return userOptional
                .map(subject -> new ResponseObject<>(null, HttpStatus.OK, "Đổi trạng thái tài khoản thành công"))
                .orElseGet(() -> new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Không tìm thấy thể loại"));

    }

}
