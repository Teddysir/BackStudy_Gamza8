package gamza.project.backstudy.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostListResponseDto {

    private int size;
    private List<PostResponseDto> posts;
}
