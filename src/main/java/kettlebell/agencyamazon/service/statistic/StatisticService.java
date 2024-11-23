package kettlebell.agencyamazon.service.statistic;

import kettlebell.agencyamazon.models.statistics.dto.SalesAndTrafficByAsin;
import kettlebell.agencyamazon.models.statistics.dto.SalesAndTrafficByDate;
import kettlebell.agencyamazon.repository.statistics.StatisticsByAsinRepository;
import kettlebell.agencyamazon.repository.statistics.StatisticsByDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final StatisticsByDateRepository statisticsByDateRepository;
    private final StatisticsByAsinRepository statisticsByAsinRepository;

    @Cacheable(value = "allStatisticsDate")
    public List<SalesAndTrafficByDate> findAllStatisticsDate() {
        return statisticsByDateRepository.findAll();
    }

    @Cacheable(value = "statisticsByDate", key = "#date")
    public List<SalesAndTrafficByDate> findStatisticsByDate(String date) {
        return statisticsByDateRepository.findByDate(date);
    }

    @Cacheable(value = "statisticsBetweenDates", key = "#startDate.toString() + '-' + #endDate.toString()")
    public List<SalesAndTrafficByDate> findStatisticsBetweenDates(LocalDate startDate, LocalDate endDate) {
        String dateStart = startDate.minusDays(1).toString();
        String dateEnd = endDate.plusDays(1).toString();
        return statisticsByDateRepository.findByDateBetween(dateStart, dateEnd);
    }

    @Cacheable(value = "allStatisticsAsin")
    public Object findAllStatisticsAsin() {
        return statisticsByAsinRepository.findAll();
    }

    @Cacheable(value = "statisticsByAsin", keyGenerator = "customKeyGenerator")
    public List<SalesAndTrafficByAsin> getStatisticsByAsin(List<String> asinList) {
        return statisticsByAsinRepository.findAllByParentAsinIn(asinList);
    }
}
