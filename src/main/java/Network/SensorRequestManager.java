package Network;

import CarManagement.SensorData;
import Util.JSONConvert;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class SensorRequestManager implements IRequestManager {
    String lastRequest;
    String jsonPath;

    public SensorRequestManager(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    @Override
    public void receivedRequest(String requestMessage) {
        lastRequest = requestMessage.trim();
        File file = new File(jsonPath);
        try{
            Files.write(file.toPath(), lastRequest.getBytes());
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public String getResponse() {
        return lastRequest;
    }
}
