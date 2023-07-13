public class infiniteDecimalToFraction {
    // 求两个数的最大公约数
    static int getGreatestCommonDivisor(int a, int b) {
        if(b == 0) {
            return a;
        } else {
            return getGreatestCommonDivisor(b, a % b);
        }
    }

    /*
    // 将无限循环小数或无限不循环小数转换成分数
    static String convertToFraction(String decimal) {
        int integerPart = Integer.parseInt(decimal.split("\\.")[0]);  // 整数部分
        String decimalPart = decimal.split("\\.")[1];  // 小数部分

        int nonRepeatingPart = 0;  // 非循环部分
        int repeatingPart = 0;  // 循环部分
        int denominator = 0;  // 分母

        int numDigits = decimalPart.length();

        // 计算非循环部分，例如小数0.0123的非循环部分为123
        if (integerPart != 0){
            nonRepeatingPart = Integer.parseInt(decimalPart);
        } else {
            nonRepeatingPart = Integer.parseInt(decimalPart);
        }

        // 计算循环部分，例如小数0.0123的循环部分为0
        if (decimalPart.contains("(")){
            decimalPart = decimalPart.replace("(", "").replace(")", "");
            repeatingPart = Integer.parseInt(decimalPart);
        }

        // 计算分母
        if (numDigits > 0){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < numDigits; i++){
                sb.append("9");
            }
            denominator = Integer.parseInt(sb.toString());
        }

        // 得到总体分子和分母
        int numerator = nonRepeatingPart * denominator + repeatingPart - nonRepeatingPart;
        denominator *= Math.pow(10, numDigits) - 1;

        // 简化分子和分母
        int gcd = getGreatestCommonDivisor(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;

        // 将分数格式化
        return numerator + "/" + denominator;
    }
     */

    static void toFraction(String decimal) {
        String[] parts = decimal.split("\\.");
        int nonRecurringLength = parts[0].length() + parts[1].length();
        int recurringLength = parts[1].length();

        // 将无循环小数部分转换为分数
        int nonRecurring = Integer.parseInt(parts[0] + parts[1]);
        int nonRecurringDenominator = (int) Math.pow(10, parts[1].length());

        // 将循环小数部分转换为分数
        int recurring = Integer.parseInt(parts[1]);
        int recurringDenominator = (int) Math.pow(10, recurringLength) - 1;

        // 计算分母
        int denominator = nonRecurringDenominator * recurringDenominator;
        // 计算分子
        int numerator = (nonRecurring * recurringDenominator) + recurring;

        // 化简为最简分数
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;

        System.out.println("分数为: " + numerator + "/" + denominator);
    }

    // 求最大公约数
    static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
    // 调用示例
    public static void main(String[] args) {
        String decimal1 = "0.1111111";
        String decimal2 = "0.2222222";
        String decimal3 = "0.3333333";
        String decimal4 = "0.4444444";
        String decimal5 = "0.5555555";
        String decimal6 = "0.6666666";
        String decimal7 = "0.7777777";
        String decimal8 = "0.8888888";
        String decimal9 = "0.9999999";
        toFraction(decimal1);
        toFraction(decimal2);
        toFraction(decimal3);
        toFraction(decimal4);
        toFraction(decimal5);
        toFraction(decimal6);
        toFraction(decimal7);
        toFraction(decimal8);
        toFraction(decimal9);
    }
}
