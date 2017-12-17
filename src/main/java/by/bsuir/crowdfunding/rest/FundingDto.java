package by.bsuir.crowdfunding.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FundingDto {

    @NotNull
    @ApiModelProperty(required = true)
    private Long userId;

    @NotNull
    @ApiModelProperty(required = true)
    private Long projectId;

    @NotNull
    @ApiModelProperty(required = true)
    @Min(0)
    private BigDecimal amountOfMoney;
}
