package kettlebell.agencyamazon.service.statistic.find;

import kettlebell.agencyamazon.models.statistics.SalesAndTrafficByAsin;
import kettlebell.agencyamazon.models.statistics.SalesAndTrafficByDate;
import kettlebell.agencyamazon.repository.statistics.StatisticsByAsinRepository;
import kettlebell.agencyamazon.repository.statistics.StatisticsByDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final StatisticsByDateRepository statisticsByDateRepository;
    private final StatisticsByAsinRepository statisticsByAsinRepository;

    @Cacheable(value = "statisticsByDate", key = "#startDate + '_' + #endDate", unless = "#result.isEmpty()")
    public List<SalesAndTrafficByDate> getStatisticsByDate(String startDate, String endDate) {
        if (startDate != null & endDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateStart = LocalDate.parse(startDate, formatter).minusDays(1).toString();
            String dateEnd = LocalDate.parse(endDate, formatter).plusDays(1).toString();

            return statisticsByDateRepository.findByDateBetween(dateStart, dateEnd);
        } else if (startDate != null) {

            return statisticsByDateRepository.findByDate(startDate);
        } else if (endDate != null) {
            return statisticsByDateRepository.findByDate(endDate);
        } else {
            return statisticsByDateRepository.findAll();
        }
    }

    @Cacheable(value = "statisticsByAsin", key = "#asinList", unless = "#result.isEmpty()")
    public List<SalesAndTrafficByAsin> getStatisticsByAsin(List<String> asinList) {
        if (asinList == null || asinList.isEmpty()) {
            return statisticsByAsinRepository.findAll();
        }
        return statisticsByAsinRepository.findAllByParentAsinIn(asinList);
    }

}
