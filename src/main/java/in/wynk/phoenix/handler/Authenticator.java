package in.wynk.phoenix.handler;

import in.wynk.netty.handler.IApplicationAuthenticator;
import in.wynk.netty.handler.IBaseRequestHandler;
import io.netty.handler.codec.http.FullHttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Authenticator implements IApplicationAuthenticator {

    private static Logger logger = LoggerFactory.getLogger(Authenticator.class);

    @Override
    public boolean authenticate(IBaseRequestHandler handler, FullHttpRequest request) {
        // TODO Auto-generated method stub
        return true;
    }

}
