package gamza.project.backstudy.service.impl;


import gamza.project.backstudy.dto.PostRequestDto;
import gamza.project.backstudy.entity.Enum.PostStatus;
import gamza.project.backstudy.entity.PostEntity;
import gamza.project.backstudy.repository.PostRepository;
import gamza.project.backstudy.service.inter.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public void createPost(PostRequestDto dto) {

        PostEntity post = PostEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .userName(dto.getUsername())
                .status(PostStatus.REGISTERED) // 0 : REGISTERED, 1 : ABANDONED
                .build();

        postRepository.save(post);
    }
}
