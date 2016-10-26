package co.edu.udea.redshift.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoConfig {

    private String database;
    private String host;
    private Integer port;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public
    @Bean
    Mongo mongo() throws Exception {
        MongoClientURI mongoClientURI = new MongoClientURI(String.format("mongodb://%s:%s", host, port));
        return new MongoClient(mongoClientURI);
    }

    public
    @Bean
    MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), database);
    }
}
