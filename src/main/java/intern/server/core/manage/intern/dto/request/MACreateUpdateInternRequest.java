package intern.server.core.manage.intern.dto.request;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MACreateUpdateInternRequest {

    private String userName;
    private String email;
    private String passWord;
    private String picture;
    private String phoneNumber;
    private String address;
    private String major;
}
