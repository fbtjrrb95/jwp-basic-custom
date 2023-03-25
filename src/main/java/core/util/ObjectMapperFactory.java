package core.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {

    private static ObjectMapper objectMapper;
    private ObjectMapperFactory() {}

    public static ObjectMapper getInstance() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }
}
