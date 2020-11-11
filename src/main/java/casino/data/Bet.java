package casino.data;

public class Bet {

    private Player player;
    private String target;
    private double price;

    public Bet(Player player, String target, double price) {
        this.player = player;
        this.target = target;
        this.price = price;
    }

    public Player getPlayer() {
        return player;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
