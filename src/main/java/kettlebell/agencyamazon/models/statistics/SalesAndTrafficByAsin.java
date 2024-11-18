package kettlebell.agencyamazon.models.statistics;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "salesAndTrafficByAsin")
public class SalesAndTrafficByAsin {
    @Id
    private String parentAsin;
    private SalesByAsin salesByAsin;
    private TrafficByAsin trafficByAsin;
}
