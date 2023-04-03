//This holds the values of cost and startdate/time in one object so we can use @requestbody and get all the values.

package CSIT3214.GroupProject.DataAccessLayer;

import CSIT3214.GroupProject.Model.DateTimeRange;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
public class AcceptServiceRequestDTO {
    private Double cost;
    private DateTimeRange dateTimeRange;

    public LocalDate getDate() {
        if (dateTimeRange != null) {
            return dateTimeRange.getDate();
        }
        return null;
    }

    public LocalTime getStartTime() {
        if (dateTimeRange != null) {
            return dateTimeRange.getStartTime();
        }
        return null;
    }
}
