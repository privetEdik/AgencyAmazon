package kettlebell.agencyamazon.controller.statistics;

import jakarta.validation.Valid;
import kettlebell.agencyamazon.service.statistic.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final StatisticService statisticService;

    @GetMapping("/all-date")
    public ResponseEntity<?> getAllStatisticsData() {
        return ResponseEntity.ok(statisticService.findAllStatisticsDate());
    }

    @GetMapping("/by-date")
    public ResponseEntity<?> getStatisticsByDate(@RequestParam String date) {

        try {
            log.info("get statistics by date: {} ", date);
            String dateStart = LocalDate.parse(date, FORMATTER).toString();
            return ResponseEntity.ok(statisticService.findStatisticsByDate(dateStart));
        } catch (Exception e) {
            log.info("error get statistics by date: {} - {}", date, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/between-dates")
    public ResponseEntity<?> getStatisticsBetweenDates(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        try {
            log.info("Find statistics  {} - {}", startDate, endDate);
            LocalDate dateStart = LocalDate.parse(startDate, FORMATTER);
            LocalDate dateEnd = LocalDate.parse(endDate, FORMATTER);
            return ResponseEntity.ok(statisticService.findStatisticsBetweenDates(dateStart, dateEnd));
        } catch (Exception e) {
            log.error(" error find statistics  {} - {} : {}", startDate, endDate, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/all-asin")
    public ResponseEntity<?> getAllStatisticAsin() {
        return ResponseEntity.ok(statisticService.findAllStatisticsAsin());
    }

    @GetMapping("/by-asin")
    public ResponseEntity<?> getStatisticByAsin(
            @Valid @RequestParam List<String> asinList) {
        log.info("Find statistics by ASIN {}", asinList.toString());
        return ResponseEntity.ok(statisticService.getStatisticsByAsin(asinList));
    }

}
