package com.jungle.okrs;

import com.jungle.okrs.db.JsonDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.net.InetAddress;


@SpringBootApplication
@EnableOpenApi
@Slf4j
public class Application {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        showApplicationInfo(context);
    }

    @Bean(name = "jsonDB")
    public JsonDB getJsonDB() {

        return JsonDB.init();
    }

    private static void showApplicationInfo(ConfigurableApplicationContext context) {
        Environment env = context.getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost()
                    .getHostAddress();
        } catch (Exception e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        String portal = env.getProperty("server.port");
        log.info("\n----------------------------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t"
                        + "Local: \t\t{}://localhost:{}\n\t"
                        + "External: \t{}://{}:{}\n\t"
                        + "Swagger-ui: \t{}://{}:{}/swagger-ui/index.html\n\t"
                        + "Swagger-ui: \thttp://localhost:{}/swagger-ui/index.html\n\t"
                        + "Profile(s): \t{}\n\t"
                        + "\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                portal,
                protocol,
                hostAddress,
                portal,
                protocol,
                hostAddress,
                portal,
                portal,
                env.getActiveProfiles()
        );
        // 设置 Node HostAddress
    }
}
