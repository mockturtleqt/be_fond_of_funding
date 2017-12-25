package by.bsuir.crowdfunding.model;

import by.bsuir.crowdfunding.model.enumeration.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
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
