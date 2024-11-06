package gamza.project.backstudy.service.impl;


import gamza.project.backstudy.Error.ErrorCode;
import gamza.project.backstudy.Error.requestError.BadRequestException;
import gamza.project.backstudy.dto.post.*;
import gamza.project.backstudy.entity.Enum.PostStatus;
import gamza.project.backstudy.entity.PostEntity;
import gamza.project.backstudy.entity.UserEntity;
import gamza.project.backstudy.repository.PostRepository;
import gamza.project.backstudy.repository.UserRepository;
import gamza.project.backstudy.service.inter.PostService;
import gamza.project.backstudy.service.jwt.JwtTokenProvider;
import gamza.project.backstudy.validation.PostValidation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostValidation postValidation;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void createPost(PostRequestDto dto, HttpServletRequest request) {

        String token = jwtTokenProvider.resolveAccessToken(request);
        jwtTokenProvider.validateAccessToken(token);
        Long userId = jwtTokenProvider.extractId(token);
        Optional<UserEntity> user = userRepository.findById(userId);

        PostEntity post = PostEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user.get())
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

            List<PostResponseDto> postResponseDtos = allPost.stream()
                    .map(post -> PostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .postStatus(post.getStatus())
                            .build())
                    .toList();

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
                .build();
    }

    @Override
    public void updatePost(PostUpdateRequestDto dto, Long id) {
        PostEntity post = postValidation.isValidateUserName(id, dto.getUsername());

        post.updatePost(dto.getTitle(), dto.getContent());

        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id, HttpHeaders headers) {

        String token = headers.get("Authorization").toString().substring(7).trim();
        System.out.println("---------------------------------");
        System.out.println(token);

        if(token.isEmpty()) {
            throw new BadRequestException("ErrorCode : 00x4", ErrorCode.NOT_ALLOW_ACCESS_EXCEPTION);
        }

        jwtTokenProvider.validateAccessToken(token);
        Long userId = jwtTokenProvider.extractId(token);

        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BadRequestException("ErrorCode : 00x5", ErrorCode.NOT_ALLOW_ACCESS_EXCEPTION);
        }

        PostEntity post = postValidation.isPresentPost(id);
        if (!post.getUser().getId().equals(userId)) {
            throw new BadRequestException("ErrorCode : 00x6", ErrorCode.NOT_ALLOW_ACCESS_EXCEPTION);
        }

        postRepository.delete(post);
    }
}
