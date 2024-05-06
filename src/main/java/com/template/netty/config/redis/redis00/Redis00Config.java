package com.template.netty.config.redis.redis00;

import com.template.netty.config.redis.configProps.RedisDataSource;
import com.template.netty.db.redis00.entity.IpTableCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;

import static java.util.Collections.singletonMap;

@Configuration
@Slf4j
public class Redis00Config {

    @Bean("redis00Template")
    public RedisTemplate<String, IpTableCache> redis00Template(@Qualifier("redis00ConnectionFactory") LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, IpTableCache> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);

        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(@Qualifier("redis00ConnectionFactory") LettuceConnectionFactory connectionFactory,
                                     @Qualifier("redis00DefaultCacheConfiguration") RedisCacheConfiguration defaultCacheConfiguration) {
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultCacheConfiguration)
                .withInitialCacheConfigurations(singletonMap("predefined", defaultCacheConfiguration.disableCachingNullValues()))
                .transactionAware()
                .build();
    }

    @Bean("redis00DefaultCacheConfiguration")
    public RedisCacheConfiguration defaultCacheConfiguration() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofDays(1L))
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
                );
    }

    @Bean("redis00ConnectionFactory")
    public LettuceConnectionFactory redis00ConnectionFactory(@Qualifier("redis00RedisDataSource") RedisDataSource redisDataSource) {
        return new LettuceConnectionFactory(
                new RedisStandaloneConfiguration(
                        redisDataSource.getRedis().get("redis00").getHOST(),
                        redisDataSource.getRedis().get("redis00").getPORT()));
    }

    @Bean(name = "redis00RedisDataSource")
    public RedisDataSource redis00DataSource() {
        return RedisDataSource.builder().build();
    }

    /*# Redis Repository 에서 사용#*/
    @Bean(name = "redis00TransactionManager")
    public PlatformTransactionManager redis00TransactionManager(@Qualifier("redis00RepoDataSource") DataSource redis00RepoDataSource) throws SQLException {
        return new DataSourceTransactionManager(redis00RepoDataSource);
    }

    @Bean(name = "redis00RepoDataSource")
    @ConfigurationProperties(prefix = "spring.datasources.nosql.redis.redis00")
    public DataSource redis00RepoDataSource() throws SQLException {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        databaseBuilder.setName("transactionDatabase");
        databaseBuilder.setType(EmbeddedDatabaseType.H2);
        return databaseBuilder.build();
    }
}
