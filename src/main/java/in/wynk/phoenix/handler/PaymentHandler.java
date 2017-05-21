package in.wynk.phoenix.handler;

import in.wynk.common.exception.WynkErrorType;
import in.wynk.common.exception.WynkRuntimeException;
import in.wynk.netty.common.RequestMapping;
import in.wynk.netty.common.ResponseType;
import in.wynk.netty.handler.IBasicAuthRequestHandler;
import in.wynk.phoenix.dto.MessageRequest;
import in.wynk.phoenix.dto.PaymentRequest;
import in.wynk.phoenix.dto.TransactionResponse;
import in.wynk.phoenix.service.UserSharedSecretService;
import in.wynk.phoenix.utils.CommonUtils;
import io.netty.handler.codec.http.HttpRequest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller("/wynk/v1/payment.*")
public class PaymentHandler implements IBasicAuthRequestHandler {

    private static final Gson       GSON   = new GsonBuilder().create();

    private static final Logger     logger = LoggerFactory.getLogger(PaymentHandler.class.getCanonicalName());

    @Autowired
    private UserSharedSecretService userSharedSecretService;

    @RequestMapping(value = "/wynk/v1/payment/makePayment", method = RequestMethod.POST, responseType = ResponseType.JSON)
    public TransactionResponse makePayment(HttpRequest httprequest, Map<String, List<String>> urlParameters, String requestPayload) {
        PaymentRequest request = GSON.fromJson(requestPayload, PaymentRequest.class);
        TransactionResponse transactionResponse = new TransactionResponse();
        try {
            transactionResponse = userSharedSecretService.makePayment(request);
        }
        catch (IllegalArgumentException e){
            transactionResponse.setErrorCode("1009");
            transactionResponse.setErrorMsg(e.getMessage());
        }
        catch (Exception e) {
            logger.error("Some error occured while making payment", e);
            throw new WynkRuntimeException(WynkErrorType.BSY999);
        }
        return transactionResponse;

    }

    @RequestMapping(value = "/wynk/v1/payment/messagePayment", method = RequestMethod.POST, responseType = ResponseType.JSON)
    public TransactionResponse makePaymentThroughMessage(HttpRequest httprequest, Map<String, List<String>> urlParameters, String requestPayload) {
        MessageRequest request = GSON.fromJson(requestPayload, MessageRequest.class);
        String message = request.getMessage();
        String messageRequest[] = message.split("|");
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUserConsentId(messageRequest[0]);
        paymentRequest.setMsisdn(CommonUtils.get10DigitMsisdn(messageRequest[1]));
        paymentRequest.setDeviceId(messageRequest[2]);
        paymentRequest.setPin(Integer.valueOf(messageRequest[3]));
        paymentRequest.setMerchantId(CommonUtils.get10DigitMsisdn(messageRequest[4]));
        paymentRequest.setPrice(messageRequest[4]);
        TransactionResponse transactionResponse = new TransactionResponse();
        try {
            transactionResponse = userSharedSecretService.makePayment(paymentRequest);
        }
        catch (Exception e) {
            logger.error("Some error occured while making payment", e);
            throw new WynkRuntimeException(WynkErrorType.BSY999);
        }
        return transactionResponse;

    }

}
