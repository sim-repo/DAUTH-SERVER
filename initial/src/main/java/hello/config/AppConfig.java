package hello.config;

import hello.pubsub.JwtPubSub;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

//mvnw package && java -jar target/gs-spring-boot-docker-0.1.0.jar
//mvnw package && java -DredisURL="redis://127.0.0.1:6379" -jar target/gs-spring-boot-docker-0.1.0.jar

@Service("appConfig")
@Scope("singleton")

public class AppConfig {

    @Autowired
    private Environment env;
    private static RedissonClient redClient = null;
    private static String RedisURL="";

    public void setup(String _redisURL) {
        RedisURL = _redisURL;
        Config config = new Config();
        config.useSingleServer().setAddress(RedisURL);
        redClient = Redisson.create(config);

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
        return redClient;
    }
}
