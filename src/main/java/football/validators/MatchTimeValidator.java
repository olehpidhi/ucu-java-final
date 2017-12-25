package football.validators;

import lombok.extern.log4j.Log4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.api.java.UDF1;
import org.springframework.stereotype.Component;
import football.primitives.Columns;
import football.utils.RegisterUDF;

import java.io.Serializable;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

/**
 * Created by king on 25.12.17.
 */

@RegisterUDF(returnType = Columns.BOOLEAN)
@Component
@Log4j
public class MatchTimeValidator implements FootballValidator, UDF1<String,Boolean>, Serializable {

    private static final String EVENT_TIME_COLUMN_NAME = "eventTime";
    private static final String START_TIME_COLUMN_NAME = "startTime";

    @Override
    public Boolean call(String eventTime) throws Exception {
        if (true /*new FootballTime(eventTime).validate()*/) {
            return true;
        } else {
            log.warn("Data validation: match time value is invalid - " + eventTime);
            return false;
        }
    }

    @Override
    public Dataset validate(Dataset dataset) {
        return dataset.filter(callUDF(MatchTimeValidator.class.getName(), (col(START_TIME_COLUMN_NAME))))
                .filter(callUDF(MatchTimeValidator.class.getName(), (col(EVENT_TIME_COLUMN_NAME))));
    }

}
