package web.spring.teamService.service;

import web.spring.teamService.entity.People;
import web.spring.teamService.entity.Team;

import java.util.Optional;

public interface PeopleService {

    Optional<People> getGreatestTimeWorkingPeople();

    Optional<Team> getTeamByTeamName(String teamName);
}
