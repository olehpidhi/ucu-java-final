package football.validators;

import org.apache.spark.sql.Dataset;

/**
 * Created by king on 25.12.17.
 */
public interface FootballValidator {
    Dataset validate(Dataset datasetToValidate);
}
