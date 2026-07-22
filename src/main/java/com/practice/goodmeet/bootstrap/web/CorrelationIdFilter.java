package com.practice.goodmeet.bootstrap.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Adds a correlation identifier to every HTTP request and response. */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public final class CorrelationIdFilter extends OncePerRequestFilter {

  public static final String HEADER_NAME = "X-Correlation-ID";
  public static final String MDC_KEY = "correlationId";
  private static final int MAX_CORRELATION_ID_LENGTH = 128;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String correlationId = resolveCorrelationId(request.getHeader(HEADER_NAME));
    MDC.put(MDC_KEY, correlationId);
    response.setHeader(HEADER_NAME, correlationId);

    try {
      filterChain.doFilter(request, response);
    } finally {
      MDC.remove(MDC_KEY);
    }
  }

  private String resolveCorrelationId(String requestedCorrelationId) {
    if (requestedCorrelationId == null
        || requestedCorrelationId.isBlank()
        || requestedCorrelationId.length() > MAX_CORRELATION_ID_LENGTH) {
      return UUID.randomUUID().toString();
    }
    return requestedCorrelationId;
  }
}
