package in.wynk.phoenix.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import in.wynk.common.exception.WynkErrorType;
import in.wynk.common.exception.WynkRuntimeException;
import in.wynk.common.vas.dto.UpdateBalanceDetailsResponse;
import in.wynk.netty.common.RequestMapping;
import in.wynk.netty.common.ResponseType;
import in.wynk.netty.handler.IBaseRequestHandler;
import in.wynk.phoenix.dto.PaymentRequest;
import in.wynk.phoenix.dto.TransactionResponse;
import in.wynk.phoenix.service.UserSharedSecretService;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller("/wynk/v1/payment.*")
public class PaymentHandler implements IBaseRequestHandler {

    private static final Gson       GSON = new GsonBuilder().create();

    private static final Logger logger = LoggerFactory.getLogger(PaymentHandler.class.getCanonicalName());

    @Autowired
    private UserSharedSecretService userSharedSecretService;


    @RequestMapping(value = "/wynk/v1/payment/makePayment", method = RequestMethod.POST, responseType = ResponseType.JSON)
    public TransactionResponse makePayment(HttpRequest httprequest, Map<String, List<String>> urlParameters, String requestPayload) {
        UpdateBalanceDetailsResponse updateBalanceDetailsResponse = new UpdateBalanceDetailsResponse();
        PaymentRequest request = GSON.fromJson(requestPayload, PaymentRequest.class);
        TransactionResponse transactionResponse = null;
        try{
            transactionResponse = userSharedSecretService.makePayment(request);
        }catch (Exception e){
            logger.error("Some error occured while making payment", e);
            throw new WynkRuntimeException(WynkErrorType.BSY999);
        }
        return transactionResponse;

    }

    @RequestMapping(value = "/wynk/v1/payment/messagePayment", method = RequestMethod.POST, responseType = ResponseType.JSON)
    public UpdateBalanceDetailsResponse makePaymentThroughMessage(HttpRequest httprequest, Map<String, List<String>> urlParameters, String requestPayload) {
        return null;

    }

}
