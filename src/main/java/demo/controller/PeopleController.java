package demo.controller;

import demo.entity.People;
import demo.entity.Team;
import demo.service.PeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class PeopleController {
    private transient final PeopleService peopleService;

    @GetMapping("/people/max")
    public ResponseEntity<People> getMaxWorkingPeople() {
        var people = peopleService.getGreatestTimeWorkingPeople();
        return ResponseEntity.ok(people.get());
    }

    @GetMapping("/teams/{name}")
    public ResponseEntity<Team> getTeamByTeamName(@PathVariable String name) {
        var team = peopleService.getTeamByTeamName(name);
        return ResponseEntity.ok(team.get());
    }
}
