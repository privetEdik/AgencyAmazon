package kettlebell.agencyamazon.models.statistics;

import lombok.Data;

@Data
public class SalesByDate {
    private OrderedProductSales orderedProductSales;
    private OrderedProductSalesB2B orderedProductSalesB2B;
    private double unitsOrdered;
    private double unitsOrderedB2B;
    private double totalOrderItems;
    private double totalOrderItemsB2B;
    private AverageSalesPerOrderItem averageSalesPerOrderItem;
    private AverageSalesPerOrderItemB2B averageSalesPerOrderItemB2B;
    private double averageUnitsPerOrderItem;
    private double averageUnitsPerOrderItemB2B;
    private AverageSellingPrice averageSellingPrice;
    private AverageSellingPriceB2B averageSellingPriceB2B;
    private double unitsRefunded;
    private double refundRate;
    private double claimsGranted;
    private ClaimsAmount claimsAmount;
    private ShippedProductSales shippedProductSales;
    private double unitsShipped;
    private double ordersShipped;
}
