package by.bsuir.crowdfunding.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CollectionType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
   /* @GenericGenerator(
            name = "USER_INFO_ID_GENERATOR",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "be_fond_of_funding.user_id_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_INFO_ID_GENERATOR")*/
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
}
