package gamza.project.backstudy.entity;

import gamza.project.backstudy.entity.Enum.PostStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private PostStatus status;

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }






}
