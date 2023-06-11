public class Element implements Cloneable{
    /* 数据域 */
    double coef;//系数
    double power;//次方
    /* 构造方法 */
    public Element(double coef,double power){
        this.coef = coef;
        this.power = power;
    }
    /* 访问器 */
    public void setCoef(double coef){
        this.coef = coef;
    }
    public void setPower(double power){
        this.power = power;
    }
    /* 修改器 */
    public double getCoef() {
        return this.coef;
    }
    public double getPower(){
        return this.power;
    }

    /* 复制 */
    @Override
    public Element clone() throws CloneNotSupportedException {
        return (Element) super.clone();
    }
}
