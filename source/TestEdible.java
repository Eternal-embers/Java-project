package javaCode;

public class TestEdible {
	public static void main(String[] args) {
		Object[] objects = {new Tiger(),new Chicken(),new Apple(),new Orange()};
		for(int i = 0;i < objects.length;i++) {
			if(objects[i] instanceof Edible)
				System.out.println(((Edible)objects[i]).howToEat());
			if(objects[i] instanceof Animal)
				System.out.println((((Animal) objects[i]).sound()));
		}
	}
}

	abstract class Animal extends Object{
		private double weight;
		public double getWeight() {
			return weight;
		}
		public void setWeight(double weight) {
			this.weight = weight;
		}
		public abstract String sound();
	}//抽象类动物
	
	class Chicken extends Animal implements Edible{
		public String howToEat(){
			return "Chicken: Fry it";
		}
		@Override
		public String sound() {
			return "Chicken: cook-a-doodle-doo";
		}
	}
	
	class Tiger extends Animal{
		@Override
		public String sound() {
			return "Tiger: RROOAARR";
		}
	}
	
	abstract class Fruit extends Object implements Edible{
		
	}//抽象类水果
	
	class Apple extends Fruit{
		@Override
		public String howToEat() {
			return "Apple: Make apple cider";
		}
	}
	class Orange extends Fruit{
		@Override
		public String howToEat() {
			return "Orange: Make orange juice";
		}
	}
