package hello.config;

import hello.pubsub.JwtPubSub;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


//mvnw package && java -jar target/gs-spring-boot-docker-0.1.0.jar

@Service("appConfig")
@Scope("singleton")
public class AppConfig {


    private static RedissonClient redClient = null;

    public AppConfig() {
        super();
        JwtPubSub.setup();
        JwtPubSub.preload_authenticationMode();
        JwtPubSub.preload_authorizationMode();
        JwtPubSub.preload_addLogin();
        JwtPubSub.preload_addToken();
        JwtPubSub.preload_defaultExpire();
        JwtPubSub.preload_addLoginRoles();
        JwtPubSub.preload_syncLogin();
    }

    public static RedissonClient getRedClient() {
        if (redClient == null) {
            Config config = new Config();
            config.useSingleServer()
                    .setAddress("redis://127.0.0.1:6379");
            redClient = Redisson.create(config);
        }
        return redClient;
    }
}
