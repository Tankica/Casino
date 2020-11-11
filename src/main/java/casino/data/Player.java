package casino.data;

public class Player {

    private String name;
    private double totalWin;
    private double totalBet;

    public Player(String name, double totalWin, double totalBet) {
        this.name = name;
        this.totalWin = totalWin;
        this.totalBet = totalBet;
    }

    public String getName() {
        return name;
    }

    public double getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(double totalWin) {
        this.totalWin = totalWin;
    }

    public double getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(double totalBet) {
        this.totalBet = totalBet;
    }
}
