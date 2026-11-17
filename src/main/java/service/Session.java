package service;

import java.time.Instant;

/**
 * User session
 * @param accessToken User access token
 * @param refreshToken User refresh token
 * @param userId User id
 * @param userEmail User email
 * @param expiresAt How many seconds until the token expires
 */
public record Session(String accessToken, String refreshToken, String userId, String userEmail, Instant expiresAt) {}