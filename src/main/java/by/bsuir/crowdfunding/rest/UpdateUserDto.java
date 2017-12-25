package by.bsuir.crowdfunding.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UpdateUserDto {

    @NotNull
    @ApiModelProperty(required = true)
    private Long userId;

    @NotBlank
    @ApiModelProperty(required = true)
    @Size(max = 20)
    private String firstName;

    @NotBlank
    @ApiModelProperty(required = true)
    @Size(max = 20)
    private String lastName;

    @ApiModelProperty
    @Size(max = 30)
    private String phoneNumber;

    @ApiModelProperty
    private String profilePicture;

    @ApiModelProperty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
}

