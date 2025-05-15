package intern.server.core.manage.meetings.service;

import intern.server.core.common.base.ResponseObject;
import intern.server.core.manage.meetings.dto.request.MACreateUpdateMeetingsRequest;
import intern.server.core.manage.meetings.dto.request.MAEvaluationRequest;
import intern.server.core.manage.meetings.dto.request.MAMeetingsRequest;
import jakarta.validation.Valid;

public interface MAMeetingsService {

    ResponseObject<?> getAllMeetings(MAMeetingsRequest request);

    ResponseObject<?> createMeetings(@Valid MACreateUpdateMeetingsRequest request);

    ResponseObject<?> updateMeetings(String categoryId, @Valid MACreateUpdateMeetingsRequest request);

    ResponseObject<?> changeMeetingsStatus(String categoryId);

    ResponseObject<?> getMeetingsById(String categoryId);

}
