package gamza.project.backstudy.service.impl;


import gamza.project.backstudy.dto.PostListResponseDto;
import gamza.project.backstudy.dto.PostRequestDto;
import gamza.project.backstudy.dto.PostResponseDto;
import gamza.project.backstudy.entity.Enum.PostStatus;
import gamza.project.backstudy.entity.PostEntity;
import gamza.project.backstudy.repository.PostRepository;
import gamza.project.backstudy.service.inter.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public PostListResponseDto allPost() {
        List<PostEntity> allPost = postRepository.findAll();

        if (allPost.isEmpty()) {
            return PostListResponseDto.builder()
                    .size(0)
                    .posts(Collections.emptyList())
                    .build();
        } else {
            // PostEntity 리스트를 PostResponseDto 리스트로 변환
            List<PostResponseDto> postResponseDtos = allPost.stream()
                    .map(post -> PostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .username(post.getUserName())
                            .postStatus(post.getStatus())
                            .build())
                    .toList();

            // PostListResponseDto 생성 후 반환
            return PostListResponseDto.builder()
                    .size(postResponseDtos.size())
                    .posts(postResponseDtos)
                    .build();
        }
    }
}
