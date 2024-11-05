package gamza.project.backstudy.dto.user;


import gamza.project.backstudy.entity.Enum.UserRole;
import gamza.project.backstudy.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDto {
    private String email;
    private String password;


    @Builder
    public UserEntity toEntity() {
        return UserEntity.builder()
                .email(email)
                .password(password)
                .userRole(UserRole.USER)
                .build();
    }

}
