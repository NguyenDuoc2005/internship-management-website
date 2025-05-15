package intern.server.core.manage.meetings.dto.request;

import intern.server.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MAMeetingsRequest extends PageableRequest {

    private Integer status;

}
