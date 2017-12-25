package football.workers;

import football.primitives.FootballEvent;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by king on 25.12.17.
 */
@Service
public class DataLoaderImpl implements DataLoader {

    @Value("${data.path}")
    private String dataPath;

    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private SQLContext sqlContext;

    @Override
    public Dataset load() {
        JavaRDD<String> rdd = sc.textFile(dataPath);
        JavaRDD<FootballEvent> rowJavaRDD = rdd.filter(line -> line.length() != 0)
                .map(line -> {
                    String[] stringsData = line.split(";");
                    for (int i = 0; i < stringsData.length; i++) {
                        stringsData[i] = stringsData[i].split("=")[1];
                    }

                    FootballEvent event = new FootballEvent();
                    event.setCode(Integer.parseInt(stringsData[0]));
                    event.setFrom(stringsData[1]);
                    event.setTo(stringsData[2]);
                    event.setEventTime(stringsData[3]);
                    event.setStadium(stringsData[4]);
                    event.setStartTime(stringsData[5]);

                    return event;
                });

        return sqlContext.createDataFrame(rowJavaRDD, FootballEvent.class);
    }
}
