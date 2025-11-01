package todo_mongo.todo_mongodb_app.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    public static ResponseEntity<Map<String, Object>> generateResponse(
            String status, String message, Object data, HttpStatus httpStatus) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data == null ? Collections.emptyList() : data);
        return new ResponseEntity<>(response, httpStatus);
    }
}
