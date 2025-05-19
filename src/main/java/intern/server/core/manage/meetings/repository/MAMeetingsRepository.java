package intern.server.core.manage.meetings.repository;

import intern.server.core.manage.intern.dto.response.MAInternResponse;
import intern.server.core.manage.meetings.dto.request.MAEvaluationRequest;
import intern.server.core.manage.meetings.dto.request.MAMeetingsRequest;
import intern.server.core.manage.meetings.dto.response.MAMeetingResponse;
import intern.server.entity.Meeting;
import intern.server.repository.MeetingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MAMeetingsRepository extends MeetingRepository {
    @Query(
            value = """
        SELECT m.id AS id,
               m.code AS code,
               m.title AS title,
               m.content AS content,
               m.location AS location,
               m.startTime AS startTime,
               m.endTime AS endTime,
               m.status AS status,
               m.link AS link
        FROM Meeting m
        WHERE
            (:#{#request.q} IS NULL OR m.title LIKE CONCAT('%', :#{#request.q}, '%'))
            AND
            (:#{#request.status} IS NULL OR m.status = :#{#request.status})
        GROUP BY m.id
    """
    )
    Page<MAMeetingResponse> getAllMeetings(Pageable pageable, MAMeetingsRequest request);

    Meeting findMeetingById(String id);

    @Query(
            value = """
        SELECT m.id AS id,
               m.code AS code,
               m.title AS title,
               m.content AS content,
               m.location AS location,
               m.startTime AS startTime,
               m.endTime AS endTime,
               m.status AS status,
               m.link AS link
        FROM Meeting m
        WHERE
            m.id = :id
    """
    )
    Optional<MAMeetingResponse> getMeetingById(@Param("id") String id);
}
