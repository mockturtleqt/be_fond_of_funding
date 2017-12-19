package by.bsuir.crowdfunding.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import by.bsuir.crowdfunding.model.enumeration.JobStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "scheduled_job", schema = "be_fond_of_funding")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduledJob implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_name", nullable = false, length = 32)
    @Size(max = 32)
    private String name;

    @Column(name = "job_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @Column(name = "job_start_time", nullable = false)
    private LocalDateTime jobStartTime;

    @Column(name = "job_end_time")
    private LocalDateTime jobEndTime;

    @OneToMany(mappedBy = "job", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ScheduledJobLog> logs;

    public void addLog(final ScheduledJobLog log) {
        if (getLogs() == null) {
            this.logs = new ArrayList<>();
        }
        log.setJob(this);
        this.logs.add(log);
    }
}
