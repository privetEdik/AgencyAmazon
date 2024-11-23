package kettlebell.agencyamazon.models.statistics.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection ="salesAndTrafficByDate" )
public class SalesAndTrafficByDate {
    @Id
    private String date;
    private SalesByDate salesByDate;
    private TrafficByDate trafficByDate;
}
