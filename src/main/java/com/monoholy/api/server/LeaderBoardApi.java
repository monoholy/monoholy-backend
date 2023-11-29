package com.monoholy.api.server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.monoholy.api.request.leaderboard.LeaderboardRequest;
import com.monoholy.api.response.leaderboard.LeaderBoardResponse;
import com.monoholy.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling leaderboard requests. Provides endpoints to retrieve leaderboard
 * information, weekly, monthly and overall.
 */
@RestController
@RequestMapping(value = "/api/leaderboard")
public class LeaderBoardApi {

  @Autowired private ScoreRepository repository;

  /**
   * Endpoint to get the leaderboard based on the specified time interval. The time interval can be
   * monthly, weekly, or all-time, as specified in the request body.
   *
   * @param leaderboardRequest The request containing the time interval information, can be monthly,
   *     weekly, or all-time. Any string other than monthly and weekly will be treated as all-time.
   * @return A ResponseEntity containing a list of LeaderBoardResponses, each representing a user's
   *     leaderboard data.
   */
  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<LeaderBoardResponse>> getLeaderboard(
      @RequestBody LeaderboardRequest leaderboardRequest) {

    String timeInterval = leaderboardRequest.getTimeInterval();
    List<String> resultSet;
    List<LeaderBoardResponse> response = new ArrayList<>();

    System.out.println(LocalDateTime.now().getMonthValue());
    if (timeInterval.equals("monthly")) {
      resultSet = repository.getLeaderBoardForMonth(LocalDateTime.now().getMonthValue());
    } else if (timeInterval.equals("weekly")) {
      resultSet = repository.getLeaderBoardForWeek(LocalDateTime.now());
    } else {
      resultSet = repository.getLeaderBoardForAll();
    }

    for (String data : resultSet) {
      String first = data.split(",")[0], second = data.split(",")[1];
      response.add(new LeaderBoardResponse(first, 0, Integer.parseInt(second)));
    }

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
