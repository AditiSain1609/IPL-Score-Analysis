import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    // =========================
    // READ MATCHES CSV
    // =========================

    public static List<Match> readMatches(String filePath) {

        List<Match> matches = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // First line is header
            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (data.length < 15) {
                    continue;
                }

                try {

                    int id = parseInt(data[0]);
                    String season = clean(data[1]);
                    String city = clean(data[2]);
                    String date = clean(data[3]);
                    String team1 = clean(data[4]);
                    String team2 = clean(data[5]);
                    String tossWinner = clean(data[6]);
                    String tossDecision = clean(data[7]);
                    String result = clean(data[8]);

                    String winner = clean(data[10]);

                    int winByRuns = parseInt(data[11]);
                    int winByWickets = parseInt(data[12]);

                    String playerOfMatch = clean(data[13]);
                    String venue = clean(data[14]);

                    Match match = new Match(
                            id,
                            season,
                            city,
                            date,
                            team1,
                            team2,
                            tossWinner,
                            tossDecision,
                            winner,
                            winByRuns,
                            winByWickets,
                            playerOfMatch,
                            venue
                    );

                    matches.add(match);

                } catch (Exception e) {
                    System.out.println("Invalid match row skipped.");
                }
            }

        } catch (IOException e) {

            System.out.println("Error reading matches.csv");
            e.printStackTrace();
        }

        return matches;
    }


    // =========================
    // READ DELIVERIES CSV
    // =========================

    public static List<Delivery> readDeliveries(String filePath) {

        List<Delivery> deliveries = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // First line is header
            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (data.length < 21) {
                    continue;
                }

                try {

                    int matchId = parseInt(data[0]);
                    int inning = parseInt(data[1]);

                    String battingTeam = clean(data[2]);
                    String bowlingTeam = clean(data[3]);

                    int over = parseInt(data[4]);
                    int ball = parseInt(data[5]);

                    String batsman = clean(data[6]);
                    String nonStriker = clean(data[7]);
                    String bowler = clean(data[8]);

                    int batsmanRuns = parseInt(data[15]);
                    int extraRuns = parseInt(data[16]);
                    int totalRuns = parseInt(data[17]);

                    String playerDismissed = clean(data[18]);
                    String dismissalKind = clean(data[19]);

                    Delivery delivery = new Delivery(
                            matchId,
                            inning,
                            battingTeam,
                            bowlingTeam,
                            over,
                            ball,
                            batsman,
                            nonStriker,
                            bowler,
                            batsmanRuns,
                            extraRuns,
                            totalRuns,
                            playerDismissed,
                            dismissalKind
                    );

                    deliveries.add(delivery);

                } catch (Exception e) {
                    System.out.println("Invalid delivery row skipped.");
                }
            }

        } catch (IOException e) {

            System.out.println("Error reading deliveries.csv");
            e.printStackTrace();
        }

        return deliveries;
    }


    // =========================
    // HELPER METHODS
    // =========================

    private static int parseInt(String value) {

        try {

            if (value == null || value.trim().isEmpty()) {
                return 0;
            }

            return Integer.parseInt(clean(value));

        } catch (Exception e) {

            return 0;
        }
    }


    private static String clean(String value) {

        if (value == null) {
            return "";
        }

        return value.trim().replace("\"", "");
    }
}
