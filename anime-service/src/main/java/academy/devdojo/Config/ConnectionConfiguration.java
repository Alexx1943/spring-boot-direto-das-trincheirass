package academy.devdojo.Config;

import external.dependecy.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {

    @Bean
    public Connection connection() {
        return new Connection("1", "alex", "12345");
    }

}
