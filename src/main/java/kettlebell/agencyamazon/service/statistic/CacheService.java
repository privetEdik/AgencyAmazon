package kettlebell.agencyamazon.service.statistic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheService {
    @CacheEvict(value = {"allStatisticsDate", "statisticsByDate", "statisticsBetweenDates",
            "allStatisticsAsin", "statisticsByAsin"}, allEntries = true)
    public void clearAllCaches() {
        log.info("All cache clear!");
    }
}
