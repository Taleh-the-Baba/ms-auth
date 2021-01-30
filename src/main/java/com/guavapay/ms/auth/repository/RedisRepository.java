package com.guavapay.ms.auth.repository;

import com.guavapay.ms.auth.model.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedissonClient redissonClient;

    @Value("${application.security.authentication.jwt.time-to-live-in-cache}")
    private long tokenTtl;

    /**
     * Saving token to redis with 15 minute ttl
     * @param username
     * @param token
     */
    public void save(String username, JwtToken token) {
        RBucket<JwtToken> bucket = redissonClient.getBucket(username);
        bucket.set(token, tokenTtl, TimeUnit.SECONDS);
    }

    /**
     * Delete token from redis after logout
     * @param username
     */
    public void delete(String username) {
        RBucket<JwtToken> bucket = redissonClient.getBucket(username);

        if (Objects.isNull(bucket)) {
            log.warn("{} bucket not found", username);
            return;
        }

        bucket.delete();
    }
}
