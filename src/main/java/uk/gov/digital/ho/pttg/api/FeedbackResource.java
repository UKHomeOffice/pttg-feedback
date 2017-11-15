package uk.gov.digital.ho.pttg.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.digital.ho.pttg.dto.FeedbackDto;
import uk.gov.digital.ho.pttg.service.FeedbackService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class FeedbackResource {

    private final FeedbackService feedbackService;
    private final RequestData requestData;

    public FeedbackResource(FeedbackService feedbackService, RequestData requestData) {
        this.feedbackService = feedbackService;
        this.requestData = requestData;
    }

    @PostMapping(value = "/feedback", consumes = APPLICATION_JSON_VALUE)
    public void recordFeedback(@RequestBody String feedback) {
        log.info("Feedback received for session id {}", requestData.sessionId());
        feedbackService.save(feedback);
    }

    @GetMapping(value = "/feedback", produces = APPLICATION_JSON_VALUE)
    public List<FeedbackDto> getFeedback() {
        log.info("returning list of feedback");
        return feedbackService.getFeedback();
    }

}