package blockChainTransactionService.job;

import blockChainTransactionService.constants.ethereum.SystemSettingsConstants;
import blockChainTransactionService.dao.ethereum.ISystemSettDAO;
import blockChainTransactionService.model.ethereum.SystemSett;
import blockChainTransactionService.service.ethereum.IEthereumBlockFetcherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class EthereumFetcherScheduler {
    private final IEthereumBlockFetcherService ethereumTransactionService;
    private final ISystemSettDAO settDAO;

    @Autowired
    public EthereumFetcherScheduler(IEthereumBlockFetcherService transactionFetcher, ISystemSettDAO settDAO) {
        this.ethereumTransactionService = transactionFetcher;
        this.settDAO = settDAO;
    }

    @Scheduled(cron = "${ethereum.job.cron}")
    public void fetchTransactionsCron() {
        Optional<SystemSett> settingOpt = settDAO.findByName(SystemSettingsConstants.ETHEREUM_JOB_ENABLED);
        if (settingOpt.isPresent()) {
            boolean ethereumJobEnabled = Boolean.parseBoolean(settingOpt.get().getValue());
            if (ethereumJobEnabled) {
                ethereumTransactionService.fetchAndSaveLatestBlock();
                log.info("Ethereum job is enabled, fetching and saving latest block.");
            } else {
                log.info("Ethereum job is disabled.");
            }
        } else {
            log.warn("Ethereum job setting not found in the database.");
        }
    }
}

