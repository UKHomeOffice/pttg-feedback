package uk.gov.digital.ho.pttg.jpa;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Access(AccessType.FIELD)
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid")
public class Feedback {

    static final String NOT_USED = "NOT USED";
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid", nullable = false)
    @Getter
    private String uuid;

    @Column(name = "timestamp", nullable = false)
    @Getter
    private LocalDateTime timestamp;

    @Column(name = "session_id", nullable = false)
    @Getter
    private String sessionId;

    @Column(name = "deployment", nullable = false)
    @Getter
    private String deployment = NOT_USED;

    @Column(name = "namespace", nullable = false)
    @Getter
    private String namespace = NOT_USED;

    @Column(name = "user_id", nullable = false)
    @Getter
    private String userId;

    @Column(name = "detail", nullable = false)
    @Getter
    private String detail;

    public Feedback(String uuid, LocalDateTime timestamp, String sessionId,
                  String userId, String detail) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.sessionId = sessionId;
        this.userId = userId;
        this.detail = detail;
    }
}
