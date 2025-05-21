package intern.server.core.manage.intern.service.impl;

import intern.server.core.common.base.PageableObject;
import intern.server.core.common.base.ResponseObject;
import intern.server.core.manage.intern.dto.request.MACreateUpdateInternRequest;
import intern.server.core.manage.intern.dto.request.MAInternRequest;
import intern.server.core.manage.intern.repository.MARoleRepository;
import intern.server.core.manage.intern.repository.MAUserRepository;
import intern.server.core.manage.intern.repository.MAUserRoleRepository;
import intern.server.core.manage.intern.service.MAInternService;
import intern.server.entity.Role;
import intern.server.entity.User;
import intern.server.entity.UserRole;
import intern.server.infrastructure.cloudinary.CloudinaryUploadImages;
import intern.server.infrastructure.constant.EntityStatus;
import intern.server.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Repository
@Validated
@RequiredArgsConstructor
public class MAInternServiceImpl implements MAInternService {

    private final MAUserRepository maUserRepository;

    private final MAUserRoleRepository maUserRoleRepository;

    private final MARoleRepository maRoleRepository;

    private final CloudinaryUploadImages cloudinaryUploadImages;

    @Override
    public ResponseObject<?> getAllIntern(MAInternRequest request) {
        Pageable pageable = Helper.createPageable(request, "createdDate");
        return new ResponseObject<>(
                PageableObject.of(maUserRepository.getAllUsers( pageable,request)),
                HttpStatus.OK,
                "Get all category successfully"
        );
    }

    @Override
    public ResponseObject<?> createIntern(MACreateUpdateInternRequest request) {
        List<User> userOptional = maUserRepository.findAllByEmail(request.getEmail().trim());
        if (!userOptional.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.BAD_REQUEST, "Email đã tồn tại");
        }

        User user = new User();
        String code = Helper.generateCodeFromName(request.getUserName());
        user.setCode(code);
        user.setUsername(request.getUserName());
        user.setStatus(EntityStatus.ACTIVE);
        user.setPicture(request.getPicture());
        user.setAddress(request.getAddress());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setMajor(request.getMajor());
        maUserRepository.save(user);
        Role role = maRoleRepository.findByCode(intern.server.infrastructure.constant.Role.THANH_VIEN.name());
        UserRole userRole = new UserRole(user, role);
        maUserRoleRepository.save(userRole);
        return new ResponseObject<>(null, HttpStatus.CREATED, "Thêm thể loại thành công");
    }

    @Override
    public ResponseObject<?> updateIntern(String categoryId, MACreateUpdateInternRequest request) {
        Optional<User> userOptional = maUserRepository.findById(categoryId);
        if (userOptional.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.BAD_REQUEST, "Email đã tồn tại");
        }
        User user = userOptional.get();
        String code = Helper.generateCodeFromName(user.getUsername());
        user.setCode(code);
        user.setUsername(request.getUserName());
        user.setPicture(request.getPicture());
        user.setAddress(request.getAddress());
        user.setMajor(request.getMajor());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        maUserRepository.save(user);
        return new ResponseObject<>(null, HttpStatus.CREATED, "Thêm thể loại thành công");
    }

    @Override
    public ResponseObject<?> changeInternStatus(String categoryId) {
        Optional<User> userOptional = maUserRepository.findById(categoryId);

        userOptional.map(user -> {
            user.setStatus(user.getStatus() != EntityStatus.ACTIVE ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);
            return maUserRepository.save(user);
        });

        return userOptional
                .map(subject -> new ResponseObject<>(null, HttpStatus.OK, "Đổi trạng thái thể loại thành công"))
                .orElseGet(() -> new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Không tìm thấy thể loại"));

    }

    @Override
    public ResponseObject<?> getInternById(String internId) {
        return maUserRepository.getAllUsersById(internId)
                .map(subject -> new ResponseObject<>(subject, HttpStatus.OK, "Get category successfully"))
                .orElseGet(() -> new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Category not found"));

    }

    @Override
    public ResponseObject<?> uploadFile(MultipartFile file) {
        try {
            return new ResponseObject<>(cloudinaryUploadImages.uploadImage(file),
                    HttpStatus.OK,
                    "Update file ảnh thành công");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseObject<>(null,HttpStatus.INTERNAL_SERVER_ERROR,"Tải file that bai");
        }
    }

}
