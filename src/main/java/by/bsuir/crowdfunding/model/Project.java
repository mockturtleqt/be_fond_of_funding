package by.bsuir.crowdfunding.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "project", schema = "be_fond_of_funding")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "project_name", length = 50, unique = true)
    @Size(max = 50)
    private String name;

    @NotBlank
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "due_date")
    private Timestamp dueDate;

    @NotNull
    @Column(name = "minumal_money_amount")
    @Min(0)
    private BigDecimal minimalMoneyAmount;

    @Column(name = "actual_money_amount")
    @Min(0)
    private BigDecimal actualMoneyAmount = BigDecimal.ZERO;

    @Column(name = "picture")
    private String picture;

    @Column(name = "additional_info")
    private String additionalInfo;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive = false;

    @NotNull
    @Column(name = "is_approved")
    private Boolean isApproved = false;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
