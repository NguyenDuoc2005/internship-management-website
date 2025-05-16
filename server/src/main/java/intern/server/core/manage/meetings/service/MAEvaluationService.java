package intern.server.core.manage.meetings.service;

import intern.server.core.common.base.ResponseObject;
import intern.server.core.manage.meetings.dto.request.MAEvaluationRequest;
import intern.server.core.manage.meetings.dto.request.MAJoinOutMeetingsRequest;

public interface MAEvaluationService {

    ResponseObject<?> getAllCheckUserEvaluation(MAEvaluationRequest request);

    ResponseObject<?> joinMeetings(MAJoinOutMeetingsRequest request);

    ResponseObject<?> outMeetings(MAJoinOutMeetingsRequest request);


}
