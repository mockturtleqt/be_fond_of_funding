package by.bsuir.crowdfunding.rest;

import by.bsuir.crowdfunding.model.enumeration.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CompleteUserDto {

    private Long userId;

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

    @ApiModelProperty
    @Size(max = 30)
    private String phoneNumber;

    @ApiModelProperty
    private String profilePicture;

    @ApiModelProperty
    private BigDecimal balance;

    @ApiModelProperty(required = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    private UserRole userRole;
}
