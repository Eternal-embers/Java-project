package Polynomial;

import java.util.ArrayList;

public  class Polynomial implements Cloneable,Digit{
    /* 数据域 */
    ArrayList<Element> poly;//多项式的各项系数和次方
    double x;//多项式的变量x
    int power;//多项式的次方
    double result;//赋值后多项式的结果

    public Polynomial(){
        this.poly = new ArrayList<Element>();
        this.x = 0;//X初始赋值为0
        this.power = 1;//默认多项式为一次幂
        this.result = 0;
    }

    //向多项式添加元素，该方法自动按次方大小添加元素，保证多项式元素的次方从小到大排序
    public Element addElement(double coef,double power){
        if(coef == 0) {
            return null;//如果系数为0，不添加到多项式中
        }
        if(poly.size() == 0) {
            Element newElement = new Element(coef,power);
            poly.add(newElement);//如果多项式为空，直接添加一项元素
            return newElement;
        }
        //按照次方从小到大排序
        else {
            //插入到合适位置
            int i;
            Element e = null;
            for(i = 0;i < poly.size();i++){
                e = poly.get(i);
                if(power > e.power){
                    break;
                }
                else if(power == e.power){
                    break;
                }
            }
            if(power == e.power) {
                e.setCoef(e.coef + coef);//次方相同，系数相加
                return e;
            }
            else {
                Element newElement = new Element(coef, power);
                poly.add(i,newElement);//插入元素的次方大于前一项且小于当前项，则在此位置插入
                return newElement;
            }
        }
    }

    //打印浮点数
    public void printDouble(double num){
        int digit = getDecimalDigit(num);
        if(num > 0){
            switch(digit){
                case 0: System.out.printf("%.0f",num); break;
                case 1: System.out.printf("%.1f",num); break;
                case 2: System.out.printf("%.2f",num); break;
                case 3: System.out.printf("%.3f",num); break;
                default: System.out.printf("%.4f",num); break;
            }
        }
        else if(num < 0){
            switch(digit){
                case 0: System.out.printf("(%.0f)",num); break;
                case 1: System.out.printf("(%.1f)",num); break;
                case 2: System.out.printf("(%.2f)",num); break;
                case 3: System.out.printf("(%.3f)",num); break;
                default: System.out.printf("(%.4f)",num); break;
            }
        }
        else System.out.print("0");
    }

    //打印多项式中除最后一项的项
    public void print(Element e){
        if(e.power != 0){
            //如果系数为0不输出该项
            if(e.coef != 0){
                if(e.coef != 1){//如果系数为1不输出系数
                    printDouble(e.coef);
                }
                System.out.print("[X^");
                printDouble(e.power);
                System.out.print("]");
                System.out.print(" + ");
            }
        }
        else {
            printDouble(e.coef);//指数为0，输出系数
            System.out.print(" + ");
        }
    }

    //单独打印多项式中的一项
    public void printSingle(Element e){
        if(e.power != 0){
            //如果系数为0不输出该项
            if(e.coef != 0){
                if(e.coef != 1){//如果系数为1不输出系数
                    printDouble(e.coef);
                }
                System.out.print("[X^");
                printDouble(e.power);
                System.out.print("]");
            }
        }
        else {
            printDouble(e.coef);//指数为0，输出系数
        }
    }

    //打印多项式
    public void printPoly(){
        if(poly.size() == 0) {
            System.out.println("null");
        }
        //输出多项式中的剩余元素
        int i;
        for(i = 0; i < poly.size() - 1; i++){
            //按系数的小数位数来输出
            Element e = poly.get(i);
            print(e);
            if((i + 1) % 10 ==0) System.out.println();//每输出5项后换行
        }
        printSingle(poly.get(poly.size() - 1));//单独输出最后一项
        //if((i + 1) % 5 != 0) System.out.println("");
    }

    //多项式的复制
    @Override
    public Polynomial clone(){
        Polynomial p = new Polynomial();
        for(int i = 0;i < this.poly.size();i++){
            Element e = this.poly.get(i);
            p.poly.add(new Element(e.coef,e.power));
        }
        return p;
    }

    /* 给多项式的变量X赋值 */
    public void setX(double x){
        this.x = x;
    }

    /* 设置多项式的次幂 */
    public void setPower(int power){
        this.power = power;
    }

    public double getResult(){
        int i;
        for(i = 0;i < poly.size();i++){
            Element e = poly.get(i);
            result += e.coef * Math.pow(x,e.power);
        }
        result *= this.power;
        return result;
    }

}
