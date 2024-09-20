package gamza.project.backstudy.service.inter;

import gamza.project.backstudy.dto.PostRequestDto;
import gamza.project.backstudy.dto.PostResponseDto;

public interface PostService {

    void createPost(PostRequestDto dto);

    PostResponseDto allPost();
}
