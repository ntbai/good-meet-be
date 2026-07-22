package com.practice.goodmeet.bootstrap.security;

import java.time.Instant;

/** Stable error payload returned by the security filter boundary. */
record SecurityErrorResponse(String code, String message, Instant timestamp, String traceId) {}
