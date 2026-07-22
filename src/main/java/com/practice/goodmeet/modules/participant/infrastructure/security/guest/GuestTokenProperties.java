package com.practice.goodmeet.modules.participant.infrastructure.security.guest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/** Configuration for anonymous participant credentials scoped to a meeting. */
@Validated
@ConfigurationProperties("application.participant.guest-token")
public record GuestTokenProperties(
    @NotBlank String issuer,
    @NotBlank String audience,
    @NotBlank @Size(min = 32) String secret,
    @NotNull Duration tokenTtl) {

  /** Validates the minimum guest-token lifetime. */
  public GuestTokenProperties {
    if (tokenTtl != null && tokenTtl.compareTo(Duration.ofMinutes(1)) < 0) {
      throw new IllegalArgumentException("tokenTtl must be at least one minute");
    }
  }
}
