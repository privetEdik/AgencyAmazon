package kettlebell.agencyamazon.service.statistic.update;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import kettlebell.agencyamazon.models.statistics.Root;
import kettlebell.agencyamazon.repository.statistics.ReportSpecificationRepository;
import kettlebell.agencyamazon.repository.statistics.StatisticsByAsinRepository;
import kettlebell.agencyamazon.repository.statistics.StatisticsByDateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class FileProcessingService {
    @Value("${file.path}")
    private String filePath;

    private final ReportSpecificationRepository reportSpecificationRepository;
    private final StatisticsByDateRepository statisticsByDateRepository;
    private final StatisticsByAsinRepository statisticsByAsinRepository;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        processFile();
    }

    @Scheduled(fixedRateString = "${data.refresh.interval}000")
    public void processFile() {
        try {

            Root root = objectMapper.readValue(new File(filePath), Root.class);

            reportSpecificationRepository.save(root.getReportSpecification());
            statisticsByDateRepository.saveAll(root.getSalesAndTrafficByDate());
            statisticsByAsinRepository.saveAll(root.getSalesAndTrafficByAsin());

            log.info("Upload successful {}", root.getReportSpecification().getReportType());

            clearStatisticsByDateCache();
            clearStatisticsByAsinCache();

        } catch (IOException e) {
            log.info("Error upload data from file, message: {}", e.getMessage());
        }

    }

    @CacheEvict(value = "statisticsByDate", allEntries = true)
    public void clearStatisticsByDateCache() {
    }

    @CacheEvict(value = "statisticsByAsin", allEntries = true)
    public void clearStatisticsByAsinCache() {
    }

}
