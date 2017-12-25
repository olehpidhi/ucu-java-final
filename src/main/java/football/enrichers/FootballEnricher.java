package football.enrichers;

import org.apache.spark.sql.Dataset;

/**
 * Created by king on 25.12.17.
 */
public interface FootballEnricher {
    Dataset enrich(Dataset datasetToEnrich);
}
