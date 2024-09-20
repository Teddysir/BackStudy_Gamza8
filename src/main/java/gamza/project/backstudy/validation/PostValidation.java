package gamza.project.backstudy.validation;

import gamza.project.backstudy.entity.PostEntity;
import gamza.project.backstudy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostValidation {

    private final PostRepository postRepository;

    public PostEntity isPresentPost(Long id) { // 그냥 유효성 검사만 하는게 나을까 아니면 받은김에 postId 값도 반환하는게 나을까? SOLID 위반인가?
        Optional<PostEntity> post = postRepository.findById(id);

        if(post.isEmpty()) {
            throw new NoSuchElementException("해당 게시물은 존재하지 않습니다."); // 나중에 커스텀 에러가 있다면 그 커스텀 에러를 던져도될듯
        }

        return post.get();
    }

    public PostEntity isValidateUserName(Long id, String username) {
        Optional<PostEntity> post = postRepository.findById(id);

        if(post.isEmpty()) {
            throw new NoSuchElementException("해당 게시물은 존재하지 않습니다."); // 나중에 커스텀 에러가 있다면 그 커스텀 에러를 던져도될듯
        }

        if(!post.get().getUserName().equals(username)){
            throw new NoSuchElementException("해당 게시물 수정 권한이 없습니다.");
        }

        return post.get();

    }
}
