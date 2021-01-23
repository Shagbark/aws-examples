package org.jteam.aws.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jteam.aws.model.dto.TopicSubscribeRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.ListTopicsRequest;
import software.amazon.awssdk.services.sns.model.ListTopicsResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;
import software.amazon.awssdk.services.sns.model.Topic;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/sns")
@RequiredArgsConstructor
public class SNSController {

    private final SnsClient snsClient;

    @GetMapping
    public Collection<String> listOfSubscribers() {
        ListTopicsRequest request = ListTopicsRequest.builder()
                .build();

        ListTopicsResponse result = snsClient.listTopics(request);
        log.debug("SNS response status: {}", result.sdkHttpResponse().statusCode());

        return result.topics().stream()
                .map(Topic::topicArn)
                .collect(Collectors.toList());
    }

    @PostMapping("/receive")
    public void snsEndpoint(@RequestBody String request) {
        log.info("SNS request: {}", request);
    }

    @PostMapping("/subscribe")
    public void subscribeApplication(@RequestBody TopicSubscribeRequest topicSubscribeRequest) {
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .protocol("http")
                // TODO: configure docker to use 127.0.0.1 instead of the computer's network address
                .endpoint("http://192.168.1.250:9090/sns/receive") // an endpoint on which SNS will send a notification
                .returnSubscriptionArn(true)
                .topicArn(topicSubscribeRequest.getArn())
                .build();

        try {
            SubscribeResponse result = snsClient.subscribe(subscribeRequest);
            log.info("Successfully subscribed to SNS. Status: {}", result.sdkHttpResponse().statusCode());
        } catch (SnsException exc) {
            log.error("Couldn't subscribe to SNS: {}", exc.getMessage(), exc);
        }
    }

}
