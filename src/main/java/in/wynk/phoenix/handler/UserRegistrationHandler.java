package in.wynk.phoenix.handler;

import in.wynk.netty.common.RequestMapping;
import in.wynk.netty.common.ResponseType;
import in.wynk.netty.handler.IBaseRequestHandler;
import in.wynk.phoenix.dto.CreateUserRequest;
import in.wynk.phoenix.dto.CreateUserResponse;
import io.netty.handler.codec.http.HttpRequest;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller("/wynk/v1/registration.*")
public class UserRegistrationHandler implements IBaseRequestHandler {

    private static final Gson GSON = new GsonBuilder().create();

    @RequestMapping(value = "/wynk/v1/registration/login", method = RequestMethod.POST, responseType = ResponseType.JSON)
    public CreateUserResponse makePayment(HttpRequest httprequest, Map<String, List<String>> urlParameters, String requestPayload) {
        CreateUserRequest request = GSON.fromJson(requestPayload, CreateUserRequest.class);
        CreateUserResponse response = new CreateUserResponse();
        response.setDeviceId(request.getDeviceId());
        response.setMsisdn(request.getMsisdn());
        String sharedSecret = "aastha";
        response.setSharedSecret(sharedSecret);

        return response;
    }

}
