package gamza.project.backstudy.service;

import gamza.project.backstudy.dto.PostRequestDto;
import gamza.project.backstudy.entity.Enum.PostStatus;
import gamza.project.backstudy.entity.PostEntity;
import gamza.project.backstudy.repository.PostRepository;
import gamza.project.backstudy.service.impl.PostServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.mockito.Mockito.*; // Mockito 정적 메서드 갖고옴
import static org.junit.jupiter.api.Assertions.*; // Assertions 정적 메서드 갖고옴

@SpringBootTest
@Transactional
public class PostServiceTest {

    private PostRequestDto postRequestDto;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postServiceImpl;


    @Test
    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);

        // 테스트에 사용할 DTO 객체 생성
        postRequestDto = new PostRequestDto();
        postRequestDto.setTitle("테스트 제목");
        postRequestDto.setContent("테스트 내용");
        postRequestDto.setUsername("테스트 유저");
    }

    @Test
    @DisplayName("게시물 생성 서비스 테스트 코드")
    void createPostTest() {

        // given
        PostEntity postEntity = PostEntity.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .userName(postRequestDto.getUsername())
                .status(PostStatus.REGISTERED) // 0 : REGISTERED, 1 : ABANDONED
                .build();

        when(postRepository.save(any(PostEntity.class))).thenReturn(postEntity); // -> 없어도 실행이 되네?

        // when
        postServiceImpl.createPost(postRequestDto);

        // then
        verify(postRepository, times(1)).save(any(PostEntity.class));
    }


    @Test
    @AfterEach
    public void after() {
        System.out.println("Test After");
    }


}
