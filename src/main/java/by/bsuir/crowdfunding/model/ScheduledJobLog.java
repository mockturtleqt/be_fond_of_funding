package by.bsuir.crowdfunding.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import by.bsuir.crowdfunding.model.enumeration.LogLevel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "scheduled_job_log", schema = "be_fond_of_funding")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduledJobLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int MAX_MESSAGE_LENGTH = 2048;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private ScheduledJob job;

    @Column(name = "message", nullable = false, length = MAX_MESSAGE_LENGTH)
    private String message;

    @Column(name = "log_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private LogLevel level;

    @Column(name = "log_date", nullable = false)
    private LocalDateTime date;
}
