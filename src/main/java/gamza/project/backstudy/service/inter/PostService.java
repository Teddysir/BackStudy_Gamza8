package gamza.project.backstudy.service.inter;

import gamza.project.backstudy.dto.post.PostListResponseDto;
import gamza.project.backstudy.dto.post.PostOneResponseDto;
import gamza.project.backstudy.dto.post.PostRequestDto;
import gamza.project.backstudy.dto.post.PostUpdateRequestDto;

public interface PostService {

    void createPost(PostRequestDto dto);

    PostListResponseDto allPost();

    PostOneResponseDto findOnePost(Long id);

    void updatePost(PostUpdateRequestDto dto, Long id);

    void deletePost(Long id);

}
