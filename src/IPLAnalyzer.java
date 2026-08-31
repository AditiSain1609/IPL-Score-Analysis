import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IPLAnalyzer {

    private List<Match> matches;
    private List<Delivery> deliveries;

    public IPLAnalyzer(List<Match> matches, List<Delivery> deliveries) {
        this.matches = matches;
        this.deliveries = deliveries;
    }

    // ==========================================
    // 1. TOTAL RUNS BY TEAM
    // ==========================================

    public void totalRunsByTeam() {

        Map<String, Integer> teamRuns = new HashMap<>();

        for (Delivery delivery : deliveries) {

            String team = delivery.getBattingTeam();

            int runs = delivery.getTotalRuns();

            teamRuns.put(
                    team,
                    teamRuns.getOrDefault(team, 0) + runs
            );
        }

        System.out.println("\n===== TOTAL RUNS BY TEAM =====");

        for (Map.Entry<String, Integer> entry : teamRuns.entrySet()) {

            System.out.println(
                    entry.getKey() + " : " + entry.getValue()
            );
        }
    }


    // ==========================================
    // 2. TOP BATSMAN
    // ==========================================

    public void topBatsman() {

        Map<String, Integer> batsmanRuns = new HashMap<>();

        for (Delivery delivery : deliveries) {

            String batsman = delivery.getBatsman();

            int runs = delivery.getBatsmanRuns();

            batsmanRuns.put(
                    batsman,
                    batsmanRuns.getOrDefault(batsman, 0) + runs
            );
        }

        String topPlayer = "";
        int highestRuns = 0;

        for (Map.Entry<String, Integer> entry : batsmanRuns.entrySet()) {

            if (entry.getValue() > highestRuns) {

                highestRuns = entry.getValue();
                topPlayer = entry.getKey();
            }
        }

        System.out.println("\n===== TOP BATSMAN =====");

        System.out.println(
                "Player : " + topPlayer
        );

        System.out.println(
                "Runs   : " + highestRuns
        );
    }


    // ==========================================
    // 3. MOST SIXES
    // ==========================================

    public void mostSixes() {

        Map<String, Integer> sixes = new HashMap<>();

        for (Delivery delivery : deliveries) {

            if (delivery.getBatsmanRuns() == 6) {

                String batsman = delivery.getBatsman();

                sixes.put(
                        batsman,
                        sixes.getOrDefault(batsman, 0) + 1
                );
            }
        }

        String player = "";
        int maximumSixes = 0;

        for (Map.Entry<String, Integer> entry : sixes.entrySet()) {

            if (entry.getValue() > maximumSixes) {

                maximumSixes = entry.getValue();
                player = entry.getKey();
            }
        }

        System.out.println("\n===== MOST SIXES =====");

        System.out.println(
                "Player : " + player
        );

        System.out.println(
                "Sixes  : " + maximumSixes
        );
    }


    // ==========================================
    // 4. TEAM WINS
    // ==========================================

    public void teamWins() {

        Map<String, Integer> wins = new HashMap<>();

        for (Match match : matches) {

            String winner = match.getWinner();

            if (winner == null || winner.isEmpty()) {
                continue;
            }

            wins.put(
                    winner,
                    wins.getOrDefault(winner, 0) + 1
            );
        }

        System.out.println("\n===== TEAM WINS =====");

        for (Map.Entry<String, Integer> entry : wins.entrySet()) {

            System.out.println(
                    entry.getKey() + " : " + entry.getValue()
            );
        }
    }


    // ==========================================
    // 5. PLAYER SEARCH
    // ==========================================

    // ==========================================
// 5. DETAILED PLAYER STATISTICS
// ==========================================

    public void searchPlayer(String playerName) {

        int totalRuns = 0;
        int totalBalls = 0;
        int totalFours = 0;
        int totalSixes = 0;

        boolean found = false;

        for (Delivery delivery : deliveries) {

            if (delivery.getBatsman()
                    .equalsIgnoreCase(playerName)) {

                found = true;

                // Total runs
                totalRuns += delivery.getBatsmanRuns();

                // Count ball faced
                // Wide balls are not counted as legal balls faced
                if (delivery.getExtraRuns() == 0) {
                    totalBalls++;
                }

                // Count fours
                if (delivery.getBatsmanRuns() == 4) {
                    totalFours++;
                }

                // Count sixes
                if (delivery.getBatsmanRuns() == 6) {
                    totalSixes++;
                }
            }
        }

        System.out.println("\n======================================");
        System.out.println("        PLAYER STATISTICS");
        System.out.println("======================================");

        if (!found) {

            System.out.println("Player not found!");
            return;
        }

        double strikeRate = 0;

        if (totalBalls > 0) {
            strikeRate = ((double) totalRuns / totalBalls) * 100;
        }

        System.out.println("Player       : " + playerName);
        System.out.println("Runs         : " + totalRuns);
        System.out.println("Balls Faced  : " + totalBalls);
        System.out.println("Fours        : " + totalFours);
        System.out.println("Sixes        : " + totalSixes);
        System.out.printf("Strike Rate  : %.2f%n", strikeRate);

        System.out.println("======================================");
    }


    // ==========================================
    // 6. MATCH WINNERS
    // ==========================================

    public void showMatchWinners() {

        System.out.println("\n===== MATCH WINNERS =====");

        for (Match match : matches) {

            System.out.println(
                    "Match " + match.getId()
                            + " : "
                            + match.getWinner()
            );
        }
    }
    // ==========================================
// 7. HIGHEST INDIVIDUAL SCORE
// ==========================================

    public void highestIndividualScore() {

        Map<String, Integer> playerRuns = new HashMap<>();

        for (Delivery delivery : deliveries) {

            String batsman = delivery.getBatsman();
            int runs = delivery.getBatsmanRuns();

            playerRuns.put(
                    batsman,
                    playerRuns.getOrDefault(batsman, 0) + runs
            );
        }

        String player = "";
        int highestScore = 0;

        for (Map.Entry<String, Integer> entry : playerRuns.entrySet()) {

            if (entry.getValue() > highestScore) {

                highestScore = entry.getValue();
                player = entry.getKey();
            }
        }

        System.out.println("\n===== HIGHEST INDIVIDUAL SCORE =====");

        System.out.println("Player : " + player);
        System.out.println("Runs   : " + highestScore);
    }


// ==========================================
// 8. MOST FOURS
// ==========================================

    public void mostFours() {

        Map<String, Integer> fours = new HashMap<>();

        for (Delivery delivery : deliveries) {

            if (delivery.getBatsmanRuns() == 4) {

                String batsman = delivery.getBatsman();

                fours.put(
                        batsman,
                        fours.getOrDefault(batsman, 0) + 1
                );
            }
        }

        String player = "";
        int maximumFours = 0;

        for (Map.Entry<String, Integer> entry : fours.entrySet()) {

            if (entry.getValue() > maximumFours) {

                maximumFours = entry.getValue();
                player = entry.getKey();
            }
        }

        System.out.println("\n===== MOST FOURS =====");

        System.out.println("Player : " + player);
        System.out.println("Fours  : " + maximumFours);
    }
    // ==========================================
// 9. TOP 10 BATSMEN
// ==========================================
 // TOP 10 BATSMEN - TABLE FORMAT
// ==========================================

    public void top10Batsmen() {

        Map<String, Integer> batsmanRuns = new HashMap<>();

        for (Delivery delivery : deliveries) {

            String batsman = delivery.getBatsman();
            int runs = delivery.getBatsmanRuns();

            batsmanRuns.put(
                    batsman,
                    batsmanRuns.getOrDefault(batsman, 0) + runs
            );
        }

        System.out.println("\n======================================================");
        System.out.println("                  TOP 10 BATSMEN");
        System.out.println("======================================================");
        System.out.printf("%-5s %-25s %10s%n",
                "Rank", "Player", "Runs");
        System.out.println("------------------------------------------------------");

        for (int i = 1; i <= 10; i++) {

            String bestPlayer = null;
            int bestRuns = -1;

            for (Map.Entry<String, Integer> entry
                    : batsmanRuns.entrySet()) {

                if (entry.getValue() > bestRuns) {
                    bestRuns = entry.getValue();
                    bestPlayer = entry.getKey();
                }
            }

            if (bestPlayer == null) {
                break;
            }

            System.out.printf("%-5d %-25s %10d%n",
                    i, bestPlayer, bestRuns);

            batsmanRuns.remove(bestPlayer);
        }

        System.out.println("======================================================");
    }

// ==========================================
// 10. TOP 10 BOWLERS - TABLE FORMAT
// ==========================================

    public void topBowlers() {

        Map<String, Integer> bowlerWickets = new HashMap<>();

        for (Delivery delivery : deliveries) {

            String dismissalKind =
                    delivery.getDismissalKind();

            if (dismissalKind == null
                    || dismissalKind.isEmpty()) {
                continue;
            }

            // These dismissals are not credited to bowler
            if (dismissalKind.equalsIgnoreCase("run out")
                    || dismissalKind.equalsIgnoreCase("retired hurt")
                    || dismissalKind.equalsIgnoreCase("obstructing the field")) {

                continue;
            }

            String bowler = delivery.getBowler();

            bowlerWickets.put(
                    bowler,
                    bowlerWickets.getOrDefault(bowler, 0) + 1
            );
        }

        System.out.println("\n======================================================");
        System.out.println("                  TOP 10 BOWLERS");
        System.out.println("======================================================");
        System.out.printf("%-5s %-25s %10s%n",
                "Rank", "Bowler", "Wickets");
        System.out.println("------------------------------------------------------");

        for (int i = 1; i <= 10; i++) {

            String bestBowler = null;
            int bestWickets = -1;

            for (Map.Entry<String, Integer> entry
                    : bowlerWickets.entrySet()) {

                if (entry.getValue() > bestWickets) {
                    bestWickets = entry.getValue();
                    bestBowler = entry.getKey();
                }
            }

            if (bestBowler == null) {
                break;
            }

            System.out.printf("%-5d %-25s %10d%n",
                    i, bestBowler, bestWickets);

            bowlerWickets.remove(bestBowler);
        }

        System.out.println("======================================================");
    }
    // ==========================================
// 11. SEASON ANALYSIS
// ==========================================

    public void seasonAnalysis(String season) {

        int totalMatches = 0;
        int totalRuns = 0;

        Map<String, Integer> teamWins = new HashMap<>();

        // ------------------------------------------
        // Match data se season ke matches aur wins
        // ------------------------------------------

        for (Match match : matches) {

            if (match.getSeason().equalsIgnoreCase(season)) {

                totalMatches++;

                String winner = match.getWinner();

                if (winner != null && !winner.isEmpty()) {

                    teamWins.put(
                            winner,
                            teamWins.getOrDefault(winner, 0) + 1
                    );
                }
            }
        }

        // ------------------------------------------
        // Delivery data se season ke runs
        // ------------------------------------------
        //
        // Delivery mein season directly nahi hai,
        // isliye pehle selected season ke match IDs
        // store karenge.
        // ------------------------------------------

        List<Integer> seasonMatchIds = new java.util.ArrayList<>();

        for (Match match : matches) {

            if (match.getSeason().equalsIgnoreCase(season)) {

                seasonMatchIds.add(match.getId());
            }
        }

        for (Delivery delivery : deliveries) {

            if (seasonMatchIds.contains(delivery.getMatchId())) {

                totalRuns += delivery.getTotalRuns();
            }
        }

        // ------------------------------------------
        // Season Winner find karo
        // ------------------------------------------

        String seasonWinner = "Not Available";
        int maximumWins = 0;

        for (Map.Entry<String, Integer> entry : teamWins.entrySet()) {

            if (entry.getValue() > maximumWins) {

                maximumWins = entry.getValue();
                seasonWinner = entry.getKey();
            }
        }

        // ------------------------------------------
        // Display result
        // ------------------------------------------

        System.out.println("\n======================================");
        System.out.println("          SEASON ANALYSIS");
        System.out.println("======================================");

        System.out.println("Season         : " + season);
        System.out.println("Total Matches  : " + totalMatches);
        System.out.println("Total Runs     : " + totalRuns);

        System.out.println("--------------------------------------");

        if (seasonWinner.equals("Not Available")) {

            System.out.println("Season Winner  : Not Available");

        } else {

            System.out.println("Season Winner  : " + seasonWinner);
            System.out.println("Total Wins     : " + maximumWins);
        }

        System.out.println("======================================");
    }
    // ==========================================
// 12. PLAYER STRIKE RATE
// ==========================================

    public void playerStrikeRate(String playerName) {

        int totalRuns = 0;
        int totalBalls = 0;

        boolean found = false;

        for (Delivery delivery : deliveries) {

            if (delivery.getBatsman()
                    .equalsIgnoreCase(playerName)) {

                found = true;

                totalRuns += delivery.getBatsmanRuns();

                // Wide ball batsman ki ball faced mein count nahi hoti
                if (delivery.getExtraRuns() == 0) {
                    totalBalls++;
                }
            }
        }

        System.out.println("\n======================================");
        System.out.println("          PLAYER STRIKE RATE");
        System.out.println("======================================");

        if (!found) {
            System.out.println("Player not found!");
            return;
        }

        double strikeRate = 0;

        if (totalBalls > 0) {
            strikeRate = ((double) totalRuns / totalBalls) * 100;
        }

        System.out.println("Player      : " + playerName);
        System.out.println("Runs        : " + totalRuns);
        System.out.println("Balls Faced : " + totalBalls);

        System.out.printf(
                "Strike Rate : %.2f%n",
                strikeRate
        );

        System.out.println("======================================");
    }
    // ==========================================
// 13. BOWLER ECONOMY RATE
// ==========================================

    public void bowlerEconomyRate(String bowlerName) {

        int totalRunsConceded = 0;
        int totalBallsBowled = 0;

        boolean found = false;

        for (Delivery delivery : deliveries) {

            if (delivery.getBowler()
                    .equalsIgnoreCase(bowlerName)) {

                found = true;

                // Bowler ke against batsman ke runs
                totalRunsConceded += delivery.getBatsmanRuns();

                // Extras bhi bowler ke runs mein add honge
                // except byes and leg byes
                totalRunsConceded += delivery.getExtraRuns();

                // 6 legal balls = 1 over
                totalBallsBowled++;
            }
        }

        System.out.println("\n======================================");
        System.out.println("          BOWLER ECONOMY RATE");
        System.out.println("======================================");

        if (!found) {
            System.out.println("Bowler not found!");
            return;
        }

        double overs = totalBallsBowled / 6.0;

        double economyRate = 0;

        if (overs > 0) {
            economyRate = totalRunsConceded / overs;
        }

        System.out.println("Bowler          : " + bowlerName);
        System.out.println("Runs Conceded   : " + totalRunsConceded);
        System.out.println("Balls Bowled    : " + totalBallsBowled);

        System.out.printf(
                "Economy Rate    : %.2f%n",
                economyRate
        );

        System.out.println("======================================");
    }
    // ==========================================
// 14. TEAM PERFORMANCE
// ==========================================

    public void teamPerformance(String teamName) {

        int totalMatches = 0;
        int wins = 0;

        for (Match match : matches) {

            boolean played =
                    match.getTeam1().equalsIgnoreCase(teamName)
                            || match.getTeam2().equalsIgnoreCase(teamName);

            if (played) {

                totalMatches++;

                if (match.getWinner() != null
                        && match.getWinner().equalsIgnoreCase(teamName)) {

                    wins++;
                }
            }
        }

        System.out.println("\n======================================");
        System.out.println("          TEAM PERFORMANCE");
        System.out.println("======================================");

        if (totalMatches == 0) {

            System.out.println("Team not found!");

            System.out.println("======================================");

            return;
        }

        int losses = totalMatches - wins;

        double winPercentage =
                ((double) wins / totalMatches) * 100;

        System.out.println("Team           : " + teamName);
        System.out.println("Matches        : " + totalMatches);
        System.out.println("Wins           : " + wins);
        System.out.println("Losses         : " + losses);

        System.out.printf(
                "Win Percentage : %.2f%%%n",
                winPercentage
        );

        System.out.println("======================================");
    }
    // ==========================================
// 15. IPL DASHBOARD
// ==========================================

    public void showDashboard() {

        // ---------- TOP BATSMAN ----------
        Map<String, Integer> batsmanRuns = new HashMap<>();

        for (Delivery delivery : deliveries) {

            String batsman = delivery.getBatsman();
            int runs = delivery.getBatsmanRuns();

            batsmanRuns.put(
                    batsman,
                    batsmanRuns.getOrDefault(batsman, 0) + runs
            );
        }

        String topBatsman = "";
        int highestRuns = 0;

        for (Map.Entry<String, Integer> entry : batsmanRuns.entrySet()) {

            if (entry.getValue() > highestRuns) {

                highestRuns = entry.getValue();
                topBatsman = entry.getKey();
            }
        }


        // ---------- MOST SIXES ----------
        Map<String, Integer> sixes = new HashMap<>();

        for (Delivery delivery : deliveries) {

            if (delivery.getBatsmanRuns() == 6) {

                String batsman = delivery.getBatsman();

                sixes.put(
                        batsman,
                        sixes.getOrDefault(batsman, 0) + 1
                );
            }
        }

        String mostSixPlayer = "";
        int maximumSixes = 0;

        for (Map.Entry<String, Integer> entry : sixes.entrySet()) {

            if (entry.getValue() > maximumSixes) {

                maximumSixes = entry.getValue();
                mostSixPlayer = entry.getKey();
            }
        }


        // ---------- MOST FOURS ----------
        Map<String, Integer> fours = new HashMap<>();

        for (Delivery delivery : deliveries) {

            if (delivery.getBatsmanRuns() == 4) {

                String batsman = delivery.getBatsman();

                fours.put(
                        batsman,
                        fours.getOrDefault(batsman, 0) + 1
                );
            }
        }

        String mostFourPlayer = "";
        int maximumFours = 0;

        for (Map.Entry<String, Integer> entry : fours.entrySet()) {

            if (entry.getValue() > maximumFours) {

                maximumFours = entry.getValue();
                mostFourPlayer = entry.getKey();
            }
        }


        // ---------- TOTAL MATCHES ----------
        int totalMatches = matches.size();


        // ---------- TOTAL RUNS ----------
        int totalRuns = 0;

        for (Delivery delivery : deliveries) {

            totalRuns += delivery.getTotalRuns();
        }


        // ---------- DISPLAY DASHBOARD ----------

        System.out.println("\n");
        System.out.println("==============================================");
        System.out.println("              IPL DASHBOARD");
        System.out.println("==============================================");

        System.out.println(
                "Total Matches       : " + totalMatches
        );

        System.out.println(
                "Total Runs          : " + totalRuns
        );

        System.out.println("----------------------------------------------");

        System.out.println(
                "Top Batsman         : " + topBatsman
        );

        System.out.println(
                "Top Batsman Runs    : " + highestRuns
        );

        System.out.println("----------------------------------------------");

        System.out.println(
                "Most Sixes          : " + mostSixPlayer
        );

        System.out.println(
                "Sixes               : " + maximumSixes
        );

        System.out.println("----------------------------------------------");

        System.out.println(
                "Most Fours          : " + mostFourPlayer
        );

        System.out.println(
                "Fours               : " + maximumFours
        );

        System.out.println("==============================================");
    }
}
