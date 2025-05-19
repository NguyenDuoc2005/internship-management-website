package intern.server.core.manage.meetings.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MAUpdateEvaluationRequest {

    private List<EvaluationUpdateItem> evaluations;

    @Getter
    @Setter
    public static class EvaluationUpdateItem {
        private String meetingId;
        private String userId;
        private Double score;
        private String comment;
    }
}
