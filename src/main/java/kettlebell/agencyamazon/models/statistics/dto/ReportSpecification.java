package kettlebell.agencyamazon.models.statistics.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
@Data
@Document(collection = "reportSpecification")
public class ReportSpecification {
    @Id
    private String reportType;
    private ReportOptions reportOptions;
    private String dataStartTime;
    private String dataEndTime;
    private ArrayList<String> marketplaceIds;
}
