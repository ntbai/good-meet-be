package com.practice.goodmeet.bootstrap.security;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/** Browser origins allowed to call the application HTTP and WebSocket entry points. */
@Validated
@ConfigurationProperties("application.cors")
public record CorsProperties(@NotEmpty List<String> allowedOrigins) {

  /** Creates an immutable configuration snapshot. */
  public CorsProperties {
    allowedOrigins = allowedOrigins == null ? List.of() : List.copyOf(allowedOrigins);
  }
}
