package football;

import org.apache.spark.sql.Dataset;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import football.workers.DataLoader;
import football.workers.DataProcessor;

/**
 * Created by king on 25.12.17.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Conf.class);

        DataProcessor processor = context.getBean(DataProcessor.class);
        DataLoader dataLoader = context.getBean(DataLoader.class);

        Dataset data = dataLoader.load();
        System.out.println(data.columns().length);
        data = processor.enrich(data);
        data = processor.validate(data);
    }
}
