package casino;

import casino.data.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {

    private ArrayList<Player> players = new ArrayList<>();

    public List<Player> findAll() {
        return players;
    }

    public void save(Player newPlayer) {
        players.add(newPlayer);
    }
        public void update(Player updatedPlayer) {
        for(Player player:players){
            if(player.getName().equals(updatedPlayer.getName())){
                player.setTotalWin(updatedPlayer.getTotalWin());
                player.setTotalBet(updatedPlayer.getTotalBet());
            }
        }
    }
    public Player getByName(String name){
        for(Player player:players){
            if(player.getName().equals(name))
                return player;
        }
        return null;
    }
}

class PlayerHandler {
    private final PlayerRepository playerRepository;

    public PlayerHandler(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void savePlayer(Player player) {
            playerRepository.save(player);
    }

    public void updatePlayer(Player newPlayer){
        playerRepository.update(newPlayer);
    }
    public Player getByName(String name){
        return playerRepository.getByName(name);
    }
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

}
