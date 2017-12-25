package football.primitives;

import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;

/**
 * Created by king on 25.12.17.
 */
public enum Columns {
    STRING("string", DataTypes.StringType), INTEGER("integer", DataTypes.IntegerType)
    , BOOLEAN("boolean", DataTypes.BooleanType);

    private String val;
    private DataType sparkTypeAnalog;
    private DataType javaType;

    Columns(String stringValue, DataType sparkTypeAnalog) {
        this.val = stringValue;
        this.sparkTypeAnalog = sparkTypeAnalog;
    }

    String getValue() {
        return this.val;
    }

    public DataType getSparkTypeAnalog() {
        return this.sparkTypeAnalog;
    }

    public static DataType convertToSparkType(Columns type) {
        for (Columns columnType: Columns.values()) {
            if (columnType.equals(type)) {
                return columnType.getSparkTypeAnalog();
            }
        }

        throw new IllegalArgumentException("Type: " + type + " is not supported");
    }

    public static Columns getColumnTypeByName(String type) {
        for (Columns columnType: Columns.values()) {
            if (columnType.getValue().equals(type)) {
                return columnType;
            }
        }

        throw new IllegalArgumentException("Type: " + type + "is not supported");
    }
}
