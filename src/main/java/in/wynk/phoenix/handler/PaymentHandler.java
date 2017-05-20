package in.wynk.phoenix.handler;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import in.wynk.phoenix.dto.PaymentRequest;
import in.wynk.phoenix.utils.TimeOTP;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller("/wynk/v1/payment.*")
public class PaymentHandler {

	private static final Gson GSON = new GsonBuilder().create();

	@Autowired
	private TimeOTP timeOTP;

	@RequestMapping(value = "/wynk/v1/payment/makePayment", method = POST)
	public HttpResponse makePayment(String requestUri, String requestPayload,
			HttpRequest httpRequest) {
		Map<String, List<String>> urlParameters = getUrlParameters(requestUri);
		PaymentRequest request = GSON.fromJson(requestPayload,
				PaymentRequest.class);
		// using trancation id get transaction log for db - if transaction found
		// send failure response
		boolean validRequest = false;
		long currentTime = System.currentTimeMillis();
		String time = null;
		for (int i = 1; i < 4; i++) {
			if (validRequest == false) {
				time = String.valueOf(currentTime / (30 * i));
				String calculatedPin = null;
				String key = null;
				calculatedPin = timeOTP.generateTOTP(key, time, "6", "SHA512");
				int calculatedPinInt = Integer.parseInt(calculatedPin);
				if (calculatedPinInt == request.getPin()) {
					validRequest = false;
				}
			}
		}
		if (validRequest == true) {
			// call mock API of Airtel for Money Tranfer
			// If transaction status is success then write in db
		}
		return null;

	}

	public static Map<String, List<String>> getUrlParameters(String url) {
		try {
			Map<String, List<String>> params = new HashMap<String, List<String>>();
			String[] urlParts = url.split("\\?");
			if (urlParts.length > 1) {
				String query = urlParts[1];
				for (String param : query.split("&")) {
					String pair[] = param.split("=");
					String key = URLDecoder.decode(pair[0], "UTF-8");
					String value = "";
					if (pair.length > 1) {
						value = URLDecoder.decode(pair[1], "UTF-8");
					}
					List<String> values = params.get(key);
					if (values == null) {
						values = new ArrayList<String>();
						params.put(key, values);
					}
					values.add(value);
				}
			}
			return params;
		} catch (UnsupportedEncodingException e) {
			return null;
		}

	}

}
