package casino;

import casino.data.Player;
import casino.exceptions.InvalidInputInformationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MainTest {

    private static PlayerRepository playerRepository = new PlayerRepository();
    private static PlayerHandler playerHandler = new PlayerHandler(playerRepository);

    @BeforeAll
    static void initialSetUpAddedFromFile() {
        playerHandler.savePlayer(new Player("Tiki_Monkey", 1.0, 2.0));
        playerHandler.savePlayer(new Player("Barbara", 2.0, 1.0));
    }

    @Test
    void isValidNumber_correct() {
        Assertions.assertTrue(Casino.isValidNumber("8"));
    }

    @Test
    void isValidNumber_incorrect() {
        Assertions.assertFalse(Casino.isValidNumber("38"));
    }

    @Test
    void isEvenOrOdd_correct() {
        Assertions.assertTrue(Casino.isEvenOrOdd("EVEN"));
    }

    @Test
    void isEvenOrOdd_incorrect() {
        Assertions.assertFalse(Casino.isEvenOrOdd("RANDOM"));
    }

    @Test
    void validInputInformation_correct() {
        Assertions.assertTrue(Casino.validInputInformation(new String[]{"Tiki_Monkey", "EVEN", "2.0"}));
    }

    @Test
    void validInputInformation_incorrect() {
        Assertions.assertThrows(InvalidInputInformationException.class, () -> Casino.validInputInformation(new String[]{"RANDOM"}));
    }

}
