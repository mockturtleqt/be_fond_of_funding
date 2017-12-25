package by.bsuir.crowdfunding.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {

    @NotBlank
    @ApiModelProperty(required = true)
    @Size(max = 20)
    private String login;

    @NotBlank
    @ApiModelProperty(required = true)
    @Size(max = 30)
    private String password;
}
