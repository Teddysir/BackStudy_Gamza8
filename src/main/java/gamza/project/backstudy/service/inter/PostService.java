package gamza.project.backstudy.service.inter;

import gamza.project.backstudy.dto.post.PostListResponseDto;
import gamza.project.backstudy.dto.post.PostOneResponseDto;
import gamza.project.backstudy.dto.post.PostRequestDto;
import gamza.project.backstudy.dto.post.PostUpdateRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

public interface PostService {

    void createPost(PostRequestDto dto, HttpServletRequest request);

    PostListResponseDto allPost();

    PostOneResponseDto findOnePost(Long id);

    void updatePost(PostUpdateRequestDto dto, Long id);

    void deletePost(Long id, HttpHeaders headers);

}
