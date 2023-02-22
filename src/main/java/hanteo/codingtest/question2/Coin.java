package hanteo.codingtest.question2;

/**
 * 해당 코드에 대한 Test는 test.java.hantoe.codingtest.question2.CoinTest에 있습니다.
 */
public class Coin {

    public int findNumberOfCase(int sum, int[] coins) {

        int[] dp = new int[sum + 1];

        dp[0] = 1;

        for (int coin : coins) {
            for (int i = coin; i <= sum; i++) {
                dp[i] += dp[i - coin];
            }
        }

        return dp[sum];
    }
}
