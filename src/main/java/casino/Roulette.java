package casino;

import casino.common.RouletteNumbers;
import casino.common.Type;
import casino.data.Bet;
import casino.data.Player;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class Roulette {

    private boolean playing;
    Random random = new Random();
    PlayerHandler playerHandler;
    BetHandler betHandler;

    public void start(PlayerRepository repository, BetRepository betRepository) {
        playerHandler = new PlayerHandler(repository);
        betHandler = new BetHandler(betRepository);
        if (!playing) {
            playing = true;
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(startSpinning, 0, 10, TimeUnit.SECONDS);
        }
    }

    public void end() {
        System.exit(0);
    }

    Runnable startSpinning = new Runnable() {
        int number;

        public void run() {
            number = getRandomNumber();
            handelBetResults();
            printPlayers();
        }

        private int getRandomNumber() {
            return random.nextInt(RouletteNumbers.MAX_VALID_ROULETTE_NUMBER + 1);
        }

        private void handelBetResults() {
            printFirstColumn();

            betHandler.findAll().forEach(bet -> {
                if (isInteger(bet.getTarget()) && number == Integer.parseInt(bet.getTarget()))
                    betResult(bet, RouletteNumbers.PAYOFF_FOR_CORRECT_NUMBER);
                else if ((bet.getTarget().equals(Type.EVEN.toString()) && number % 2 == 0) || (bet.getTarget().equals(Type.ODD.toString()) && number % 2 == 1))
                    betResult(bet, RouletteNumbers.PAYOFF_FOR_CORRECT_ODD_EVEN);
                else
                    betResult(bet, RouletteNumbers.PAYOFF_FOR_INCORRECT_VALUE);
            });
        }

        private void printFirstColumn(){
            System.out.println("\nNumber: " + number);
            System.out.println("-------------------");
            System.out.println(String.format("%-20s %s %20s %20s",
                    "Players",
                    "Bet",
                    "OUTCOME",
                    "WINNINGS"));
        }

        private void betResult(Bet bet, double payoff) {
            printBet(bet, payoff);
            updatePlayersInfo(bet,payoff);
        }

        private void printBet(Bet bet, double payoff){
            System.out.println(String.format("%-20s %s %20s %20s",
                    bet.getPlayer().getName(),
                    bet.getTarget(),
                    payoff == 0 ? "LOSE" : "WIN",
                    bet.getPrice()*payoff));
        }

        private void updatePlayersInfo(Bet bet, double payoff){
            Player player = playerHandler.getByName(bet.getPlayer().getName());
            playerHandler.updatePlayer(
                    new Player(bet.getPlayer().getName(),
                            payoff != 0 ? player.getTotalWin()+bet.getPrice()*payoff : player.getTotalWin(),
                            player.getTotalBet()+bet.getPrice()));
        }

        boolean isInteger(String input) {
            try {
                Integer.parseInt(input);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        private void printPlayers() {
            System.out.println("\n");
            System.out.println(String.format("%-20s %s %20s",
                    "Players",
                    "Total Win",
                    "Total Bet"));
            playerHandler.findAll().forEach(player ->
                    System.out.println(String.format("%-20s %s %20s",
                            player.getName(),
                            player.getTotalWin(),
                            player.getTotalBet())));
        }
    };
}
