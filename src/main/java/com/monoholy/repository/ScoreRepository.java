package com.monoholy.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.monoholy.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository interface for {@link Score} entities. Provides CRUD operations for Score entities.
 * Also provides custom queries.
 */
public interface ScoreRepository extends JpaRepository<Score, Long> {
  /**
   * Retrieves a leaderboard showing the sum of total scores acquired since their registration for
   * all users. The leaderboard is sorted in descending order of total scores.
   *
   * @return A List of strings, each representing a user's display name and their summed total
   *     score.
   */
  @Query(
      value =
          "SELECT ku.displayName, SUM(s.totalScore) AS total FROM Score s, User ku WHERE ku.userId = s.user.userId GROUP BY ku.userId ORDER BY total DESC")
  List<String> getLeaderBoardForAll();

  /**
   * Retrieves a leaderboard for the current month showing the sum of total scores for all users.
   * The leaderboard is sorted in descending order of total scores.
   *
   * @param month The current month as in integer format.
   * @return A List of strings, each representing a user's display name and their summed total score
   *     for the current month.
   */
  @Query(
      value =
          "SELECT ku.displayName, SUM(s.totalScore) AS total FROM Score s, User ku WHERE ku.userId = s.user.userId and MONTH(s.history) = ?1 GROUP BY ku.userId ORDER BY total DESC")
  List<String> getLeaderBoardForMonth(int month);

  /**
   * Retrieves a leaderboard for the current week. The leaderboard shows the sum of total scores for
   * all users during this period. The leaderboard is sorted in descending order of total scores of
   * this week.
   *
   * @param dateTime Current dateTime in LocalDateTime format.
   * @return A List of strings, each representing a user's display name and their summed total score
   *     for the current week.
   */
  @Query(
      value =
          "SELECT ku.displayName, SUM(s.totalScore) AS total FROM Score s, User ku WHERE ku.userId = s.user.userId and DATEDIFF(?1, s.history) < 7 GROUP BY ku.userId ORDER BY total DESC")
  List<String> getLeaderBoardForWeek(LocalDateTime dateTime);
}
