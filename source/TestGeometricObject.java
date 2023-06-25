package javaCode;
public class TestGeometricObject{
    public static void main(String[] args){
        GeometricAbstract geoObject1 = new Circle(5);
        GeometricAbstract geoObject2 = new Rectangle(5.3);w
        System.out.println("The two objects have the same area?" + equalArea(geoObject1,geoObject2));
        displayGeometricObject(geoObject1);
        displayGeometricObject(geoObject2);
    }
    public static boolean eaualArea(GeometricObject object1,GeometricObject object2){
        return object1.getArea()==object2.getArea();
    }
    public static void displayGeometricObject(GeometricAbstract geoObject1){
        System.out.println();
        System.out.println("The area is " + geoObject1.getArea());
        System.out.println("The perimeter is " + geoObject1.getPerimeter());
    }
}