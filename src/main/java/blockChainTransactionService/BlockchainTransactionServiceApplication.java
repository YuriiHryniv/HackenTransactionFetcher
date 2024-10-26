package blockChainTransactionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableFeignClients
public class BlockchainTransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockchainTransactionServiceApplication.class, args);
	}

}
