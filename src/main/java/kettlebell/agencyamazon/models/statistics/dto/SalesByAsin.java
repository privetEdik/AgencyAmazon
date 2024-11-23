package kettlebell.agencyamazon.models.statistics.dto;

import lombok.Data;
@Data
public class SalesByAsin {
    private double unitsOrdered;
    private double unitsOrderedB2B;
    private OrderedProductSales orderedProductSales;
    private OrderedProductSalesB2B orderedProductSalesB2B;
    private double totalOrderItems;
    private double totalOrderItemsB2B;
}
