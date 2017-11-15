package uk.gov.digital.ho.pttg.api

import com.google.common.collect.ImmutableList
import groovy.json.JsonSlurper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import uk.gov.digital.ho.pttg.dto.FeedbackDto
import uk.gov.digital.ho.pttg.service.FeedbackService

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class FeedbackResourceSpec extends Specification {

    def feedbackJson =  "{\"nino\": \"JB557733D\", \"match\": \"yes\", \"whynot\": {\"combinedincome\": true, \"multiple_employers\": true, \"pay_frequency_change\": false}, \"caseref\": \"21111111\", \"matchOther\": \"test2\"}"

    def mockFeedbackService = Mock(FeedbackService)
    def mockRequestData = Mock(RequestData)
    def feedbackResource = new FeedbackResource(mockFeedbackService, mockRequestData)
    def jsonSlurper = new JsonSlurper()

    MockMvc mockMvc = standaloneSetup(feedbackResource).setControllerAdvice(new ResourceExceptionHandler()).build()


    def "service is called on a feedback POST request"() {
        when:
        def response = mockMvc.perform(post("/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(feedbackJson)
        )

        then:
        response.andExpect(status().isOk())
        1 * mockFeedbackService.save(feedbackJson)

    }

    def "feedback list is returned for a feedback GET request"() {

        given:
        mockFeedbackService.getFeedback() >> buildFeedbackDtoList()

        when:
        def response = mockMvc.perform(get("/feedback")
                .contentType(MediaType.APPLICATION_JSON)
        )

        then:
        response.andExpect(status().isOk())
        def feedbackList = jsonSlurper.parseText(response.andReturn().response.getContentAsString())
        feedbackList[0].uuid == 'uuid1'
    }

    def buildFeedbackDtoList() {
        ImmutableList.of(FeedbackDto.builder().detail(feedbackJson)
                                              .userId("u1")
        .sessionId("s1")
        .deployment("not used").namespace("not used").uuid("uuid1").timestamp(LocalDateTime.now()).build())
    }
}
