package Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JSONConvert{
    public static <T> T deserializeFromFile(String jsonFile, Class<T> x){
        File file = new File(jsonFile);
        ObjectMapper mapper = new ObjectMapper();
        try {
            T result = mapper.readValue(file, x);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T deserializeFromString(String jsonString, Class<T> x){
        ObjectMapper mapper = new ObjectMapper();
        try {
            T result = mapper.readValue(jsonString, x);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> String serialize(T obj, Class<T> x){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(obj);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
