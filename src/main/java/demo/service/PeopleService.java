package demo.service;

import demo.entity.People;
import demo.entity.Team;

import java.util.Optional;

public interface PeopleService {

    Optional<People> getGreatestTimeWorkingPeople();

    Optional<Team> getTeamByTeamName(String teamName);
}
