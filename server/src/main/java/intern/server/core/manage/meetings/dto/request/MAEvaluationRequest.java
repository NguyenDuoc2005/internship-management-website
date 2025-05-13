package intern.server.core.manage.meetings.dto.request;

import intern.server.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MAEvaluationRequest extends PageableRequest {

    private String internName;

    private Integer internStatus;

    private String meetingId;

    private Boolean evaluatedOnly;

}
