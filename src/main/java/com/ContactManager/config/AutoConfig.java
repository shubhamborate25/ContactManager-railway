package com.ContactManager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
public class AutoConfig {

	@Value("${aws.access.key}")
	private String accesskey;

	@Value("${aws.secret.key}")
	private String secretkey;

	@Value("${aws.region}")
	private String region;

	@Bean
	public AmazonS3 amazonS3() {
		BasicAWSCredentials Credentials = new BasicAWSCredentials(accesskey, secretkey);

		return AmazonS3Client.builder().withRegion(region)
				.withCredentials(new AWSStaticCredentialsProvider(Credentials)).build();

	}

}
