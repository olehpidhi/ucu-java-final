package football.enrichers;

import football.utils.RegisterUDF;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.api.java.UDF1;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import football.primitives.Columns;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Properties;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

/**
 * Created by king on 25.12.17.
 */

@RegisterUDF(returnType = Columns.STRING)
@Component
@Log4j
public class CodeDescriptionEnricher implements FootballEnricher, UDF1<Integer,String>, Serializable {

    private static final String CODES_PROPERTIES_FILE = "codes.properties";
    private static final String CODE_COLUMN_NAME = "code";
    private static final String CODE_DESCRIPTION_COLUMN_NAME = "codeDescription";
    private Properties codeDescriptions;


    @PostConstruct
    @SneakyThrows
    private void loadPlayers() {
        this.codeDescriptions = PropertiesLoaderUtils.loadAllProperties(CODES_PROPERTIES_FILE);
    }

    @Override
    public String call(Integer code) throws Exception {
        return codeDescriptions.getProperty(code.toString());
    }

    @Override
    public Dataset enrich(Dataset dataset) {
        return dataset.withColumn(CODE_DESCRIPTION_COLUMN_NAME, callUDF(CodeDescriptionEnricher.class.getName(),
                (col(CODE_COLUMN_NAME))));
    }
}
