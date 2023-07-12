/* 结果类，用于保存多项式计算的结果 */
public class Result implements Cloneable{
    Polynomial poly1;
    Polynomial poly2;
    Polynomial res;
    String operator;

    /* 构造方法 */
    public Result(Polynomial poly1,Polynomial poly2,Polynomial res,String operator) throws CloneNotSupportedException {
        if(poly1 != null) this.poly1 = poly1.clone();
        else this.poly1 = null;
        if(poly2 != null) this.poly2 = poly2.clone();
        else this.poly2 = null;
        this.res = res.clone();
        this.operator = new String(operator);
    }

    //打印结果
    public void printResult(){
        if(!operator.contains("^")){
            System.out.print("(");
            poly1.printPoly();
            System.out.print(")");
            System.out.print(" " + operator + " ");
            System.out.print("(");
            poly2.printPoly();
            System.out.print(")");
            System.out.print(" = ");
            System.out.print("(");
            res.printPoly();
            System.out.print(")");
            System.out.println();
        }
        else {
            System.out.print("(");
            if(poly1 != null) poly1.printPoly();
            else poly2.printPoly();
            System.out.print(")");
            System.out.print(operator);
            System.out.print(" = ");
            res.printPoly();
            System.out.println();
        }
    }
}
