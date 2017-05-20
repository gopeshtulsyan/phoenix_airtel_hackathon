package in.wynk.phoenix.handler;

import in.wynk.netty.common.RequestMapping;
import in.wynk.netty.common.ResponseType;
import io.netty.handler.codec.http.HttpRequest;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("/wynk/v1/registration.*")
public class UserRegistrationHandler {

    @RequestMapping(value = "/wynk/v1/registration/login", method = RequestMethod.POST, responseType = ResponseType.JSON)
    public String makePayment(HttpRequest httprequest, Map<String, List<String>> urlParameters, String requestPayload) {
        String sharedSecret = null;
        return sharedSecret;
    }

}
