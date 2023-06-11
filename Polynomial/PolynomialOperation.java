public class PolynomialOperation {
    //构造方法
    public PolynomialOperation(){
    }
    /* 多项式加法 */
    public Polynomial add(Polynomial poly1,Polynomial poly2)  throws CloneNotSupportedException {
        if(poly1 == null || poly1.poly == null || poly1 == null || poly2.poly == null)
            return null;
        Polynomial res;
        if(poly1.poly.size() > poly2.poly.size()){
            res = poly1.clone();
            for(int i = 0;i < poly2.poly.size();i++){
                Element e = poly2.poly.get(i);
                res.addElement(e.coef,e.power);
            }
        }
        else {
            res = poly2.clone();
            for(int i = 0;i < poly1.poly.size();i++){
                Element e = poly1.poly.get(i);
                res.addElement(e.coef,e.power);
            }
        }
        return res;
    }

    /* 多项式减法 */
    public Polynomial sub(Polynomial poly1,Polynomial poly2) throws CloneNotSupportedException {
        if(poly1 == null || poly1.poly == null || poly1 == null || poly2.poly == null)
            return null;
        Polynomial res ;
        if(poly1.poly.size() > poly2.poly.size()){
            res =  poly1.clone();
            for(int i = 0;i < poly2.poly.size();i++){
                Element e = poly2.poly.get(i);
                res.addElement(-e.coef,e.power);
            }
        }
        else {
            res = poly2.clone();
            for(int i = 0;i < poly1.poly.size();i++){
                Element e = poly1.poly.get(i);
                res.addElement(-e.coef,e.power);
            }
        }
        return res;
    }

    /* 多项式乘法 */
    public Polynomial mult(Polynomial poly1,Polynomial poly2){
        if(poly1 == null || poly1.poly == null || poly1 == null || poly2.poly == null)
            return null;
        Polynomial res = new Polynomial();
        for(int i = 0;i < poly1.poly.size(); i++){
            Element e1 = poly1.poly.get(i);
            for(int j = 0;j < poly2.poly.size(); j++){
                Element e2 = poly2.poly.get(j);
                res.addElement(e1.coef * e2.coef,e1.power + e2.power);
            }
        }
        return res;
    }

    /* 多项式的幂 */
    public Polynomial pow(Polynomial poly,int power){
        if(poly == null || poly.poly == null ) {
            return null;
        }
        if(power == 0) {
            Polynomial res = new Polynomial();
            res.addElement(1,0);
            return res;
        }
        if(power == 1) return poly;
        Polynomial res;
        if(power % 2 == 0){
            res = mult(pow(poly,power / 2),pow(poly,power / 2));
        }
        else {
            res = mult(mult(pow(poly,power / 2),pow(poly,power / 2)),poly);
        }
        return res;
    }
}
