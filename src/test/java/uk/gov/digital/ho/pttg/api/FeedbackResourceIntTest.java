package uk.gov.digital.ho.pttg.api;

import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.digital.ho.pttg.dto.FeedbackDto;

import java.io.IOException;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/sql/FeedbackResourceIntTest/before.sql")
})
public class FeedbackResourceIntTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldRetrieveOrderedFeedback() throws IOException, JSONException {
        String feedbackRecords = restTemplate.getForObject("/feedback", String.class);
        String expectedRecords = IOUtils.toString(getClass().getResourceAsStream("/expected.json"));

        JSONAssert.assertEquals(feedbackRecords, expectedRecords, false);
    }

    @Test
    public void shouldPersistFeedbackAndRetrieveOrderedFeedback() throws IOException, JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(RequestData.USER_ID_HEADER, "some user ID just created");
        HttpEntity<String> entity = new HttpEntity<>(getFeedbackBody(), headers);
        restTemplate.postForObject("/feedback", entity, String.class);

        final ResponseEntity<List<FeedbackDto>> feedback = restTemplate.exchange("/feedback", HttpMethod.GET, null, new ParameterizedTypeReference<List<FeedbackDto>>() {
        });
        Assertions.assertThat(feedback.getBody()).extracting("userId")
                .contains("some user ID just created");
    }

    private String getFeedbackBody() {
        return "{\"feedback\" : \"contents\", }";
    }

}