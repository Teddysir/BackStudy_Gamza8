package gamza.project.backstudy.service.impl;


import gamza.project.backstudy.dto.*;
import gamza.project.backstudy.entity.Enum.PostStatus;
import gamza.project.backstudy.entity.PostEntity;
import gamza.project.backstudy.repository.PostRepository;
import gamza.project.backstudy.service.inter.PostService;
import gamza.project.backstudy.validation.PostValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostValidation postValidation;

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

    @Override
    public PostOneResponseDto findOnePost(Long id) {

        PostEntity post = postValidation.isPresentPost(id);

        return PostOneResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .postStatus(post.getStatus())
                .username(post.getUserName())
                .build();
    }

    @Override
    public void updatePost(PostUpdateRequestDto dto, Long id) {
        PostEntity post = postValidation.isValidateUserName(id, dto.getUsername());

        post.updatePost(dto.getTitle(), dto.getContent());

        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        PostEntity post = postValidation.isPresentPost(id);
        postRepository.delete(post);
    }
}
