package io.github.alancavalcante_dev.codefreelaapi.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "tbl_work_session")
@EntityListeners(AuditingEntityListener.class)
public class WorkSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_work_session")
    @JsonIgnore
    private UUID idWorkSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_container", nullable = false)
    @JsonIgnore
    private Container container;

    @Column(name = "comment", nullable = false)
    private String comment;

    // só pode ser preenchida se houver alteração nos commits entre essas horas de work session
    @Column(name = "comment_generated_ia", nullable = false)
    @JsonIgnore
    private String commentsGeneratedIA;

    @Column(name = "date_starting", nullable = false)
    private LocalDateTime dateStarting;

    @Column(name = "date_closing")
    private LocalDateTime dateClosing;

    @CreatedDate
    @Column(name = "date_created")
    @JsonIgnore
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private User user;
}
