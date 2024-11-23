package kettlebell.agencyamazon.service.statistic;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileProcessingService {
    private final FileTransactionService fileTransactionService;

    @PostConstruct
    public void init() {
        fileTransactionService.processFile();
    }

}
