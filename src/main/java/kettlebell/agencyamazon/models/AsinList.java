package kettlebell.agencyamazon.models;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AsinList {
    @Size(min = 1, message = "ASIN list cannot be empty")
    private List<@Pattern(regexp = "^[A-Z0-9]{10}$", message = "Invalid ASIN format") String> asins;
}
