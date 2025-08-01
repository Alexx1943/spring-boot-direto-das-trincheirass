package academy.devdojo.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {

    @Value("${database.url}")
    private String url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Bean
    public Connection connectionMySql() {
        return new Connection(url, username, password);
    }

    @Bean(name = "connectionMongoBD")
    public Connection connectionMongo() {
        return new Connection(url, username, password);
    }

}
