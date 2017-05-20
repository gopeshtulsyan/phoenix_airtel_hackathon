package in.wynk.phoenix.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import in.wynk.netty.common.RequestMapping;
import in.wynk.netty.common.ResponseType;
import in.wynk.netty.handler.IBaseRequestHandler;
import in.wynk.phoenix.dao.UserDao;
import in.wynk.phoenix.dto.CreateUserRequest;
import in.wynk.phoenix.dto.CreateUserResponse;
import in.wynk.phoenix.entity.User;
import in.wynk.phoenix.service.UserSharedSecretService;
import io.netty.handler.codec.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/wynk/v1/registration.*")
public class UserRegistrationHandler implements IBaseRequestHandler {

    private static final Gson       GSON = new GsonBuilder().create();

    @Autowired
    private UserDao                 userDao;

    @Autowired
    private UserSharedSecretService userSharedSecretService;

    @RequestMapping(value = "/wynk/v1/registration/login", method = RequestMethod.POST, responseType = ResponseType.JSON)
    public CreateUserResponse makePayment(HttpRequest httprequest, Map<String, List<String>> urlParameters, String requestPayload) throws SignatureException {
        CreateUserRequest request = GSON.fromJson(requestPayload, CreateUserRequest.class);
        CreateUserResponse response = new CreateUserResponse();
        User user = null;
        try {
             user = userSharedSecretService.getSharedSecret(request.getDeviceId(),request.getMsisdn());
        }
        catch (IllegalStateException e) {
            user = new User();
            user.setMsisdn(request.getMsisdn());
            user.setAmount(Float.parseFloat("100"));
            Map<String, String> sharedSecrets = new HashMap<String, String>();
            String sharedSecret = userSharedSecretService.createUserSharedSecret(request.getMsisdn(), request.getDeviceId());
            sharedSecrets.put(request.getDeviceId(), sharedSecret);
            user.setSharedSecret(sharedSecrets);
            userDao.saveUser(user);
        }catch (Exception e){
            response.setErrorCode("1005");
            response.setErrorMsg(e.getMessage());
        }
        finally {
            response.setDeviceId(request.getDeviceId());
            response.setMsisdn(request.getMsisdn());
            response.setSharedSecret(user.getSharedSecrets().get(request.getDeviceId()));
            response.setAmount(user.getAmount());
        }
        return response;
    }

}
