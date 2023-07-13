import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FractionMatch {
    int limit;//分母的最大值
    ArrayList<Fraction> list;

    /* 构造函数 */
    public FractionMatch(int limit){
        list = new ArrayList<>();
        this.limit = limit;
        createFraction();
        fractionSort();
        System.out.println("FractionMatch is loaded.");
    }

    String match(double num){
        /* 如果是有限小数 */
        if (Double.isFinite(num) && isFinite(num))
            return finiteDecimalMatchFraction(num);
            /* 如果是无限小数 */
        else
            return infiniteDecimalMatchFraction(num);
    }

    /* 创建分母小于limit且分式值为无限小数的分式 */
    void createFraction(){
        for(int i = 3;i < limit;i++){
            for(int j = 1;j < i;j++){
                /* 如果分式的值为无限小数且分子分母的最大公约数为1 */
                if(!isFinite(j,i) && findGCD(i,j) == 1)
                    list.add(new Fraction(j,i));
            }
        }
    }

    /* 是否为有限小数 */
    boolean isFinite(int numerator, int denominator) {
        BigDecimal dividend = new BigDecimal(numerator);
        BigDecimal divisor = new BigDecimal(denominator);
        BigDecimal quotient = dividend.divide(divisor, 20, BigDecimal.ROUND_DOWN);
        String decimalPart = quotient.toString().split("\\.")[1];
        String sub = decimalPart.substring(9,decimalPart.length() - 1);//从小数部分后面截取一段
        boolean isAllZero = sub.matches("^[0]+$");//判断截取部分是否全部是0
        boolean isFinite = isAllZero;
        return isFinite;
    }

    boolean isFinite(double num) {
        int precision = 20;//精确位数
        // 创建DecimalFormat对象，指定精确位数
        DecimalFormat df = new DecimalFormat("#." + "0".repeat(precision));
        String decimal = df.format(num).substring(10,20);//获取后面30位
        boolean isAllZero = decimal.matches("^[0]+$");//判断截取部分是否全部是0
        boolean isFinite = isAllZero;
        return isFinite;
    }

    /* 将list中的分式按照分式的值从小到大排序 */
    void fractionSort(){
        //选择排序
        int i,j,k;
        int length = list.size();
        for(i = 0;i < length;i++){
            k = i;
            for(j = i + 1;j < length;j++){
                if(list.get(k).value > list.get(j).value)
                    k = j;
            }
            if(k != i){
                Fraction fraction1 = list.get(i);
                Fraction fraction2 = list.get(k);
                list.set(i,fraction2);
                list.set(k,fraction1);
            }
        }
    }

    /* 匹配与无限小数的最接近的分式 */
    String infiniteDecimalMatchFraction(double num){
        System.out.println(num);
        /* 在排序好的list中二分查找最匹配的分式 */
        double error = 1e-30;
        int left = 0,right = list.size() - 1;
        while(left <= right){
            int mid = (left + right) / 2;
            if(list.get(mid).value - num > error)
                right = mid - 1;
            else
                left = mid + 1;
        }
        return list.get(right).toString();
    }

    /* 可以使用欧几里得算法实现将有限小数转换为最简分数的功能。*/
    static String finiteDecimalMatchFraction(double num) {
        /* 将num转换为指定位数 */
        int precision = 9;//保留四位小数
        // 创建DecimalFormat对象，指定精确位数
        DecimalFormat df = new DecimalFormat("#." + "0".repeat(precision));
        // 格式化小数
        num = Double.parseDouble(df.format(num));

        // 将小数转换为分数
        int numerator = (int) (num * 1000000000);
        int denominator = 1000000000;
        // 使用欧几里得算法求解最大公约数
        int gcd = findGCD(numerator, denominator);

        // 将分子和分母都除以最大公约数
        numerator /= gcd;
        denominator /= gcd;

        return numerator + "/" + denominator;
    }

    static int findGCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        return findGCD(b, a % b);
    }

    public static void main(String[] args) {
        FractionMatch fractionMatch = new FractionMatch(300);
        System.out.println(fractionMatch.match(0.20588235294117646));
    }
}
