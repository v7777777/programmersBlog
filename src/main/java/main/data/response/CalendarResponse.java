package main.data.response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import lombok.Data;

@Data
public class CalendarResponse {

  private List<Integer> years;

  private HashMap<LocalDate, Integer> posts;


}
