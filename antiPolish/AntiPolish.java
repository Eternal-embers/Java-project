import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.System.exit;


public class AntiPolish {
    /*
        变量集从A~Z
        static char[] varSet_AZ = {
            'A','B','C','D','E','F','G','H','I','J','K','L','M',
            'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
        };

        变量集从a~z
        static char[] varSet_az = {
            'a','b','c','d','e','f','g','h','i','j','k','l','m',
            'n','o','p','q','r','s','t','u','v','w','x','y','z'
        };
    */
    static Object[] varSet_az_value = new Object[26];//索引index处的值表示变量('a'+index)的值
    static Object[] varSet_AZ_value = new Object[26];//索引index处的值表示变量('A'+index)的值
    static boolean ifShowProcess = true;
    static File logPath = new File("data//log.txt");

    static void varsInitialize(){
        /* 设置所有变量的初始值为null */
        for(int i = 0;i < 26;i++){
            varSet_az_value[i] = null;
            varSet_AZ_value[i] = null;
        }
    }

    /* 读取指定文件的变量集 */
    static String readVarSet(File readPath) throws IOException {
        String varSet = "";
        if(!readPath.exists()) {
            System.out.println("路径\"" + readPath + "\"不存在!");
            exit(0);
        }
        Scanner scanner = new Scanner(readPath);
        while(scanner.hasNextLine()){
            varSet = varSet.concat(scanner.nextLine());
        }
        scanner.close();
        return varSet;
    }

    /* 将程序中已经赋值的变量集保存到指定文件 */
    static void saveVarSet(File savePath) throws IOException {
        FileWriter writer = new FileWriter(savePath,true);//向后追加
        writer.write('$' + "\n");
        for(int i = 0; i < 26;i++){
            Object obj = varSet_az_value[i];
            if(obj != null) {
                writer.write('a' + i);
                writer.write(" = " +  obj + "\n");
            }
        }
        for(int i = 0; i < 26;i++){
            Object obj = varSet_AZ_value[i];
            if(obj != null) {
                writer.write('A' + i);
                writer.write(" = " +  obj + "\n");
            }
        }
        writer.write('$' + "\n");
        writer.close();
    }

    static void logging(String log) throws IOException {
        FileWriter writer = new FileWriter(logPath);


    }

