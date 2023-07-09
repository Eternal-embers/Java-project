public class FractionConverter {
    /**
     * 将小数转换为最简分数形式
     *
     * @param num            待转换的小数
     * @param maxDenominator 最大分母限制
     * @return 最简分数形式的字符串表示
     */
    public static String simplifyFraction(double num, int maxDenominator) {
        // 调用 approximateFraction 方法获取近似分数
        int[] fraction = approximateFraction(num, maxDenominator);
        int numerator = fraction[0];
        int denominator = fraction[1];

        return numerator + "/" + denominator;
    }

    /**
     * 根据给定的小数和最大分母限制，返回一个近似的分数表示
     *
     * @param num            待转换的小数
     * @param maxDenominator 最大分母限制
     * @return 近似分数的分子和分母组成的数组
     */
    public static int[] approximateFraction(double num, int maxDenominator) {
        double EPSILON = 1e-15; // 定义一个极小值作为误差限制
        int[] result = new int[2];

        double error = Math.abs(num); // 初始化误差
        int bestNumerator = 0; // 存储最佳近似的分子
        int bestDenominator = 1; // 存储最佳近似的分母

        for (int i = 1; i <= maxDenominator; i++) {
            int currentNumerator = (int) Math.round(num * i); // 计算当前分子
            double currentError = Math.abs(num - (double) currentNumerator / i); // 计算当前误差

            if (currentError < error - EPSILON) {
                // 如果当前误差小于之前的误差，更新最佳近似的分子、分母和误差
                error = currentError;
                bestNumerator = currentNumerator;
                bestDenominator = i;
            }
        }

        result[0] = bestNumerator;
        result[1] = bestDenominator;

        return result;
    }

    public static void main(String[] args) {
        double num = 0.591141396933560477;
        int maxDenominator = 1000;
        String result = simplifyFraction(num, maxDenominator);
        System.out.println(result);  // 输出：3/8
    }
}