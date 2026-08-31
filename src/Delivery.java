public class Delivery {

    private int matchId;
    private int inning;

    private String battingTeam;
    private String bowlingTeam;

    private int over;
    private int ball;

    private String batsman;
    private String nonStriker;
    private String bowler;

    private int batsmanRuns;
    private int extraRuns;
    private int totalRuns;

    private String playerDismissed;
    private String dismissalKind;

    public Delivery(
            int matchId,
            int inning,
            String battingTeam,
            String bowlingTeam,
            int over,
            int ball,
            String batsman,
            String nonStriker,
            String bowler,
            int batsmanRuns,
            int extraRuns,
            int totalRuns,
            String playerDismissed,
            String dismissalKind) {

        this.matchId = matchId;
        this.inning = inning;
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.over = over;
        this.ball = ball;
        this.batsman = batsman;
        this.nonStriker = nonStriker;
        this.bowler = bowler;
        this.batsmanRuns = batsmanRuns;
        this.extraRuns = extraRuns;
        this.totalRuns = totalRuns;
        this.playerDismissed = playerDismissed;
        this.dismissalKind = dismissalKind;
    }

    public int getMatchId() {
        return matchId;
    }

    public int getInning() {
        return inning;
    }

    public String getBattingTeam() {
        return battingTeam;
    }

    public String getBowlingTeam() {
        return bowlingTeam;
    }

    public int getOver() {
        return over;
    }

    public int getBall() {
        return ball;
    }

    public String getBatsman() {
        return batsman;
    }

    public String getNonStriker() {
        return nonStriker;
    }

    public String getBowler() {
        return bowler;
    }

    public int getBatsmanRuns() {
        return batsmanRuns;
    }

    public int getExtraRuns() {
        return extraRuns;
    }

    public int getTotalRuns() {
        return totalRuns;
    }

    public String getPlayerDismissed() {
        return playerDismissed;
    }

    public String getDismissalKind() {
        return dismissalKind;
    }
}