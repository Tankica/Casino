package casino;

import casino.common.RouletteNumbers;
import casino.common.Type;
import casino.data.Bet;
import casino.data.Player;
import casino.exceptions.InvalidInputInformationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Casino {

    private static PlayerRepository playerRepository = new PlayerRepository();
    private static PlayerHandler playerHandler = new PlayerHandler(playerRepository);

    private static BetRepository betRepository = new BetRepository();
    private static BetHandler betHandler = new BetHandler(betRepository);

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Roulette roulette = new Roulette();

        readPlayersFromFile();

        System.out.println("To start the game please enter player's name, target(number/EVEN/ODD) and how much money is he betting");
        String line;
        while (true) {
            if (!(line = input.nextLine()).equals("end")) {
                storeBettingInfoForPlayer(line);
                roulette.start(playerRepository, betRepository);

            } else {
                roulette.end();
            }
        }
    }

    static void storeBettingInfoForPlayer(String line) {
        String[] lineToArray = line.split(" ");
        if (validInputInformation(lineToArray)) {
            Bet bet = new Bet(playerHandler.getByName(lineToArray[0]), lineToArray[1], Double.parseDouble(lineToArray[2]));
            if ((betHandler.getByPlayersName(lineToArray[0]) == null)) {
                betHandler.saveBet(bet);
            } else {
                betHandler.updateBet(bet);
            }
        }
    }

    static boolean validInputInformation(String[] lineToArray) {
        if (playerHandler.getByName(lineToArray[0]) != null
                && (isEvenOrOdd(lineToArray[1]) || isValidNumber(lineToArray[1]))
                && Double.parseDouble(lineToArray[2]) > 0) {
            return true;
        } else {
            throw new InvalidInputInformationException("Invalid bet information");
        }
    }

    static boolean isEvenOrOdd(String string) {
        final Type type = Type.getName(string);
        return type != null;
    }

    static boolean isValidNumber(String numberIntoString) {
        try {
            final int number = Integer.parseInt(numberIntoString);
            return (number > RouletteNumbers.MIN_VALID_ROULETTE_NUMBER) && (number <= RouletteNumbers.MAX_VALID_ROULETTE_NUMBER);
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    static void readPlayersFromFile() {
        try {
            File file = new File("data.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arrOfStrings = data.split(",");
                playerHandler.savePlayer(new Player(arrOfStrings[0], Double.parseDouble(arrOfStrings[1]), Double.parseDouble(arrOfStrings[2])));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}