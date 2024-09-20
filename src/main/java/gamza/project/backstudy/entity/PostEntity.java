package gamza.project.backstudy.entity;

import gamza.project.backstudy.entity.Enum.PostStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private PostStatus status;


}
