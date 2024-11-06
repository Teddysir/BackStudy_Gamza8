package gamza.project.backstudy.dto.post;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;

}
