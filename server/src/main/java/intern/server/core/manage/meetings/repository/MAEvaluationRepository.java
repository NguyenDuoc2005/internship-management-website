package intern.server.core.manage.meetings.repository;

import intern.server.core.manage.meetings.dto.request.MAEvaluationRequest;
import intern.server.core.manage.meetings.dto.request.MAMeetingsRequest;
import intern.server.core.manage.meetings.dto.response.MAEvaluationResponse;
import intern.server.core.manage.meetings.dto.response.MAMeetingResponse;
import intern.server.entity.Evaluation;
import intern.server.entity.Meeting;
import intern.server.entity.User;
import intern.server.repository.EvaluationRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MAEvaluationRepository extends EvaluationRepository {

    @Query("""
    SELECT 
        u.id AS userId,
        u.username AS userName,
        u.email AS email,
        u.picture AS picture,
        e.id AS evaluationId,
        e.score AS score,
        e.comment AS comment,
        CASE WHEN e.id IS NOT NULL THEN TRUE ELSE FALSE END AS isEvaluated
    FROM User u
    LEFT JOIN Evaluation e ON e.user = u AND e.meeting.id = :meetingId
    WHERE 
        (:#{#request.q} IS NULL OR u.email LIKE CONCAT('%', :#{#request.q}, '%'))
            
    """)
    Page<MAEvaluationResponse> getAllMeetingEvaluations(
            @Param("meetingId") String meetingId,
            MAEvaluationRequest request,
            Pageable pageable
    );

    User findByUserId(String idUser);

    Evaluation findByUser(@NotNull User user);

}
