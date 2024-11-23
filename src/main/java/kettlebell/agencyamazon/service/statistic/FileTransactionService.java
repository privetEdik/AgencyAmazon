package kettlebell.agencyamazon.service.statistic;

import com.fasterxml.jackson.databind.ObjectMapper;
import kettlebell.agencyamazon.models.statistics.dto.Root;
import kettlebell.agencyamazon.repository.statistics.ReportSpecificationRepository;
import kettlebell.agencyamazon.repository.statistics.StatisticsByAsinRepository;
import kettlebell.agencyamazon.repository.statistics.StatisticsByDateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class FileTransactionService {
    @Value("${file.path}")
    private String filePath;

    private final ReportSpecificationRepository reportSpecificationRepository;
    private final StatisticsByDateRepository statisticsByDateRepository;
    private final StatisticsByAsinRepository statisticsByAsinRepository;
    private final CacheService cacheService;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRateString = "${data.refresh.interval}000")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void processFile() {
        try {

            Root root = objectMapper.readValue(new File(filePath), Root.class);

            reportSpecificationRepository.save(root.getReportSpecification());
            statisticsByDateRepository.saveAll(root.getSalesAndTrafficByDate());
            statisticsByAsinRepository.saveAll(root.getSalesAndTrafficByAsin());

            log.info("Upload successful. {} - {}",
                    root.getReportSpecification().getDataStartTime(),
                    root.getReportSpecification().getDataEndTime());

            cacheService.clearAllCaches();

        } catch (IOException e) {
            log.info("Error upload data from file, message: {}", e.getMessage());
        }

    }
}
