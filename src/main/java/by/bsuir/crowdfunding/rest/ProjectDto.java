package by.bsuir.crowdfunding.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
public class ProjectDto {

    @NotBlank
    @ApiModelProperty(required = true)
    @Size(max = 50)
    private String name;

    @NotBlank
    @ApiModelProperty(required = true)
    private String description;

    @NotNull
    @ApiModelProperty(required = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;

    @NotNull
    @ApiModelProperty(required = true)
    @Min(0)
    private BigDecimal minimalMoneyAmount;

    @Min(0)
    private BigDecimal actualMoneyAmount;

    private String picture;

    private String additionalInfo;

    private Long userId;

    private Long projectId;
}
