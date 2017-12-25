package football.primitives;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by king on 25.12.17.
 */
@Data
public class FootballEvent {
    int code;
    String from;
    String to;
    String eventTime;
    String stadium;
    String startTime;
}
