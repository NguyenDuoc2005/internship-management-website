package intern.server.core.manage.intern.controller;

import intern.server.core.manage.intern.dto.request.MACreateUpdateInternRequest;
import intern.server.core.manage.intern.dto.request.MAInternRequest;
import intern.server.core.manage.intern.service.MAInternService;
import intern.server.infrastructure.constant.MappingConstants;
import intern.server.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(MappingConstants.API_MANAGE_INTERN)
public class MAInternController {

    private final MAInternService maInternService;

    @GetMapping()
    public ResponseEntity<?> get(MAInternRequest request) {
        return Helper.createResponseEntity(maInternService.getAllIntern(request));
    }

    @PostMapping("/add")
    public ResponseEntity<?> createIntern(@RequestBody MACreateUpdateInternRequest request) {
        return Helper.createResponseEntity(maInternService.createIntern(request));
    }

    @PutMapping("/update/{internId}")
    public ResponseEntity<?> updateIntern(@PathVariable String internId, @RequestBody MACreateUpdateInternRequest request) {
        return Helper.createResponseEntity(maInternService.updateIntern(internId, request));
    }

    @PutMapping("/{internId}/change-status")
    public ResponseEntity<?> changeStatusIntern(@PathVariable String internId) {
        return Helper.createResponseEntity(maInternService.changeInternStatus(internId));
    }

    @GetMapping("/detail/{internId}")
    public ResponseEntity<?> getByIdIntern(@PathVariable String internId) {
        return Helper.createResponseEntity(maInternService.getInternById(internId));
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file) {
        return Helper.createResponseEntity(maInternService.uploadFile(file));
    }

}
