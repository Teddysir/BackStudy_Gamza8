package gamza.project.backstudy.dto;

import gamza.project.backstudy.entity.Enum.PostStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostOneResponseDto {
    private String title;
    private String content;
    private String username;
    private PostStatus postStatus;
}
