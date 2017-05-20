package in.wynk.phoenix.dto;


public class MessageRequest {

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageRequest [message=" + message + "]";
    }

}
