package javaCode;

public class Card {
		private String name;
		public Card(){
			name = "";
		}//构造方法
		public Card(String name){
			this.name = name;
		}//以指定name构造Card实例
		public String getName(){
	      return name;
		}//获取姓名
		public void setname(String name) {
			this.name = name;
		}//修改name
		public boolean isExpired() {
	       return false;
		}//是否已过期
		public String format(){
	       return "Card holder: " + name;
		}//返回卡的格式化
		public boolean equals(Card ob) {
			if(this.name.equals(ob.name)) return true;
			return false;
		}
}
