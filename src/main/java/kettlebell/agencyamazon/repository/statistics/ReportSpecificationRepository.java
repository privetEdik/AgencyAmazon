package kettlebell.agencyamazon.repository.statistics;

import kettlebell.agencyamazon.models.statistics.ReportSpecification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportSpecificationRepository extends MongoRepository<ReportSpecification,String> {

}
