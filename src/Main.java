import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String matchesFile = "data/matches.csv";
        String deliveriesFile = "data/deliveries.csv";

        System.out.println("======================================");
        System.out.println("       IPL SCORE ANALYSIS SYSTEM");
        System.out.println("======================================");

        System.out.println("\nLoading IPL data...");

        List<Match> matches =
                CSVReader.readMatches(matchesFile);

        List<Delivery> deliveries =
                CSVReader.readDeliveries(deliveriesFile);

        System.out.println("\nData Loading Complete!");

        System.out.println("------------------------------");

        System.out.println(
                "Matches loaded    : " + matches.size()
        );

        System.out.println(
                "Deliveries loaded : " + deliveries.size()
        );

        System.out.println("------------------------------");

        if (matches.isEmpty() || deliveries.isEmpty()) {

            System.out.println(
                    "ERROR: IPL data could not be loaded!"
            );

            return;
        }

        System.out.println(
                "\nIPL data successfully loaded!"
        );

        IPLAnalyzer analyzer =
                new IPLAnalyzer(matches, deliveries);

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n======================================");
            System.out.println("             IPL MENU");
            System.out.println("======================================");

            System.out.println("1. Total Runs By Team");
            System.out.println("2. Top Batsman");
            System.out.println("3. Most Sixes");
            System.out.println("4. Team Wins");
            System.out.println("5. Search Player");
            System.out.println("6. Show Match Winners");
            System.out.println("7. Highest Individual Score");
            System.out.println("8. Most Fours");
            System.out.println("9. Top 10 Batsmen");
            System.out.println("10. Top Bowlers");
            System.out.println("11. Season Analysis");
            System.out.println("12. Player Strike Rate");
            System.out.println("13. Bowler Economy Rate");
            System.out.println("14. Team Performance");
            System.out.println("15. IPL Dashboard");
            System.out.println("16. Exit");

            System.out.print("\nEnter your choice: ");

            int choice;

            try {

                choice = Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (Exception e) {

                System.out.println(
                        "Please enter a valid number!"
                );

                continue;
            }

            switch (choice) {

                case 1:
                    analyzer.totalRunsByTeam();
                    break;

                case 2:
                    analyzer.topBatsman();
                    break;

                case 3:
                    analyzer.mostSixes();
                    break;

                case 4:
                    analyzer.teamWins();
                    break;

                case 5:

                    System.out.print(
                            "Enter player name: "
                    );

                    String player =
                            scanner.nextLine();

                    analyzer.searchPlayer(player);

                    break;

                case 6:
                    analyzer.showMatchWinners();
                    break;

                case 7:
                    analyzer.highestIndividualScore();
                    break;

                case 8:
                    analyzer.mostFours();
                    break;

                case 9:
                    analyzer.top10Batsmen();
                    break;

                case 10:
                    analyzer.topBowlers();
                    break;

                case 11:

                    System.out.print("Enter season: ");

                    String season = scanner.nextLine();

                    analyzer.seasonAnalysis(season);

                    break;

                case 12:

                    System.out.print("Enter player name: ");

                    String batsmanName = scanner.nextLine();

                    analyzer.playerStrikeRate(batsmanName);

                    break;


                case 13:

                    System.out.print("Enter bowler name: ");

                    String bowlerName = scanner.nextLine();

                    analyzer.bowlerEconomyRate(bowlerName);

                    break;


                case 14:

                    System.out.print("Enter team name: ");

                    String teamName = scanner.nextLine();

                    analyzer.teamPerformance(teamName);

                    break;


                case 15:

                    analyzer.showDashboard();

                    break;

                case 16:

                    System.out.println(
                            "\nThank you for using IPL Score Analysis!"
                    );

                    scanner.close();

                    return;
                default:

                    System.out.println(
                            "Invalid choice! Please try again."
                    );
            }
        }
    }
}