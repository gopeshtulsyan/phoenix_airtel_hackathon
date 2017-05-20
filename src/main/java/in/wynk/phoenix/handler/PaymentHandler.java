package in.wynk.phoenix.handler;

import in.wynk.common.vas.dto.UpdateBalanceDetailsResponse;
import in.wynk.netty.common.RequestMapping;
import in.wynk.netty.common.ResponseType;
import in.wynk.netty.handler.IBaseRequestHandler;
import in.wynk.phoenix.dao.TransactionDao;
import in.wynk.phoenix.dto.PaymentRequest;
import in.wynk.phoenix.entity.Transaction;
import in.wynk.phoenix.service.UserSharedSecretService;
import in.wynk.phoenix.utils.TimeOTP;
import io.netty.handler.codec.http.HttpRequest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller("/wynk/v1/payment.*")
public class PaymentHandler implements IBaseRequestHandler {

    private static final Gson       GSON = new GsonBuilder().create();

    @Autowired
    private TimeOTP                 timeOTP;

    @Autowired
    private UserSharedSecretService userSharedSecretService;

    @Autowired
    private TransactionDao          transactionDao;

    @RequestMapping(value = "/wynk/v1/payment/makePayment", method = RequestMethod.POST, responseType = ResponseType.JSON)
    public UpdateBalanceDetailsResponse makePayment(HttpRequest httprequest, Map<String, List<String>> urlParameters, String requestPayload) {
        UpdateBalanceDetailsResponse updateBalanceDetailsResponse = new UpdateBalanceDetailsResponse();
        PaymentRequest request = GSON.fromJson(requestPayload, PaymentRequest.class);
        Transaction transaction = transactionDao.getTransactionDetailsByUserConsentId(request.getUserConsentId());
        if(null != transaction) {
            updateBalanceDetailsResponse.setCode("");
        }
        // using trancation id get transaction log for db - if transaction found
        // send failure response
        boolean validRequest = false;
        long currentTime = System.currentTimeMillis();
        String time = null;

        String key = null;
        key = userSharedSecretService.getUserSharedSecret(request.getMsisdn(), request.getDeviceId());
        if(key == null) {
            return null;
        }
        for(int i = 1; i < 4; i++) {
            if(validRequest == false) {
                time = String.valueOf(currentTime / (30 * i));
                String calculatedPin = null;

                calculatedPin = timeOTP.generateTOTP(key, time, "6", "HmacSHA512");
                int calculatedPinInt = Integer.parseInt(calculatedPin);
                if(calculatedPinInt == request.getPin()) {
                    validRequest = true;
                }
            }
        }
        if(validRequest == true) {
            // call mock API of Airtel for Money Tranfer
            // If transaction status is success then write in db
        }
        return null;

    }

    @RequestMapping(value = "/wynk/v1/payment/messagePayment", method = RequestMethod.POST, responseType = ResponseType.JSON)
    public UpdateBalanceDetailsResponse makePaymentThroughMessage(HttpRequest httprequest, Map<String, List<String>> urlParameters, String requestPayload) {
        return null;

    }

}
