package in.wynk.phoenix.handler;

import in.wynk.netty.handler.IApplicationAuthenticator;
import in.wynk.netty.handler.IBaseRequestHandler;
import in.wynk.netty.handler.IBasicAuthRequestHandler;
import in.wynk.phoenix.utils.ValidationService;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Authenticator implements IApplicationAuthenticator {

    private static Logger logger = LoggerFactory.getLogger(Authenticator.class);

    @Autowired
    private ValidationService validationService;

    @Override
    public boolean authenticate(IBaseRequestHandler handler, FullHttpRequest request) {
        if(handler instanceof IBasicAuthRequestHandler) {
            return validateBasicAuth(request);
        }
        return true;
    }

    private boolean validateBasicAuth(FullHttpRequest request) {
        String authHeader = request.headers().get("Authorization");
        if(authHeader != null) {
            StringTokenizer st = new StringTokenizer(authHeader);
            if(st.hasMoreTokens()) {
                String basic = st.nextToken();
                if(basic.equalsIgnoreCase("Basic")) {
                    try {
                        String credentials = new String(Base64.decodeBase64(st.nextToken()), "UTF-8");
                        logger.debug("Credentials: " + credentials);
                        int p = credentials.indexOf(":");
                        if(p != -1) {
                            String login = credentials.substring(0, p).trim();
                            String password = credentials.substring(p + 1).trim();
                            return validationService.validate(login, password);
                        }
                        else {
                            logger.error("Invalid authentication token");
                        }
                    }
                    catch (UnsupportedEncodingException e) {
                        logger.warn("Couldn't retrieve authentication", e);
                    }
                }
            }
        }
        return false;
    }

}
