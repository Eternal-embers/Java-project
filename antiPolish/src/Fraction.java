public class Fraction {
    int numerator;//分子
    int denominator;//分母
    double value;//小数值

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.value = numerator *  1.0 / denominator;
    }

    public String toString(){
        return numerator + "/" + denominator;
    }
}
