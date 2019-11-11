package Network;

public interface IRequestManager {
    void receivedRequest(String requestMessage);
    String getResponse();
}
