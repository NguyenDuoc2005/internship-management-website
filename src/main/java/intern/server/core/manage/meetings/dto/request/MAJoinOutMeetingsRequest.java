package intern.server.core.manage.meetings.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MAJoinOutMeetingsRequest {

    private String meetingId;

    private String idUser;

}
