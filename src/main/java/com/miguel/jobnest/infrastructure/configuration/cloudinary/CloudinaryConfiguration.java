package com.miguel.jobnest.infrastructure.configuration.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {

    @Value("${spring.cloudinary.cloud-name}")
    private String cloudinaryCloudName;

    @Value("${spring.cloudinary.api-key}")
    private String cloudinaryApiKey;

    @Value("${spring.cloudinary.api-secret}")
    private String cloudinaryApiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap("cloud_name", this.cloudinaryCloudName, "api_key", this.cloudinaryApiKey, "api_secret", this.cloudinaryApiSecret)
        );
    }
}
