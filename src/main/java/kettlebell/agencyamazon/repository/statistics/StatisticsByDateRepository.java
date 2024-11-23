package kettlebell.agencyamazon.repository.statistics;

import kettlebell.agencyamazon.models.statistics.dto.SalesAndTrafficByDate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StatisticsByDateRepository extends MongoRepository<SalesAndTrafficByDate,String> {
    List<SalesAndTrafficByDate> findByDateBetween(String startDate,String endDate);
    List<SalesAndTrafficByDate> findByDate(String date);
}
