// src/main/java/com/siyamuddin/blog/blogappapis/Config/RateLimitConfig.java
package com.siyamuddin.blog.blogappapis.Config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Value("${app.rate-limit.login.requests:5}")
    private int loginRequests;

    @Value("${app.rate-limit.login.duration:60}")
    private int loginDuration;

    @Value("${app.rate-limit.registration.requests:3}")
    private int registrationRequests;

    @Value("${app.rate-limit.registration.duration:3600}")
    private int registrationDuration;

    @Value("${app.rate-limit.post.requests:10}")
    private int postRequests;

    @Value("${app.rate-limit.post.duration:3600}")
    private int postDuration;

    @Value("${app.rate-limit.comment.requests:20}")
    private int commentRequests;

    @Value("${app.rate-limit.comment.duration:3600}")
    private int commentDuration;

    @Value("${app.rate-limit.general.requests:100}")
    private int generalRequests;

    @Value("${app.rate-limit.general.duration:60}")
    private int generalDuration;

    @Bean
    public Bucket loginRateLimitBucket() {
        Bandwidth limit = Bandwidth.classic(loginRequests, Refill.intervally(loginRequests, Duration.ofHours(loginDuration)));
        return Bucket.builder().addLimit(limit).build();
    }

    @Bean
    public Bucket registrationRateLimitBucket() {
        Bandwidth limit = Bandwidth.classic(registrationRequests, Refill.intervally(registrationRequests, Duration.ofHours(registrationDuration)));
        return Bucket.builder().addLimit(limit).build();
    }

    @Bean
    public Bucket postCreationRateLimitBucket() {
        Bandwidth limit = Bandwidth.classic(postRequests, Refill.intervally(postRequests, Duration.ofHours(postDuration)));
        return Bucket.builder().addLimit(limit).build();
    }

    @Bean
    public Bucket commentCreationRateLimitBucket() {
        Bandwidth limit = Bandwidth.classic(commentRequests, Refill.intervally(commentRequests, Duration.ofHours(commentDuration)));
        return Bucket.builder().addLimit(limit).build();
    }

    @Bean
    public Bucket generalApiRateLimitBucket() {
        Bandwidth limit = Bandwidth.classic(generalRequests, Refill.intervally(generalRequests, Duration.ofHours(generalDuration)));
        return Bucket.builder().addLimit(limit).build();
    }
}