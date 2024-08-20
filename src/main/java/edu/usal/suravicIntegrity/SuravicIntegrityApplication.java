package edu.usal.suravicIntegrity;

import edu.usal.suravicIntegrity.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SuravicIntegrityApplication {

	public static void main(String[] args) {
		if (System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME) == null)
			System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev");

		SpringApplication.run(SuravicIntegrityApplication.class, args);
	}

}
