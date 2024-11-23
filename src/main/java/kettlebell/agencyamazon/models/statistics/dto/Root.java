package kettlebell.agencyamazon.models.statistics.dto;

import lombok.Data;
import java.util.ArrayList;
@Data
public class Root {
    private ReportSpecification reportSpecification;
    private ArrayList<SalesAndTrafficByDate> salesAndTrafficByDate;
    private ArrayList<SalesAndTrafficByAsin> salesAndTrafficByAsin;
}
