package by.bsuir.crowdfunding.rest;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank
    @ApiModelProperty(required = true)
    @Size(max = 20)
    private String login;

    @NotBlank
    @ApiModelProperty(required = true)
    @Size(max = 30)
    private String password;
}
