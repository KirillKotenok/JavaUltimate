package web.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @PropertyName(value = "city")
    private String city;
    @PropertyName(value = "first_name")
    private String firstName;
    @PropertyName(value = "last_name")
    private String lastName;
    private String address;
}
