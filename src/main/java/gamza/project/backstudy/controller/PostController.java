package gamza.project.backstudy.controller;

import gamza.project.backstudy.dto.PostListResponseDto;
import gamza.project.backstudy.dto.PostOneResponseDto;
import gamza.project.backstudy.dto.PostRequestDto;
import gamza.project.backstudy.dto.PostUpdateRequestDto;
import gamza.project.backstudy.service.inter.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public PostListResponseDto getAllPost() {
        return postService.allPost();
    }

    @GetMapping("/{id}")
    public PostOneResponseDto getOnePost(@PathVariable("id") Long id) {
        return postService.findOnePost(id);
    }

    @PostMapping("")
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto dto) {
        postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.OK).body("게시물이 생성되었습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@RequestBody PostUpdateRequestDto dto, @PathVariable("id") Long id) {
        postService.updatePost(dto, id);
        return ResponseEntity.status(HttpStatus.OK).body("게시물이 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body("게시물이 삭제되었습니다.");
    }
}
