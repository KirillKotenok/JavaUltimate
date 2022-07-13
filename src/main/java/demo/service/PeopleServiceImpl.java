package demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.entity.People;
import demo.entity.Team;
import demo.entity.Teams;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.util.Comparator;
import java.util.Optional;

@Service
public class PeopleServiceImpl implements PeopleService {
    private transient final String fetchDataUrl = "https://salty-escarpment-89358.herokuapp.com/training/stats";

    @Override
    public Optional<People> getGreatestTimeWorkingPeople() {
        var restTemplate = new RestTemplate();
        var teams = restTemplate.getForObject(URI.create(fetchDataUrl), Teams.class);

        return teams.getTeams().stream()
                .flatMap(team -> team.getTeamMembers().stream())
                .max(Comparator.comparing(people -> people.getMinutesPerTrainingDay() * people.getTrainingDaysPerWeek()));
    }

    @Override
    @SneakyThrows
    public Optional<Team> getTeamByTeamName(String teamName) {
        var restTemplate = new RestTemplate();
        var mapper = new ObjectMapper();
        mapper.readTree(new URL(fetchDataUrl));
        var teams = restTemplate.getForObject(URI.create(fetchDataUrl), Teams.class);

        return teams.getTeams().stream()
                .filter(team -> team.getTeam().equals(teamName))
                .findFirst();
    }
}
