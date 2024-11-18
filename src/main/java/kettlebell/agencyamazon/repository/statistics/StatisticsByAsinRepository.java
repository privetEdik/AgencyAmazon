package kettlebell.agencyamazon.repository.statistics;

import kettlebell.agencyamazon.models.statistics.SalesAndTrafficByAsin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StatisticsByAsinRepository extends MongoRepository<SalesAndTrafficByAsin, String> {
    List<SalesAndTrafficByAsin> findAllByParentAsinIn(List<String> parentAsins);
}
