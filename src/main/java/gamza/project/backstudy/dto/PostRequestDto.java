package gamza.project.backstudy.dto;

import gamza.project.backstudy.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostRequestDto {

    private String title;
    private String content;
    private String username;

}
