package uk.gov.digital.ho.pttg.api;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Component
public class RequestData implements HandlerInterceptor {

    private static final String SESSION_ID_HEADER = "x-session-id";
    private static final String CORRELATION_ID_HEADER = "x-correlation-id";
    static final String USER_ID_HEADER = "x-auth-userid";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        MDC.clear();
        MDC.put(SESSION_ID_HEADER, initialiseSessionId(request));
        MDC.put(CORRELATION_ID_HEADER, initialiseCorrelationId(request));
        MDC.put(USER_ID_HEADER, initialiseUserName(request));
        MDC.put("userHost", request.getRemoteHost());

        return true;
    }

    private String initialiseSessionId(HttpServletRequest request) {
        String sessionId = WebUtils.getSessionId(request);
        return StringUtils.isNotBlank(sessionId) ? sessionId : "unknown";
    }

    private String initialiseCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        return StringUtils.isNotBlank(correlationId) ? correlationId : UUID.randomUUID().toString();
    }

    private String initialiseUserName(HttpServletRequest request) {
        String userId = request.getHeader(USER_ID_HEADER);
        return StringUtils.isNotBlank(userId) ? userId : "anonymous";
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MDC.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public String sessionId() {
        return MDC.get(SESSION_ID_HEADER);
    }


    public String userId() {
        return MDC.get(USER_ID_HEADER);
    }
}
