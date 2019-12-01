package Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JSONConvert{
    public static <T> T deserialize(String jsonString, Class<T> x){
        File file = new File("HttpConfig.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            T result = mapper.readValue(file, x);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
