package football.configs;

import org.apache.spark.SparkConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by king on 25.12.17.
 */
@Configuration
@Profile("dev")
public class DevConfig {

    @Bean
    public SparkConf sparkConf(){
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("football_dev");
        sparkConf.setMaster("local[*]");
        return sparkConf;
    }
}
