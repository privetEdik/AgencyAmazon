package kettlebell.agencyamazon.controller.statistics;

import kettlebell.agencyamazon.models.statistics.SalesAndTrafficByAsin;
import kettlebell.agencyamazon.models.statistics.SalesAndTrafficByDate;
import kettlebell.agencyamazon.service.statistic.find.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {

    private final StatisticService statisticService;

    @GetMapping("/data-on-date")
    public List<SalesAndTrafficByDate> getStatisticOnDate(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) {

        log.info("Find statistics by date between {} and {}", startDate, endDate);

        return statisticService.getStatisticsByDate(startDate, endDate);
    }

    @GetMapping("/data-on-asin")
    public List<SalesAndTrafficByAsin> getStatisticOnAsin(
            @RequestParam(required = false) List<String> asinList) {
        log.info("Find statistics by ASIN");
        return statisticService.getStatisticsByAsin(asinList);
    }

}
