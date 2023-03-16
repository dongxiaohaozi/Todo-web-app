package tw.cn.cap.gtb.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonUtil {
    public static ObjectMapper objectMapper = new ObjectMapper();


    public static String ObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e.getMessage());
        }
    }

    public static <T>T JsonToObject(InputStream requestBody, Class<T> type) {
        try {
            return objectMapper.readValue(requestBody, type);
        } catch (IOException e) {
            throw new DeserializationException(e.getMessage());
        }
    }
}
