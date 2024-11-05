package gamza.project.backstudy.dto.post;

import gamza.project.backstudy.entity.Enum.PostStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private PostStatus postStatus;
}
