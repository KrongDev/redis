package com.geonlee.redis.example.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RedisPublisherTest {
    @Autowired
    private RedisPublisher redisPublisher;

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Test
    void publish() throws InterruptedException {
        // GIVEN
        String topicName = "testTopic";
        ChannelTopic topic = new ChannelTopic(topicName);
        String message = "hello world";
        String[] subscribeMessage = new String[1];
        CountDownLatch latch = new CountDownLatch(1);

        reactiveRedisTemplate.listenToChannel(topicName).doOnNext(msg -> {
            subscribeMessage[0] = msg.getMessage();
            latch.countDown();
        }).subscribe();
        latch.await(1, TimeUnit.SECONDS);
        // WHEN
        redisPublisher.publish(topic, message);

        // THEN
        boolean received = latch.await(3, TimeUnit.SECONDS);
        assertThat(received).isTrue();
        assertThat(message).isEqualTo(subscribeMessage[0]);
    }
}