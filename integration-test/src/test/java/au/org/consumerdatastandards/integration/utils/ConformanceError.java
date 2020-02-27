package au.org.consumerdatastandards.integration.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class ConformanceError {

    private String dataJson;

    private Type errorType;

    private Field errorField;

    private String message;

    public ConformanceError dataJson(String dataJson) {
        this.dataJson = dataJson;
        return this;
    }

    public ConformanceError errorType(Type errorType) {
        this.errorType = errorType;
        return this;
    }

    public ConformanceError errorField(Field errorField) {
        this.errorField = errorField;
        return this;
    }

    public ConformanceError errorMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDescription() {
        switch (errorType) {
            case MISSING_VALUE:
                return String.format("Required field '%s' has null value in\n%s",
                        errorField.getName(), dataJson);
            case MISSING_PROPERTY:
                return String.format("Required field '%s' is missing in\n%s",
                        errorField.getName(), dataJson);
            case BROKEN_CONSTRAINT:
            case REDUNDANT_VALUE:
                return String.format("%s. See below:\n%s", message, dataJson);
            default:
                if (!StringUtils.isBlank(message)) return message;
                else return "Unknown error";
        }
    }

    public enum Type {

        MISSING_HEADER,
        INCORRECT_HEADER_VALUE,
        MISSING_PROPERTY,
        MISSING_VALUE,
        REDUNDANT_VALUE,
        BROKEN_CONSTRAINT,
        DATA_NOT_MATCHING_CRITERIA
    }
}
