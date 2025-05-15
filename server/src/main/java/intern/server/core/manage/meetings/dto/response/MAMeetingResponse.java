package intern.server.core.manage.meetings.dto.response;

public interface MAMeetingResponse {
    String getId();
    String getCode();
    String getTitle();
    String getContent();
    String getLocation();
    Long getStartTime();
    Long getEndTime();
    Long getMemberCount();
    Integer getStatus();
    String getLink();
}
