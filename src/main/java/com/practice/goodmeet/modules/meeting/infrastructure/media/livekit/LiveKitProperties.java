package com.practice.goodmeet.modules.meeting.infrastructure.media.livekit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.net.URI;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/** Connection and token configuration for the LiveKit media adapter. */
@Validated
@ConfigurationProperties("application.livekit")
public record LiveKitProperties(
    @NotNull URI url,
    @NotBlank String apiKey,
    @NotBlank @Size(min = 32) String apiSecret,
    @NotNull Duration tokenTtl,
    @NotBlank @Pattern(regexp = "[a-z0-9-]+") String roomPrefix) {

  /** Validates the short-lived LiveKit access-token lifetime. */
  public LiveKitProperties {
    if (tokenTtl != null
        && (tokenTtl.compareTo(Duration.ofMinutes(1)) < 0
            || tokenTtl.compareTo(Duration.ofMinutes(15)) > 0)) {
      throw new IllegalArgumentException("tokenTtl must be between one and fifteen minutes");
    }
  }
}
