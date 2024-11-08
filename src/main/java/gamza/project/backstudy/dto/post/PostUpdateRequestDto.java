package gamza.project.backstudy.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostUpdateRequestDto {

    private String title;
    private String content;
    private String username;
}
