package gamza.project.backstudy.dto;

import gamza.project.backstudy.entity.PostEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;
    private String username;

}
