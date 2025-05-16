package intern.server.core.manage.meetings.controller;

import intern.server.core.manage.intern.dto.request.MAInternRequest;
import intern.server.core.manage.meetings.dto.request.MACreateUpdateMeetingsRequest;
import intern.server.core.manage.meetings.dto.request.MAMeetingsRequest;
import intern.server.core.manage.meetings.service.MAMeetingsService;
import intern.server.infrastructure.constant.MappingConstants;
import intern.server.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MappingConstants.API_MANAGE_MEETINGS)
public class MAMeetingsController {

    private final MAMeetingsService maMeetingsService;

    @GetMapping()
    public ResponseEntity<?> getAllMeetings(MAMeetingsRequest request) {
        return Helper.createResponseEntity(maMeetingsService.getAllMeetings(request));
    }

    @PostMapping("/add")
    public ResponseEntity<?> createMeetings(@RequestBody MACreateUpdateMeetingsRequest request) {
        return Helper.createResponseEntity(maMeetingsService.createMeetings(request));
    }

    @PutMapping("/update/{internId}")
    public ResponseEntity<?> updateMeetings(@PathVariable String internId, @RequestBody MACreateUpdateMeetingsRequest request) {
        return Helper.createResponseEntity(maMeetingsService.updateMeetings(internId, request));
    }

    @PutMapping("/{internId}/change-status")
    public ResponseEntity<?> changeMeetingsStatus(@PathVariable String internId) {
        return Helper.createResponseEntity(maMeetingsService.changeMeetingsStatus(internId));
    }

    @GetMapping("/detail/{internId}")
    public ResponseEntity<?> getMeetingsById(@PathVariable String internId) {
        return Helper.createResponseEntity(maMeetingsService.getMeetingsById(internId));
    }



}
