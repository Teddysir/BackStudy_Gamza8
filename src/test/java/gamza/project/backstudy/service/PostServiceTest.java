package gamza.project.backstudy.service;

import gamza.project.backstudy.dto.post.PostListResponseDto;
import gamza.project.backstudy.dto.post.PostRequestDto;
import gamza.project.backstudy.entity.Enum.PostStatus;
import gamza.project.backstudy.entity.PostEntity;
import gamza.project.backstudy.repository.PostRepository;
import gamza.project.backstudy.service.impl.PostServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*; // Mockito 정적 메서드 갖고옴
import static org.junit.jupiter.api.Assertions.*; // Assertions 정적 메서드 갖고옴

@SpringBootTest
@Transactional
@Nested
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
//                .userName(postRequestDto.getUsername())
                .status(PostStatus.REGISTERED) // 0 : REGISTERED, 1 : ABANDONED
                .build();

        when(postRepository.save(any(PostEntity.class))).thenReturn(postEntity); // -> 없어도 실행이 되네?

        // when
//        postServiceImpl.createPost(postRequestDto);

        // then
        verify(postRepository, times(1)).save(any(PostEntity.class));
    }

    @Test
    @DisplayName("한개의 게시물 조회 서비스 테스트 코드")
    void getOnePostTest() {
//        PostEntity post = PostEntity.builder().id()
    }

    @Test
    @DisplayName("게시물 조회 서비스 테스트 코드")
    void getAllPostTest() {

        // given
        List<PostEntity> postEntities = Arrays.asList(
                PostEntity.builder().id(1L).title("첫번째 테스트 제목").content("첫번째 테스트 내용").userName("유저 1").status(PostStatus.REGISTERED).build(),
                PostEntity.builder().id(2L).title("두번째 테스트 제목").content("두번째 테스트 내용").userName("유저 2").status(PostStatus.REGISTERED).build()
        );

        when(postRepository.findAll()).thenReturn(postEntities);

        // when
        PostListResponseDto response = postServiceImpl.allPost();

        // then
        assertNotNull(response);
        assertEquals(2, response.getSize());
        assertEquals(2, response.getPosts().size());

        assertEquals("첫번째 테스트 제목", response.getPosts().get(0).getTitle());
        assertEquals("첫번째 테스트 내용", response.getPosts().get(0).getContent());
        assertEquals("유저 1", response.getPosts().get(0).getUsername());
        assertEquals(PostStatus.REGISTERED, response.getPosts().get(0).getPostStatus());

        assertEquals("두번째 테스트 제목", response.getPosts().get(1).getTitle());
        assertEquals("두번째 테스트 내용", response.getPosts().get(1).getContent());
        assertEquals("유저 2", response.getPosts().get(1).getUsername());
        assertEquals(PostStatus.REGISTERED, response.getPosts().get(1).getPostStatus());
    }
//
//    @Test
//    @AfterEach
//    public void after() {
//        System.out.println("Test After");
//    }
//

}
