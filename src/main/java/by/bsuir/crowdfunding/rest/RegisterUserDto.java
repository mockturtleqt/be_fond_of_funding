package by.bsuir.crowdfunding.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RegisterUserDto {

    @NotBlank
    @ApiModelProperty(required = true)
    @Size(max = 20)
    private String firstName;

    @NotBlank
    @ApiModelProperty(required = true)
    @Size(max = 20)
    private String lastName;

    @NotBlank
    @ApiModelProperty(required = true)
    @Size(max = 20)
    private String login;

    @NotBlank
    @ApiModelProperty(required = true)
    @Size(max = 30)
    private String password;

    @Email
    @NotBlank
    @ApiModelProperty(required = true)
    @Size(max = 30)
    private String email;
}


