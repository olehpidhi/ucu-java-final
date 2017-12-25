package football.utils;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.spark.sql.Dataset;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by king on 25.12.17.
 */
@Aspect
@Component
@Log4j
@Profile("dev")
public class DatasetPrinter {

    @SneakyThrows
    @Around("execution(org.apache.spark.sql.Dataset football.workers.*.*(..))")
    public Dataset print(ProceedingJoinPoint pjp) {
        Dataset dataset = (Dataset) pjp.proceed();
        log.warn("METHOD: " + pjp.getSignature().toShortString());
        log.warn("DATASET SIZE: " + dataset.count());
        dataset.show();
        return dataset;
    }
}