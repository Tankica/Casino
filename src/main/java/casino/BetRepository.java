package casino;

import casino.data.Bet;

import java.util.ArrayList;
import java.util.List;

public class BetRepository {

    private ArrayList<Bet> bets = new ArrayList<>();

    public List<Bet> findAll() {
        return bets;
    }

    public void save(Bet newBet) {
        bets.add(newBet);
    }

    public void update(Bet updatedBet) {
        for (Bet bet : bets) {
            if (bet.getPlayer().getName().equals(updatedBet.getPlayer().getName())) {
                bet.setTarget(updatedBet.getTarget());
                bet.setPrice(updatedBet.getPrice());
            }
        }
    }

    public Bet getByPlayersName(String name) {
        for (Bet bet : bets) {
            if (bet.getPlayer().getName().equals(name))
                return bet;
        }
        return null;
    }
}

class BetHandler {
    private final BetRepository betRepository;

    public BetHandler(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public void saveBet(Bet bet) {
        betRepository.save(bet);
    }

    public void updateBet(Bet updatedBet) {
        betRepository.update(updatedBet);
    }

    public Bet getByPlayersName(String name) {
        return betRepository.getByPlayersName(name);
    }

    public List<Bet> findAll() {
        return betRepository.findAll();
    }
}
