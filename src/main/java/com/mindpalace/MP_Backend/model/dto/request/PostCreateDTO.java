package com.mindpalace.MP_Backend.model.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostCreateDTO {
    private String keyword;

    @ApiParam(hidden = true)
    private String backgroundImage;

    @NotBlank(message = "일기를 적어주세요")
    @Size(max = 500, message = "일기는 최대 500자까지 쓸 수 있습니다")
    private String text; //board contents.

    private String videoId;

    @NotNull(message = "멤버 ID는 필수 입력 값입니다")
    private String memberId;
}
