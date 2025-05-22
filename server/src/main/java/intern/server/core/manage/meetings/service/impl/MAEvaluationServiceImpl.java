package intern.server.core.manage.meetings.service.impl;

import intern.server.core.common.base.PageableObject;
import intern.server.core.common.base.ResponseObject;
import intern.server.core.admin.account_manage.repository.ADUserRepository;
import intern.server.core.manage.intern.repository.MAUserRepository;
import intern.server.core.manage.meetings.dto.request.MAEvaluationRequest;
import intern.server.core.manage.meetings.dto.request.MAJoinOutMeetingsRequest;
import intern.server.core.manage.meetings.dto.request.MAUpdateEvaluationRequest;
import intern.server.core.manage.meetings.repository.MAEvaluationRepository;
import intern.server.core.manage.meetings.repository.MAMeetingsRepository;
import intern.server.core.manage.meetings.service.MAEvaluationService;
import intern.server.entity.Evaluation;
import intern.server.entity.Meeting;
import intern.server.entity.User;
import intern.server.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Repository
@Validated
@RequiredArgsConstructor
public class MAEvaluationServiceImpl implements MAEvaluationService {

    private final MAEvaluationRepository maEvaluationRepository;

    private final MAMeetingsRepository maMeetingsRepository;

    private final MAUserRepository maUserRepository;

    @Override
    public ResponseObject<?> getAllCheckUserEvaluation(MAEvaluationRequest request) {

        Pageable pageable = Helper.createPageable(request, "createdDate");
        return new ResponseObject<>(
                PageableObject.of(maEvaluationRepository.getAllMeetingEvaluations(
                        request.getMeetingId()
                        ,request,pageable)),
                HttpStatus.OK,
                "Get all category successfully"
        );
    }

    @Override
    public ResponseObject<?> joinMeetings(MAJoinOutMeetingsRequest request) {
        System.out.println("joinMeetings"+request.getMeetingId());
        System.out.println("joinMeetin1"+request.getIdUser());
        User user = maUserRepository.findUserById(request.getIdUser());
        Meeting meeting = maMeetingsRepository.findMeetingById(request.getMeetingId());
//        Evaluation evaluation = maEvaluationRepository.findEvaluationByUserAndMeeting(user, meeting);
//        if (evaluation != null) {
//            return new ResponseObject<>(null,HttpStatus.NOT_FOUND,"Không tìm thấy ");
//        }
        Evaluation evaluationJoin = new Evaluation();

        evaluationJoin.setUser(user);
        evaluationJoin.setMeeting(meeting);
        maEvaluationRepository.save(evaluationJoin);
        return new ResponseObject<>(null,HttpStatus.OK,"join Thành công");

    }

    @Override
    public ResponseObject<?> outMeetings(MAJoinOutMeetingsRequest request) {
        User user = maUserRepository.findUserById(request.getIdUser());
        Meeting meeting = maMeetingsRepository.findMeetingById(request.getMeetingId());
        Optional<Evaluation> evaluationFind = maEvaluationRepository.findEvaluationByUserAndMeeting(user, meeting);
        if (evaluationFind.isEmpty() ) {
            return new ResponseObject<>(null,HttpStatus.NOT_FOUND,"Không tìm thấy ");
        }
        maEvaluationRepository.delete(evaluationFind.get());
        return new ResponseObject<>(null,HttpStatus.OK,"out Thành công");
    }

    @Override
    public ResponseObject<?> getUserEvaluation(MAEvaluationRequest request) {
        Pageable pageable = Helper.createPageable(request, "createdDate");
        return new ResponseObject<>(
                PageableObject.of(maEvaluationRepository.getUserEvaluation(
                        request.getMeetingId()
                        ,request,pageable)),
                HttpStatus.OK,
                "Get all category successfully"
        );
    }

    @Override
    public ResponseObject<?> updateEvaluation(MAUpdateEvaluationRequest request) {
        List<MAUpdateEvaluationRequest.EvaluationUpdateItem> evaluations = request.getEvaluations();

        for (MAUpdateEvaluationRequest.EvaluationUpdateItem item : evaluations) {
            User user = maUserRepository.findUserById(item.getUserId());
            Meeting meeting = maMeetingsRepository.findMeetingById(item.getMeetingId());
            Optional<Evaluation> evaluationFind = maEvaluationRepository.findEvaluationByUserAndMeeting(user, meeting);

            if (evaluationFind.isPresent()) {
                Evaluation evaluation = evaluationFind.get();
                evaluation.setScore(item.getScore());
                evaluation.setComment(item.getComment());
                maEvaluationRepository.save(evaluation);
            } else {
            }

        }

        return new ResponseObject<>(null,HttpStatus.OK,"cập nhật điểm thành công");

    }

}
