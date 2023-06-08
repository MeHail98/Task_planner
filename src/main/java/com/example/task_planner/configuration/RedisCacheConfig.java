package com.example.task_planner.configuration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.time.Duration;


/**
 * По дефолту spring boot создает бин RedisCacheManager при подключении зависимости редиса и spring кеша,
 * где есть дефолтные настройки того как кешировать, но можно создать свои бины и задать свои конфиги.
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {
    /**
     * .entryTtl(Duration.ofMinutes(15) - указываем колличество минут, которые объкты будут храниться в кеше
     * .disableCachingNullValues() - в кеш нельзя класть null.
     * .serializeValuesWith - меняем логику дефолтной сериализации(т.к. все объекты когда попадают в кеш -
     * сереализуются)
     */
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(15))
                //.disableCachingNullValues()
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    /**
     * Тут делаем еще более гибкую настройку, то есть уже на основе выше созданного бина RedisCacheConfiguration
     * делаем как-бы отдельно 2 пространства для кеша itemCache,customerCache и для них переопределяем
     * дефолтное поведение RedisCacheConfiguration. В перово варианте поменяли срок хранения объектов в кеше на 10
     * мин, во втором на 5.
     */
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("itemCache",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration("customerCache",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
    }

}
