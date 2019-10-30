package imaniprima.meitrack.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResponseDuplicateKeyException extends RuntimeException  {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResponseDuplicateKeyException() { super(); }

    public ResponseDuplicateKeyException(String message) {
        super(message);
    }

    public ResponseDuplicateKeyException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s%s : '%s' sudah ada", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}

