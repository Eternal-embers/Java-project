package Polynomial;

public interface Digit {
    //获取整数位数
    default int getIntegerDigit(double num){
        int digit = 0;
        int t = (int) Math.floor(num);//向下取整
        while(t > 0){
            digit++;
            t /= 10;
        }
        return digit;
    }

    //获取小数位数
    default int getDecimalDigit(double num){
        int digit = 0;
        double t = num - (int)Math.floor(num);
        while(t - Math.floor(t) > 1e-9){
            digit++;
            t *= 10;
        }
        return digit;
    }
}
