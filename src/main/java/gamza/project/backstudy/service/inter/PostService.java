package gamza.project.backstudy.service.inter;

import gamza.project.backstudy.dto.PostListResponseDto;
import gamza.project.backstudy.dto.PostOneResponseDto;
import gamza.project.backstudy.dto.PostRequestDto;
import gamza.project.backstudy.dto.PostResponseDto;

public interface PostService {

    void createPost(PostRequestDto dto);

    PostListResponseDto allPost();

    PostOneResponseDto findOnePost(Long id);

    void deletePost(Long id);

}
