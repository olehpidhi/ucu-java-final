package football.workers;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * Created by king on 25.12.17.
 */
public interface DataProcessor {
    Dataset<Row> enrich(Dataset datasetToEnrich);
    Dataset validate(Dataset datasetToValidate);
}
