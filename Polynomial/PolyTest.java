public class PolyTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        //建立多项式
        Polynomial poly = new Polynomial();
        poly.addElement(1,1);
        poly.addElement(1,0);
        //打印多项式
        poly.printPoly();
        //测试获取数的整数位数和小数位数
        int digit1 = poly.getIntegerDigit(1345.0);
        int digit2 = poly.getDecimalDigit(1345.0);
        System.out.printf("\n%d.%d\n",digit1,digit2);
        //测试加法
        PolynomialOperation operation = new PolynomialOperation();
        Result res = new Result(poly,poly,operation.add(poly,poly),"+");
        res.printResult();
        //测试减法
        res = new Result(poly,poly,operation.sub(poly,poly),"-");
        res.printResult();
        //测试乘法
        res = new Result(poly, poly,operation.mult(poly,poly),"*");
        res.printResult();
        //测试幂运算
        res = new Result(poly,null,operation.pow(poly,4),"^4");
        res.printResult();
    }
}
