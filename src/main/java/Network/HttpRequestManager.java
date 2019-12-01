package Network;

import java.io.File;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestManager implements IRequestManager {
    private String requestText;
    private String requestResource;
    private String rootFolder;
    private boolean isGet = false;
    private String requestRegex = "(GET|POST)\\s*(\\/.*)\\s*HTTP";
    private String htmlTemplate =
            "HTTP/1.1 200 Ok\r\n" +
            "Content-type: text/html\r\n" +
            "Content-length: {length}\r\n\r\n" +
            "{html}";
    private Pattern httpPattern;

    public HttpRequestManager(String rootFolder){
        this.rootFolder = rootFolder;
        httpPattern = Pattern.compile(requestRegex);
    }

    @Override
    public void receivedRequest(String requestMessage) {
        requestText = requestMessage.trim();
        Matcher m = httpPattern.matcher(requestText);

        if(!m.lookingAt())
            return;

        m.reset();
        if(m.find()){
            isGet = m.group(1).equals("GET");
            requestResource = m.group(2).trim();
        }
    }

    @Override
    public String getResponse() {
        String response = "";
        String htmlString = "";
        if(isGet){

            if(requestResource.equals("/"))
                requestResource = requestResource + "index.html";
            try{
                File file = new File(rootFolder + requestResource.replace("/", "\\"));
                htmlString = new String(Files.readAllBytes(file.toPath()));
            }
            catch(Exception e){
                System.out.println(e);
            }

            if(htmlString.isEmpty())
                htmlString = "<html>Fehler</html>";
            response = htmlTemplate
                    .replace("{length}", String.valueOf(htmlString.length()))
                    .replace("{html}", htmlString);
        }

        return response;
    }
}

