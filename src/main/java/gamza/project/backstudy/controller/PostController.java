package gamza.project.backstudy.controller;

import gamza.project.backstudy.dto.PostRequestDto;
import gamza.project.backstudy.service.inter.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto dto) {
        postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.OK).body("게시물이 생성되었습니다.");
    }
}
