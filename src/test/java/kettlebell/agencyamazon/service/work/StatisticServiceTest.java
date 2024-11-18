package kettlebell.agencyamazon.service.work;

import kettlebell.agencyamazon.service.statistic.find.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;

import java.time.Duration;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@SpringBootTest
@Slf4j
class StatisticServiceTest {

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void init(){
        clearStatisticsByDateCache();
    }

    @Test
    public void testCachingIsNotEmpty() {
        String startDate = "2024-02-17";
        String endDate = "2024-02-22";

        // first request
        statisticService.getStatisticsByDate(startDate, endDate);

        // The cache must contain data
        Object cachedValue = Objects.requireNonNull(Objects.requireNonNull(cacheManager.getCache("statisticsByDate")).get(startDate + "_" + endDate)).get();
        assertThat(cachedValue).isNotNull();
    }

    @Test
    public void testCachingTimeMeasurement() {
        String startDate = "2024-02-15";
        String endDate = "2024-02-26";

        long startTime = System.currentTimeMillis();
        statisticService.getStatisticsByDate(startDate, endDate);
        long endTime = System.currentTimeMillis();
        long timeBeforeSaveCache  = endTime-startTime;

        startTime = System.currentTimeMillis();
        statisticService.getStatisticsByDate(startDate, endDate);
        endTime = System.currentTimeMillis();
        long timeAfterSaveCache  = endTime-startTime;

        startTime = System.currentTimeMillis();
        statisticService.getStatisticsByDate(startDate, endDate);
        endTime = System.currentTimeMillis();
        long timeAfterSaveCacheTwo  = endTime-startTime;

        log.info("timeBeforeSaveCache: {} ms", timeBeforeSaveCache);
        log.info("timeAfterSaveCache: {}", timeAfterSaveCache);
        log.info("timeAfterSaveCacheTwo: {}", timeAfterSaveCacheTwo);

        assertTimeout(Duration.ofMillis(timeBeforeSaveCache / 2),
                ()->{ statisticService.getStatisticsByDate(startDate, endDate);});

        assert timeAfterSaveCache < timeBeforeSaveCache : "The cache is not working, the time has not decreased";

    }

    @CacheEvict(value = "statisticsByDate", allEntries = true)
    public void clearStatisticsByDateCache() {
    }
}