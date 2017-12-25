package football.workers;

import football.enrichers.FootballEnricher;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import football.validators.FootballValidator;

import java.util.List;

/**
 * Created by king on 25.12.17.
 */

@Service
public class DataProcessorImpl implements DataProcessor {

    @Autowired
    private DataLoader datasetLoader;

    @Autowired
    private List<FootballEnricher> enrichers;

    @Autowired
    private List<FootballValidator> validators;

    @Override
    public Dataset<Row> enrich(Dataset datasetToEnrich) {
        for (FootballEnricher enricher : enrichers) {
            datasetToEnrich = enricher.enrich(datasetToEnrich);
        }

        return datasetToEnrich;
    }

    @Override
    public Dataset validate(Dataset datasetToValidate) {
        for (FootballValidator validator : validators) {
            datasetToValidate = validator.validate(datasetToValidate);
        }

        return datasetToValidate;
    }
}
