package football.enrichers;

import football.primitives.Columns;
import javax.annotation.PostConstruct;
import java.util.Properties;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import football.utils.RegisterUDF;
import static org.apache.spark.sql.functions.callUDF;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.api.java.UDF2;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import static org.apache.spark.sql.functions.col;
import org.springframework.stereotype.Component;

/**
 * Created by king on 25.12.17.
 */

@RegisterUDF(returnType = Columns.STRING, arguments = 2)
@Component
@Log4j
public class ActionsEnricher implements FootballEnricher, UDF2<String, String, String>, Serializable {

    private static final String TEAMS_PROPERTIES_FILE = "teams.properties";
    private static final String TO_COLUMN_NAME = "to";
    private static final String FROM_COLUMN_NAME = "from";
    private static final String TRACE_COLUMN_NAME = "Trace";

    private Map<String, String> players;


    @PostConstruct
    @SneakyThrows
    private void loadPlayers() {
        Properties teams = PropertiesLoaderUtils.loadAllProperties(TEAMS_PROPERTIES_FILE);
        Map<String, String> players = new HashMap<>();
        teams.stringPropertyNames().stream().forEach(country -> {
            Arrays.stream(teams.getProperty(country).split(","))
                    .forEach(player -> players.put(player, country));
        });
        this.players = players;
    }

    @Override
    public String call(String fromPlayer, String toPlayer) throws Exception {

        return String.format("%s ===> %s", players.get(fromPlayer), players.get(toPlayer));
    }

    @Override
    public Dataset enrich(Dataset dataset) {
        return dataset.withColumn(TRACE_COLUMN_NAME, callUDF(ActionsEnricher.class.getName(),
                col(FROM_COLUMN_NAME), col(TO_COLUMN_NAME)));
    }
}
