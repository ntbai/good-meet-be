package com.practice.goodmeet.modules.identity.infrastructure.security.jwt;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/** Configuration for registered-user platform access tokens. */
@Validated
@ConfigurationProperties("application.identity.tokens")
public record PlatformTokenProperties(
    @NotBlank String issuer,
    @NotBlank String audience,
    @NotBlank @Size(min = 32) String secret,
    @NotNull Duration accessTokenTtl) {

  /** Validates the minimum platform-token lifetime. */
  public PlatformTokenProperties {
    if (accessTokenTtl != null && accessTokenTtl.compareTo(Duration.ofMinutes(1)) < 0) {
      throw new IllegalArgumentException("accessTokenTtl must be at least one minute");
    }
  }
}
