package in.wynk.phoenix.service;

import in.wynk.common.dto.http.HttpRequest;
import in.wynk.common.utils.HttpUtils;
import in.wynk.phoenix.entity.Transaction;
import in.wynk.phoenix.utils.CommonUtils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class MessageSendingService {

    private static final Logger logger     = LoggerFactory.getLogger(MessageSendingService.class.getCanonicalName());

    private static String       messageUrl = "http://sms-1134353423.ap-south-1.elb.amazonaws.com/sms/send";

    @Autowired
    private HttpUtils           httpRequestUtil;

    private Gson                gson       = new GsonBuilder().create();

    public void sendMessage(Transaction transactionResponse, String price) {
        try {
            String userMsisdn = CommonUtils.get10DigitMsisdn(transactionResponse.getUserId());
            StringBuilder sb = new StringBuilder();
            String UserMessage = sb.append("An amount of Rs. ").append(price).append("has been transferred to ").append(transactionResponse.getMerchantId()).append(". Your updated balance is Rs.")
                    .append(transactionResponse.getUserUpdatedAmount()).append(". Keep using Airtel Payments Bank").toString();
            URI url = new URIBuilder(messageUrl).build();
            Map<String, Object> payload1 = new HashMap<String, Object>();
            payload1.put("message", UserMessage);
            payload1.put("msisdn", userMsisdn);
            payload1.put("source", "WYNK");
            payload1.put("priority", "HIGH");
            payload1.put("nineToNine", true);
            payload1.put("useDnd", true);
            HttpRequest httpRequest = new HttpRequest.Builder().url(url).payload(gson.toJson(payload1)).build();
            logger.info("Send Message request to User for transactionStatus : {} , request : {}", transactionResponse, httpRequest);
            String sendMessageResponse = httpRequestUtil.sendPostResquest(httpRequest, String.class);
            logger.info("Send Message Response to User for transactionStatus : {} and request : {}, response : {}", transactionResponse, httpRequest, sendMessageResponse);

        }
        catch (Exception e) {

        }
        try {
            String merchantMsisdn = CommonUtils.get10DigitMsisdn(transactionResponse.getMerchantId());
            StringBuilder sb = new StringBuilder();
            String merchantMessage = sb.append("An amount of Rs. ").append(price).append("has been received from ").append(transactionResponse.getUserId()).append(". Your updated balance is Rs.")
                    .append(transactionResponse.getMerchantUpdatedAmount()).append(". Keep using Airtel Payments Bank").toString();
            URI url = new URIBuilder(messageUrl).build();
            Map<String, Object> payload2 = new HashMap<String, Object>();
            payload2.put("message", merchantMessage);
            payload2.put("msisdn", merchantMsisdn);
            payload2.put("source", "WYNK");
            payload2.put("priority", "HIGH");
            payload2.put("nineToNine", true);
            payload2.put("useDnd", true);
            HttpRequest httpRequest = new HttpRequest.Builder().url(url).payload(gson.toJson(payload2)).build();
            logger.info("Send Message request to Merchant for transactionStatus : {} , request : {}", transactionResponse, httpRequest);
            String sendMessageResponse = httpRequestUtil.sendPostResquest(httpRequest, String.class);
            logger.info("Send Message Response to Merchant for transactionStatus : {} and request : {}, response : {}", transactionResponse, httpRequest, sendMessageResponse);

        }
        catch (Exception e) {

        }

    }

}
