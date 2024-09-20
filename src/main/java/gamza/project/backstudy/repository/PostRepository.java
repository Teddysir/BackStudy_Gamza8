package gamza.project.backstudy.repository;

import gamza.project.backstudy.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
