package gamza.project.backstudy.dto;

import gamza.project.backstudy.entity.Enum.PostStatus;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private PostStatus postStatus;
}
