package javaCode;
public abstract class GeometricAbstract{
    private String color = "white";
    private boolean filled;
    private java.util.Date dateCreated;
    //默认构造方法
    protected GeometricAbstract(String color,boolean filled){
        dateCreated = new java.util.Date();
    }
    //返回color
    public String getColor(){
        return color;
    }
    //设置color
    public void setColor(String color){
        this.color = color;
    }
    //返回是否填充颜色
    public boolean isFilled(){
        return filled;
    }
    //设置filled
    public void setFilled(boolean filled){
        this.filled = filled;
    }
    //获取创建日期
    public java.util.Date getDateCreated(){
        return dateCreated;
    }
    @Override
    public String toString(){
        return "created on " + dateCreated + "\ncolor:" + color + 
        "and filled:" + filled;
    }
    //抽象方法
    public abstract  double getArea();
    public abstract double getPerimeter();
}