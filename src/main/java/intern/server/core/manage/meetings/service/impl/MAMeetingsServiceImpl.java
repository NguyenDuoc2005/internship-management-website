package intern.server.core.manage.meetings.service.impl;

import intern.server.core.common.base.PageableObject;
import intern.server.core.common.base.ResponseObject;
import intern.server.core.manage.meetings.dto.request.MACreateUpdateMeetingsRequest;
import intern.server.core.manage.meetings.dto.request.MAMeetingsRequest;
import intern.server.core.manage.meetings.repository.MAMeetingsRepository;
import intern.server.core.manage.meetings.service.MAMeetingsService;
import intern.server.entity.Meeting;
import intern.server.entity.User;
import intern.server.infrastructure.constant.EntityStatus;
import intern.server.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Repository
@Validated
@RequiredArgsConstructor
public class MAMeetingsServiceImpl implements MAMeetingsService {

    private final MAMeetingsRepository maMeetingsRepository;

    @Override
    public ResponseObject<?> getAllMeetings(MAMeetingsRequest request) {
        Pageable pageable = Helper.createPageable(request, "createdDate");
        return new ResponseObject<>(
                PageableObject.of(maMeetingsRepository.getAllMeetings( pageable,request)),
                HttpStatus.OK,
                "Get all category successfully"
        );
    }

    @Override
    public ResponseObject<?> createMeetings(MACreateUpdateMeetingsRequest request) {
        Meeting meeting = new Meeting();
        String code = Helper.generateCodeFromName(request.getTitle().trim());
        meeting.setTitle(request.getTitle().trim());
        meeting.setCode(code);
        meeting.setContent(request.getContent().trim());
        meeting.setStatus(EntityStatus.ACTIVE);
        meeting.setLink(request.getLink().trim());
        meeting.setEndTime(request.getEndTime());
        meeting.setStartTime(request.getStartTime());
        meeting.setLocation(request.getLocation().trim());
        maMeetingsRepository.save(meeting);
        return new ResponseObject<>(null, HttpStatus.CREATED, "Thêm cuộc họp thành công");
    }

    @Override
    public ResponseObject<?> updateMeetings(String categoryId, MACreateUpdateMeetingsRequest request) {
        Meeting meeting = maMeetingsRepository.findMeetingById(categoryId);
        meeting.setContent(request.getContent().trim());
        String code = Helper.generateCodeFromName(request.getTitle().trim());
        meeting.setTitle(request.getTitle().trim());
        meeting.setCode(code);
        meeting.setStatus(EntityStatus.ACTIVE);
        meeting.setLink(request.getLink().trim());
        meeting.setEndTime(request.getEndTime());
        meeting.setStartTime(request.getStartTime());
        meeting.setLocation(request.getLocation().trim());
        maMeetingsRepository.save(meeting);
        return new ResponseObject<>(null, HttpStatus.CREATED, "Sửa cuộc họp thành công");
    }

    @Override
    public ResponseObject<?> changeMeetingsStatus(String categoryId) {
        Optional<Meeting> userOptional = maMeetingsRepository.findById(categoryId);

        userOptional.map(user -> {
            user.setStatus(user.getStatus() != EntityStatus.ACTIVE ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);
            return maMeetingsRepository.save(user);
        });

        return userOptional
                .map(subject -> new ResponseObject<>(null, HttpStatus.OK, "Đổi trạng thái thể loại thành công"))
                .orElseGet(() -> new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Không tìm thấy thể loại"));

    }

    @Override
    public ResponseObject<?> getMeetingsById(String categoryId) {
        return maMeetingsRepository.getMeetingById(categoryId)
                .map(subject -> new ResponseObject<>(subject, HttpStatus.OK, "Get category successfully"))
                .orElseGet(() -> new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Category not found"));
    }
}
