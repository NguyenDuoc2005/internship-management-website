package intern.server.core.manage.meetings.dto.response;

public interface  MAEvaluationResponse {
    String getUserId();
    String getUserName();
    String getEmail();
    String getPicture();

    String getEvaluationId();
    Double getScore();
    String getComment();
    Boolean getIsEvaluated();
}
