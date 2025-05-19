package intern.server.core.manage.meetings.controller;

import intern.server.core.manage.meetings.dto.request.MAEvaluationRequest;
import intern.server.core.manage.meetings.dto.request.MAJoinOutMeetingsRequest;
import intern.server.core.manage.meetings.dto.request.MAUpdateEvaluationRequest;
import intern.server.core.manage.meetings.service.MAEvaluationService;
import intern.server.infrastructure.constant.MappingConstants;
import intern.server.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MappingConstants.API_MANAGE_EVALUATION)
public class MAEvaluationController {

    private final MAEvaluationService maEvaluationService;

    @GetMapping("/check-user")
    public ResponseEntity<?> getAllCheckUserEvaluation(MAEvaluationRequest request) {
        return Helper.createResponseEntity(maEvaluationService.getAllCheckUserEvaluation(request));
    }

    @PutMapping("/join-meetings")
    public ResponseEntity<?> joinMeetings(@RequestBody MAJoinOutMeetingsRequest request) {
        return Helper.createResponseEntity(maEvaluationService.joinMeetings(request));
    }

    @PutMapping("/out-meetings")
    public ResponseEntity<?> outMeetings(@RequestBody MAJoinOutMeetingsRequest request) {
        return Helper.createResponseEntity(maEvaluationService.outMeetings(request));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllEvaluation(MAEvaluationRequest request) {
        return Helper.createResponseEntity(maEvaluationService.getUserEvaluation(request));
    }

    @PutMapping("/update-evaluation")
    public ResponseEntity<?> updateEvaluation(@RequestBody MAUpdateEvaluationRequest request) {
        return Helper.createResponseEntity(maEvaluationService.updateEvaluation(request));
    }

}
