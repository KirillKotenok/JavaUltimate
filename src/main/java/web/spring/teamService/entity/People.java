package web.spring.teamService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class People {
    private String name;
    private String lastName;
    private Integer trainingDaysPerWeek;
    private Integer minutesPerTrainingDay;
}
