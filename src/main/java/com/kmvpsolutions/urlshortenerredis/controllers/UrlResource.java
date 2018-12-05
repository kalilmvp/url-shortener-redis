package com.kmvpsolutions.urlshortenerredis.controllers;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/rest/url")
public class UrlResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlResource.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("{id}")
    public String find(@PathVariable("id") String id) {
        String url = this.redisTemplate.opsForValue().get(id);

        if (url == null)
            throw new RuntimeException("URL was not found for ID: " + id);

        return url;
    }

    @PostMapping
    public String create(@RequestBody String url) {
        UrlValidator validator = new UrlValidator(new String[]{"http", "https"});

        if (validator.isValid(url)) {
            String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
            LOGGER.info("URL shortened generated: " + id);
            this.redisTemplate.opsForValue().set(id, url);
            return id;
        }

        throw new RuntimeException("Invalid URL: " + url);
    }
}
