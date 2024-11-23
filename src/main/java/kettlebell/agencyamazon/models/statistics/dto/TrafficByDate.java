package kettlebell.agencyamazon.models.statistics.dto;

import lombok.Data;
@Data
public class TrafficByDate {
    private double browserPageViews;
    private double browserPageViewsB2B;
    private double mobileAppPageViews;
    private double mobileAppPageViewsB2B;
    private double pageViews;
    private double pageViewsB2B;
    private double browserSessions;
    private double browserSessionsB2B;
    private double mobileAppSessions;
    private double mobileAppSessionsB2B;
    private double sessions;
    private double sessionsB2B;
    private double buyBoxPercentage;
    private double buyBoxPercentageB2B;
    private double orderItemSessionPercentage;
    private double orderItemSessionPercentageB2B;
    private double unitSessionPercentage;
    private double unitSessionPercentageB2B;
    private double averageOfferCount;
    private double averageParentItems;
    private double feedbackReceived;
    private double negativeFeedbackReceived;
    private double receivedNegativeFeedbackRate;
}
