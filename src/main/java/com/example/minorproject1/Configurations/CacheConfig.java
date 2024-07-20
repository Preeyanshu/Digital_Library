package com.example.minorproject1.Configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CacheConfig {

    @Bean
    public LettuceConnectionFactory getConnectionFactory(){

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("localhost",6380);
//        configuration.setPassword("jyKIu7mFf2JuLBiMmYLXXnb26t8PVv02");

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration);


        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String,Object> getTemplate(){      //Generic template for all entities

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(getConnectionFactory());

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        //for hash data structures
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());


        return redisTemplate;

    }

    @Bean
    public ObjectMapper getMapper(){
        return new ObjectMapper();
    }

}
