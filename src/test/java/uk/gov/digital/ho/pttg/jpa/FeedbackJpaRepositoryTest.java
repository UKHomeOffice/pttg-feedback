package uk.gov.digital.ho.pttg.jpa;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FeedbackJpaRepositoryTest {

    private static final String FEEDBACK_CONTENTS = "feedback contents";
    private static final String SESSION_ID = "session_id";
    private static final String UU_ID = UUID.randomUUID().toString();
    private static final String USER_ID = "user_id";
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static LocalDateTime NOW_MINUS_60_MINS = LocalDateTime.now().minusMinutes(60);
    private static LocalDateTime NOW_PLUS_60_MINS = LocalDateTime.now().plusMinutes(60);


    @Autowired
    private FeedbackJpaRepository repo;

    @Before
    public void setUp() throws Exception {
        repo.save(new Feedback(UU_ID, NOW, SESSION_ID, USER_ID, FEEDBACK_CONTENTS));
        repo.save(new Feedback(UU_ID, NOW_MINUS_60_MINS, SESSION_ID, USER_ID, FEEDBACK_CONTENTS));
        repo.save(new Feedback(UU_ID, NOW_PLUS_60_MINS, SESSION_ID, USER_ID, FEEDBACK_CONTENTS));
    }

    @Test
    public void feedbackPersistsWithoutError() throws Exception {
        final String uuid =UUID.randomUUID().toString();
        repo.save(new Feedback(uuid, LocalDateTime.now(), SESSION_ID, USER_ID, FEEDBACK_CONTENTS));
    }

    @Test
    public void shouldRetrieveAllFeedbackOrderedByTimestampDesc() {

        final Iterable<Feedback> all = repo.findAllByOrderByTimestampDesc();
        assertThat(all).hasSize(3);
        assertThat(all).extracting("timestamp").containsExactly(NOW_PLUS_60_MINS, NOW, NOW_MINUS_60_MINS);
    }

    @Test
    public void feedbackRecordIncludesDefaults() throws Exception {
        repo.findAll().forEach(next -> {
            assertThat(next.getDeployment()).isEqualTo(Feedback.NOT_USED);
            assertThat(next.getNamespace()).isEqualTo(Feedback.NOT_USED);
            assertThat(next.getSessionId()).isEqualTo(SESSION_ID);
            assertThat(next.getUserId()).isEqualTo(USER_ID);
            assertThat(next.getDetail()).isEqualTo(FEEDBACK_CONTENTS);
        });
    }

}