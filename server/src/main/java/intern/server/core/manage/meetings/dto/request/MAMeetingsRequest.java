package intern.server.core.manage.meetings.dto.request;

import intern.server.core.common.base.PageableRequest;
import intern.server.infrastructure.constant.EntityStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MAMeetingsRequest extends PageableRequest {

    private EntityStatus status;

}