    static void save() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入保存路径：");
        String path = scanner.nextLine();
        File savePath = new File(path);
        while(!savePath.exists()){
            System.out.println("路径\"" + path + "\"不存在，是否新建该文件再保存？(是/否)");
            System.out.print(">>");
            String option = scanner.nextLine();
            if(option.contains("是") || option.compareTo("")==0)
                break;
            else{
                System.out.println("- - - - - - - - - - - - - - - -");
                System.out.println("重新输入新的路径进行保存       >>1");
                System.out.println("取消保存                    >>0");
                System.out.println("- - - - - - - - - - - - - - - -");
                System.out.println(">>");
                option = scanner.nextLine();
                if(option.contains("1") || option.compareTo("") == 0){
                    System.out.print("请输入新的保存路径：");
                    path = scanner.nextLine();
                    savePath = new File(path);
                }
                else
                    System.out.println("已取消保存!");
            }
        }
        saveVarSet(savePath);
        System.out.println("保存变量集成功!");
    }

    /* 获取运算符的优先级，返回值越大优先级越大 */
    static int getLevel(char ch){
        if(ch == '+' || ch == '-')  return 1;
        else if(ch == '*' || ch == '/' || ch == '%') return 2;
        else return 3;
    }

    static boolean isOperator(char ch){
        char[] operators = {
                '+','-','*','/','%'
        };
        for (char operator : operators) if (ch == operator) return true;
        return false;
    }

    static void showAllVars(){
        System.out.println("进行赋值过的所有变量：");
        for(int i = 0;i < 26;i++){
            if(varSet_az_value[i] != null){
                System.out.printf("%c = ",'a' + i);
                System.out.println(varSet_az_value[i]);
            }
        }
        for(int i = 0;i < 26;i++){
            if(varSet_AZ_value[i] != null){
                System.out.printf("%c = ",'A' + i);
                System.out.println(varSet_AZ_value[i]);
            }
        }
    }

    /* 将list转String */
    static String listToString(List<Object> list){
        if(list.size() == 0) return null;
        String res = list.toString();
        //System.out.println(res);
        String sub = res.substring(1,res.length() - 1);
        /* 去除字符串中的逗号和空字符 */
        return sub.replaceAll("\\s","").replaceAll(",","");
    }

    /*
        规定变量集以$开头以$结尾
        变量集的格式
        $
        a = value
        b = value
        ...
        $
        在书写变量集的时候允许任意使用空字符，变量名称必须使用变量集里面的变量名称，可以是大小写字母
    */
    static void submitVar(String varSet){
        int length = varSet.length();
        /* 以$为开头和结尾表示该字符串为变量集 */
        if(varSet.charAt(0) == '$' && varSet.charAt(length - 1) == '$'){
            for(int i = 1;i < length - 1;i++){
               char ch = varSet.charAt(i);//逐字处理变量集中的字符
               if(ch >= 'a' && ch <= 'z'){
                   int start,end;
                   /* 向后找到第一个=的位置 */
                   start = varSet.indexOf("=",i);
                   while(!(varSet.charAt(start) >= '0' && varSet.charAt(start) <= '9'))
                       start++;
                   end = start + 1;
                   while((varSet.charAt(end) >= '0') && (varSet.charAt(end) <= '9') || varSet.charAt(end) == '.')
                       end++;
                   String sub = varSet.substring(start,end);
                   Object value;
                   //如果sub中有空格下面将报错
                   if(sub.contains("."))
                       value = Double.parseDouble(sub);
                   else
                       value = Integer.parseInt(sub);
                   varSet_az_value[ch - 'a'] = value;//变量赋值
                   i = end - 1;
               }
               else if(ch >= 'A' && ch <= 'Z'){
                   int start,end;
                   /* 向后找到第一个=的位置 */
                   start = varSet.indexOf("=",i);
                   while(!(varSet.charAt(start) >= '0' && varSet.charAt(start) <= '9'))
                       start++;
                   end = start + 1;
                   while((varSet.charAt(end) >= '0') && (varSet.charAt(end) <= '9') || varSet.charAt(end) == '.')
                       end++;
                   String sub = varSet.substring(start,end);
                   Object value;
                   //如果sub中有空格下面将报错
                   if(sub.contains("."))
                       value = Double.parseDouble(sub);
                   else
                       value = Integer.parseInt(sub);
                   varSet_AZ_value[ch - 'A'] = value;//变量赋值
                   i = end - 1;
               }
               else if(ch == ' ' || ch == '\n' || ch == '\t');
               else {
                   System.out.println(varSet);
                   System.out.printf("在上面提交的变量集中%c不属于规定的变量名称，变量名称只能是大小写字母!",ch);
                   exit(0);
               }
            }
        }
        else{
            System.out.printf("变量集{%s}格式错误!",varSet);
            exit(0);
        }
    }

    /*
        计算表达式转逆波兰表达式的测试
        (3 + 5 % 6) * 7 + 6 / 3 - 4 => 356%+7*63/+4-
        ((a * c + 7) % b + 2) + c * (5 - d) => ac*7+b%2+c5d-*+
        (((a * b- 5) % c + 9) + d * 3 / e) + f % (g - h * 2 ) + 4 => ab*5-c%9+d3*e/+fgh2*-%+4+
        (10 - (a * c - 5 / b) % d + 12) * 30 - 250 * e + f => 10ac*5b/-d%-12+30*250e*-f+
        25 * a - ((340 * b - 1024) * 12 + 23 % c) - 7 * d / 6 => 25a*340b*1024-12*23c%+-7d*6/-
     */
    static List antiPolish(String expression){
        Stack stack = new Stack();
        ArrayList<Object> postfixNotation = new ArrayList<>();
        int length = expression.length();
        for(int i = 0;i < length;i++){
            char ch = expression.charAt(i);
            if(ch == ' ' || ch == '\t') continue;
            /* 如果是变量 */
            if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
                postfixNotation.add(ch);
            /* 如果是数字 */
            else if(ch >= '0' && ch <= '9'){
                int j = i;
                char num;
                Object number;
                do{
                    j++;
                    if(j >= length) break;
                    num = expression.charAt(j);
                }while(num >= '0' && num <= '9' || num == '.');
                String sub = expression.substring(i,j);
                if(sub.contains(".")){
                    number = Double.parseDouble(sub);
                }
                else number = Integer.parseInt(sub);//将数字字符串解析为Int变量
                i = j - 1;//将下一个扫描的位置移到数字后一个字符
                postfixNotation.add(number);
            }
            else {
                if(stack.isEmpty()) stack.push(ch);//栈为空
                else if(ch == ')' || ch == '）') {
                    /* 一直出栈直到遇到'(' */
                    char top = (char)stack.peek();
                    while(top != '(' && top != '（') {
                        postfixNotation.add(stack.pop());
                        top = (char)stack.peek();
                    }
                    stack.pop();
                }//中英文括号可以混合使用
                else if(ch == ']') {
                    /* 一直出栈直到遇到'(' */
                    while((char)stack.peek() != '[')
                        postfixNotation.add(stack.pop());
                    stack.pop();
                }//方括号[]与()的作用相同
                else {
                    char top = (char)stack.peek();
                    int level1 = getLevel(ch);//扫描到的符号
                    int level2 = getLevel(top);//栈顶的符号现在h
                    /* 将优先级低于栈顶符号的符号入栈 */
                    if(level1 > level2 || top == '(' || top == '（') {
                        stack.push(ch);
                    }
                    else {//把优先级大于等于当前级别的符号全部出栈
                        while(level1 <= level2 && top != '(' && top != '（' && top != '['){
                            postfixNotation.add(stack.pop());
                            if(stack.isEmpty()) break;
                            top = (char)stack.peek();
                            level2 = getLevel(top);
                        }
                        stack.push(ch);//将当前扫描的符号入栈
                    }
                }
            }
        }
        /* 将栈中剩余符号pop */
        while(!stack.isEmpty())
            postfixNotation.add(stack.pop());
        /*
            测试将计算表达式转换为后缀表达式
            System.out.println(listToString(result));//将ArrayList result转换为字符串
        */
        return postfixNotation;
    }

    /* 对逆波兰表达式进行求值 */
    static Object antiPolishEvaluate(ArrayList<Object> postfixNotation){
        LinkedList<Object> list= new LinkedList<>();
        LinkedList<Object> varTrack = new LinkedList<>();//用于跟踪变量，确定哪些值是变量
        int length = postfixNotation.size();
        for(int i = 0;i < length;i++){
            /*
            System.out.println("list：" + listToString(list));
            System.out.println("varTrack：" + listToString(varTrack));
             */
            Object obj = postfixNotation.get(i);
            /* 对象是Double的实例 */
            if(obj instanceof Double){
                list.push(obj);
                varTrack.push(obj);//跟踪
                //System.out.println("Double:" + obj);
            }
            /* 对象是Integer的实例 */
            else if(obj instanceof Integer){
                list.push(obj);
                varTrack.push(obj);//跟踪
            }
            /* 对象是字符的实例 */
            else if(obj instanceof Character){
                //System.out.println("Character:" + obj);
                char ch = (char)obj;
                if(ch >= 'a' && ch <=  'z'){
                    if(varSet_az_value[ch - 'a'] == null){
                        System.out.printf("变量%c未进行赋值！",ch);
                        exit(0);
                    }
                    list.push(varSet_az_value[ch - 'a']);
                    varTrack.push(ch);//跟踪
                }
                else if(ch >= 'A' && ch <= 'Z'){
                    if(varSet_AZ_value[ch - 'A'] == null){
                        System.out.printf("变量%c未进行赋值！",ch);
                        exit(0);
                    }
                    list.push(varSet_AZ_value[ch - 'A']);
                    varTrack.push(ch);//跟踪
                }
                else if(isOperator(ch)){
                    int flag = 0;//用于判断value1和value2的类型
                    Object value1,value2;
                    value1 = list.pop();//权值1，Integer为1，Double为0
                    value2 = list.pop();//权值2，Integer为1，Double为0
                    Object obj1,obj2;
                    obj1 = varTrack.pop();//跟踪
                    obj2 = varTrack.pop();//跟踪
                    if(value1 instanceof Integer)
                        flag += 1;
                    if(value2 instanceof Integer)
                        flag += 2;
                    //System.out.println("flag = " + flag);
                    switch(ch){
                        case '+':{
                            switch(flag){
                                case 0:{
                                    list.push((Double) value2 + (Double) value1);
                                    break;
                                }//value1：Double value2：Double
                                case 1:{
                                    list.push((Double) value2 + (Integer) value1);
                                    break;
                                }//value1：Integer value2：Double
                                case 2:{
                                    list.push((Integer) value2 + (Double) value1);
                                    break;
                                }//value1: Double value2：Integer
                                case 3:{
                                    list.push((Integer) value2 + (Integer) value1);
                                    break;
                                }//value：Integer value2：Integer
                            }
                            break;
                        }
                        case '-':{
                            switch(flag){
                                case 0:{
                                    list.push((Double) value2 - (Double) value1);
                                    break;
                                }//value1：Double value2：Double
                                case 1:{
                                    list.push((Double) value2 - (Integer) value1);
                                    break;
                                }//value1：Integer value2：Double
                                case 2:{
                                    list.push((Integer) value2 - (Double) value1);
                                    break;
                                }//value1: Double value2：Integer
                                case 3:{
                                    list.push((Integer) value2 - (Integer) value1);
                                    break;
                                }//value：Integer value2：Integer
                            }
                            break;
                        }
                        case '*':{
                            switch(flag){
                                case 0:{
                                    list.push((Double) value2 * (Double) value1);
                                    break;
                                }//value1：Double value2：Double
                                case 1:{
                                    list.push((Double) value2 * (Integer) value1);
                                    break;
                                }//value1：Integer value2：Double
                                case 2:{
                                    list.push((Integer) value2 * (Double) value1);
                                    break;
                                }//value1: Double value2：Integer
                                case 3:{
                                    list.push((Integer) value2 * (Integer) value1);
                                    break;
                                }//value：Integer value2：Integer
                            }
                            break;
                        }
                        case '/':{
                            switch(flag){
                                case 0:{
                                    list.push((Double) value2 / (Double) value1);
                                    break;
                                }//value1：Double value2：Double
                                case 1:{
                                    list.push((Double) value2 / (Integer) value1);
                                    break;
                                }//value1：Integer value2：Double
                                case 2:{
                                    list.push((Integer) value2 / (Double) value1);
                                    break;
                                }//value1: Double value2：Integer
                                case 3:{
                                    list.push((Integer) value2 / (Integer) value1);
                                    break;
                                }//value：Integer value2：Integer
                            }
                            break;
                        }
                        case '%':{
                            switch(flag){
                                case 0:{
                                    list.push((Double) value2 / (Double) value1);
                                    break;
                                }//value1：Double value2：Double
                                case 1:{
                                    list.push((Double) value2 / (Integer) value1);
                                    break;
                                }//value1：Integer value2：Double
                                case 2:{
                                    list.push((Integer) value2 / (Double) value1);
                                    break;
                                }//value1: Double value2：Integer
                                case 3:{
                                    list.push((Integer) value2 % (Integer) value1);
                                    break;
                                }//value：Integer value2：Integer
                            }
                            break;
                        }
                    }
                    varTrack.push(list.peek());//跟踪每一步计算求得的值
                    if(ifShowProcess){
                        int status = 0;
                        if(obj1 instanceof Character) status += 1;//权值1
                        if(obj2 instanceof Character) status += 2;//权值2
                        switch(status){
                            case 0:{
                                System.out.print(value2 + " " + ch + " " + value1 + " = ");
                                break;
                            }
                            case 1:{
                                System.out.print(value2 + " " + ch + " " + obj1 + "(" + value1 + ")" + " = ");
                                break;
                            }
                            case 2:{
                                System.out.print(obj2 + "(" + value2 + ")" + " " + ch + " " + value1  + " = ");
                                break;
                            }
                            case 3: {
                                System.out.print(obj2 + "(" + value2 + ")" + " " + ch + " " + obj1 + "(" + value1 + ")" + " = ");
                                break;
                            }
                        }
                        System.out.println(list.peek());
                    }
                }
                else System.out.printf("无法找到%c的定义\n",ch);
            }
        }
        if(list.size() == 0){
            System.out.printf("逆波兰表达式\"%s\"存在错误!",listToString(postfixNotation));
            exit(0);
        }
        return list.pop();//将最后求得的值返回
    }

    public static void main(String[] args) throws IOException {
        varsInitialize();//变量初始化
        File varSetPath = new File("data//test.txt");
        String str = "$z = 40.5p = 60.9 \tq = 80.75 R = 3.14$";
        submitVar(str);
        String varSet = readVarSet(varSetPath);//读取变量集
        submitVar(varSet);//提交变量集
        showAllVars();//展示全部变量
        save();
        Scanner input = new Scanner(System.in);
        System.out.print("请输入计算表达式：");
        String expression = input.nextLine();
        ArrayList  postfixNotation = (ArrayList) antiPolish(expression);
        System.out.println("结果是：" + antiPolishEvaluate(postfixNotation));
        /*
        LinkedList list = new LinkedList();
        list.push(Double.parseDouble("3.14"));
        Object var = list.pop();
        list.push(((Double)var).doubleValue());
        System.out.println(listToString(list));
         */
    }
}
