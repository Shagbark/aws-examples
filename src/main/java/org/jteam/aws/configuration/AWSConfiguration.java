package org.jteam.aws.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;

@Configuration
public class AWSConfiguration {

    @Bean
    public SnsClient snsClient(AwsCredentialsProvider awsCredentialsProvider) {
        return SnsClient.builder()
                .credentialsProvider(awsCredentialsProvider)
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.EU_CENTRAL_1)
                .build();
    }

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        return EnvironmentVariableCredentialsProvider.create();
    }

}
