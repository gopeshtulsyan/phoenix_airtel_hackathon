package in.wynk.phoenix.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 20/05/17.
 */
@Configuration
@PropertySource("classpath:dev/app.properties")
public class AppConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private String redisPort;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        if (StringUtils.isEmpty(redisHost) || StringUtils.isEmpty(redisPort))
            throw new RuntimeException("Redis host or redis port is not set, exiting");

        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisHost);
        factory.setPort(Integer.parseInt(redisPort));

        // setting time out as 10 sec default is 2 seconds
        factory.setTimeout(20000);

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(1000);
        poolConfig.setMaxIdle(1);

        factory.setPoolConfig(poolConfig);
        factory.setUsePool(true);
        return factory;
    }
}
