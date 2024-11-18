package kettlebell.agencyamazon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableCaching
public class AgencyAmazonApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgencyAmazonApplication.class, args);
    }

}
