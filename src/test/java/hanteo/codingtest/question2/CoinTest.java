package hanteo.codingtest.question2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CoinTest {

    Coin coin = new Coin();

    @Test
    @DisplayName("question2 테스트")
    void question2Test() {
        int sum = 10;
        int[] coins = new int[]{2, 5, 3, 6};

        int result = coin.findNumberOfCase(sum, coins);

        System.out.println(result);
    }

}
