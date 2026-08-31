public class Match {

    private int id;
    private String season;
    private String city;
    private String date;
    private String team1;
    private String team2;
    private String tossWinner;
    private String tossDecision;
    private String winner;
    private int winByRuns;
    private int winByWickets;
    private String playerOfMatch;
    private String venue;

    public Match(int id, String season, String city, String date,
                 String team1, String team2,
                 String tossWinner, String tossDecision,
                 String winner, int winByRuns,
                 int winByWickets, String playerOfMatch,
                 String venue) {

        this.id = id;
        this.season = season;
        this.city = city;
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
        this.tossWinner = tossWinner;
        this.tossDecision = tossDecision;
        this.winner = winner;
        this.winByRuns = winByRuns;
        this.winByWickets = winByWickets;
        this.playerOfMatch = playerOfMatch;
        this.venue = venue;
    }

    public int getId() {
        return id;
    }

    public String getSeason() {
        return season;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getTossWinner() {
        return tossWinner;
    }

    public String getTossDecision() {
        return tossDecision;
    }

    public String getWinner() {
        return winner;
    }

    public int getWinByRuns() {
        return winByRuns;
    }

    public int getWinByWickets() {
        return winByWickets;
    }

    public String getPlayerOfMatch() {
        return playerOfMatch;
    }

    public String getVenue() {
        return venue;
    }
}
