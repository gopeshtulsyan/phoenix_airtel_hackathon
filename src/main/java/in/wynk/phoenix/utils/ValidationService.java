package in.wynk.phoenix.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import in.wynk.common.utils.EncryptUtils;

@Component
public class ValidationService {
	private static final Logger logger = LoggerFactory.getLogger(ValidationService.class);
	private static Map<String, String> credentials = new HashMap<String, String>();

	private static Map<String, String> passwordHashCache = new HashMap<String, String>();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock writeLock = lock.writeLock();
	@Autowired
	private ResourceLoader resourceLoader;

	@PostConstruct
	public void refreshCache() {
		BufferedReader reader = null;
		writeLock.lock();
		try {
			credentials.clear();
			Resource resource = resourceLoader.getResource("classpath:security.properties");
			reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

			String rule = null;
			while ((rule = reader.readLine()) != null) {
				String[] values = rule.split("=");
				credentials.put(values[0], values[1]);
			}
		} catch (IOException e) {
			logger.error("Error while reading security file ERROR: {}", e.getMessage());
			throw new RuntimeException("Error while reading security file");
		} finally {
			writeLock.unlock();
			IOUtils.closeQuietly(reader);
		}
	}

	public boolean validate(String username, String password) {
		if (passwordHashCache.containsKey(password)) {
			return StringUtils.equals(credentials.get(username), passwordHashCache.get(password));
		}else{
			String hashedPassword = EncryptUtils.generateMD5Hash(password);
			if(passwordHashCache.size() < 10){
				passwordHashCache.put(password, hashedPassword);
			}
			return StringUtils.equals(credentials.get(username), hashedPassword);
		}
	}

}
