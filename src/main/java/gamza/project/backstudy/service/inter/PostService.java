package gamza.project.backstudy.service.inter;

import gamza.project.backstudy.dto.*;

public interface PostService {

    void createPost(PostRequestDto dto);

    PostListResponseDto allPost();

    PostOneResponseDto findOnePost(Long id);

    void updatePost(PostUpdateRequestDto dto, Long id);

    void deletePost(Long id);

}
