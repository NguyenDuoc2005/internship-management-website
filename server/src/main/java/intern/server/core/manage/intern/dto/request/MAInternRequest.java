package intern.server.core.manage.intern.dto.request;

import intern.server.core.common.base.PageableRequest;
import intern.server.infrastructure.constant.EntityStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MAInternRequest extends PageableRequest {

    private String internName;

    private EntityStatus internStatus;

    private String idMeeting;

    private String idProducts;

}
