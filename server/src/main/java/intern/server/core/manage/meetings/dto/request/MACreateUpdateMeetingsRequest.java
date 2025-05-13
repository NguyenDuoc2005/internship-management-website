package intern.server.core.manage.meetings.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MACreateUpdateMeetingsRequest {

    private String code;

    private String title;

    private String content;

    private String location;

    private Long startTime;

    private Long endTime;

    private String link ;

}
