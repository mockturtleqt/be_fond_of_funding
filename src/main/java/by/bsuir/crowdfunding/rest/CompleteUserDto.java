package by.bsuir.crowdfunding.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import by.bsuir.crowdfunding.model.enumeration.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
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
public class CompleteUserDto {

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
