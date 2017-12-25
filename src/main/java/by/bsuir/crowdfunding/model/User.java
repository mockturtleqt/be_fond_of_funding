package by.bsuir.crowdfunding.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import by.bsuir.crowdfunding.model.enumeration.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static by.bsuir.crowdfunding.model.enumeration.UserRole.USER;

@Entity
@Table(name = "user_info", schema = "be_fond_of_funding")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString(exclude = "password")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "first_name", length = 20)
    @Size(max = 20)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", length = 20)
    @Size(max = 20)
    private String lastName;

    @NotBlank
    @Column(name = "login", unique = true, length = 20)
    @Size(max = 20)
    private String login;

    @NotBlank
    @Column(name = "pass", length = 100)
    @Size(max = 100)
    @JsonIgnore
    private String password;

    @NotBlank
    @Column(name = "email", unique = true, length = 30)
    @Size(max = 30)
    private String email;

    @Column(name = "birth_date")
    private Timestamp birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profilePicture")
    private String profilePicture;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = USER;

    @Column(name = "balance")
    @Min(0)
    private BigDecimal balance;

    @Column(name = "enabled")
    private boolean enabled;
}
