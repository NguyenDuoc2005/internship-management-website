package intern.server.core.manage.intern.dto.response;

import java.time.LocalDateTime;

public interface MAInternResponse {
    String getId();
    String getCode();
    String getUserName();
    String getEmail();
    String getPassWord();
    String getPicture();
    String getPhoneNumber();
    String getAddress();
    String getMajor();
    Long getCreatedDate();
    Integer getStatus();
    Boolean getIsEvaluated();
    String getPosition();
}

