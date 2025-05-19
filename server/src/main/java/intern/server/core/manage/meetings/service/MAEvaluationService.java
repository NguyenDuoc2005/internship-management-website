package intern.server.core.manage.meetings.service;

import intern.server.core.common.base.ResponseObject;
import intern.server.core.manage.meetings.dto.request.MAEvaluationRequest;
import intern.server.core.manage.meetings.dto.request.MAJoinOutMeetingsRequest;
import intern.server.core.manage.meetings.dto.request.MAUpdateEvaluationRequest;

public interface MAEvaluationService {

    ResponseObject<?> getAllCheckUserEvaluation(MAEvaluationRequest request);

    ResponseObject<?> joinMeetings(MAJoinOutMeetingsRequest request);

    ResponseObject<?> outMeetings(MAJoinOutMeetingsRequest request);

    ResponseObject<?> getUserEvaluation(MAEvaluationRequest request);

    ResponseObject<?> updateEvaluation(MAUpdateEvaluationRequest request);

}
