package intern.server.core.manage.meetings.controller;

import intern.server.core.common.base.ResponseObject;
import intern.server.infrastructure.constant.MappingConstants;
import intern.server.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(MappingConstants.API_MANAGE_PREFIX)
public class MeetingsController {

    @GetMapping("/test")
    public ResponseEntity<?> get() {
        return Helper.createResponseEntity(new ResponseObject<>("ra", HttpStatus.OK,"thanh cong"));
    }

}
