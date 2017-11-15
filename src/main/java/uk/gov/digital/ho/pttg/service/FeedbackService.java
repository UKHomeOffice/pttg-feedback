package uk.gov.digital.ho.pttg.service;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.digital.ho.pttg.api.RequestData;
import uk.gov.digital.ho.pttg.dto.FeedbackDto;
import uk.gov.digital.ho.pttg.jpa.Feedback;
import uk.gov.digital.ho.pttg.jpa.FeedbackJpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FeedbackService {

    private final FeedbackJpaRepository repository;
    private final RequestData requestData;

    public FeedbackService(FeedbackJpaRepository repository, RequestData requestData) {
        this.repository = repository;
        this.requestData = requestData;
    }

    @Transactional
    public void save(String feedback) {
        repository.save(buildFeedbackJpa(feedback));
    }

    private Feedback buildFeedbackJpa(String feedback) {

        return new Feedback(UUID.randomUUID().toString(),
                LocalDateTime.now(),
                requestData.sessionId(),
                requestData.userId(),
                feedback);
    }

    public List<FeedbackDto> getFeedback() {
        return ImmutableList.copyOf(repository.findAllByOrderByTimestampDesc()).stream().map(FeedbackService::buildFeedbackDto).collect(Collectors.toList());
    }

    private static FeedbackDto buildFeedbackDto(Feedback f) {
        return FeedbackDto.builder()
                .sessionId(f.getSessionId())
                .timestamp(f.getTimestamp())
                .userId(f.getUserId())
                .uuid(f.getUuid())
                .deployment(f.getDeployment())
                .namespace(f.getNamespace())
                .detail(f.getDetail())
                .build();
    }

}
