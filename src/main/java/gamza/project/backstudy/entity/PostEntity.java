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

    @Column(nullable = false)
    private String userName; // UserEntity 추가될경우 매핑을 통해 받아 올 예정  임시 유저 저장

    @Column(nullable = false)
    private PostStatus status; // 휴지통에 들어간거 꺼낼 수 있게 하고싶은데 아직 구현안해봄 ㅋ.ㅋ


    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }






}
