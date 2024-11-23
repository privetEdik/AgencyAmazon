package kettlebell.agencyamazon.service.work;

import kettlebell.agencyamazon.service.statistic.CacheService;
import kettlebell.agencyamazon.service.statistic.StatisticService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class StatisticServiceTest {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private StatisticService statisticService;
    @Autowired
    private CacheService cacheService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void init() {
        cacheService.clearAllCaches();
    }

    @Test
    public void testCachingIsNotEmpty() {
        String startDate = "2024-02-17";
        String endDate = "2024-02-22";

        Cache cache = cacheManager.getCache("statisticsBetweenDates");

        assertNotNull(cache);


        assertNull(cache.get(startDate + "-" + endDate));

        // first request
        statisticService.findStatisticsBetweenDates(
                LocalDate.parse(startDate, FORMATTER),
                LocalDate.parse(endDate, FORMATTER));

        // The cache must contain data
        Object cachedValue = cache.get(startDate + "-" + endDate);
        assertNotNull(cachedValue);
        log.info(cachedValue.toString());
    }

    @Test
    public void testCachingTimeMeasurement() {

        LocalDate startDate = LocalDate.parse("2024-02-15", FORMATTER);
        LocalDate endDate = LocalDate.parse("2024-02-26", FORMATTER);

        long startTime = System.currentTimeMillis();
        statisticService.findStatisticsBetweenDates(startDate, endDate);
        long endTime = System.currentTimeMillis();
        long timeBeforeSaveCache = endTime - startTime;

        startTime = System.currentTimeMillis();
        statisticService.findStatisticsBetweenDates(startDate, endDate);
        endTime = System.currentTimeMillis();
        long timeAfterSaveCache = endTime - startTime;

        startTime = System.currentTimeMillis();
        statisticService.findStatisticsBetweenDates(startDate, endDate);
        endTime = System.currentTimeMillis();
        long timeAfterSaveCacheTwo = endTime - startTime;

        log.info("timeBeforeSaveCache: {} ms", timeBeforeSaveCache);
        log.info("timeAfterSaveCache: {}", timeAfterSaveCache);
        log.info("timeAfterSaveCacheTwo: {}", timeAfterSaveCacheTwo);

        assertTimeout(Duration.ofMillis(timeBeforeSaveCache / 2),
                () -> {
                    statisticService.findStatisticsBetweenDates(startDate, endDate);
                });

        assert timeAfterSaveCache < timeBeforeSaveCache : "The cache is not working, the time has not decreased";

    }

}