package Network;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class HttpConfig {
    private String rootFolder;
    private int port;

    public String getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public HttpConfig(String rootFolder, int port) {
        this.rootFolder = rootFolder;
        this.port = port;
    }
}

