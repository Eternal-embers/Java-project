import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    static boolean ifShowProcess = true;//是否显示每一步计算过程
    static boolean ifShowAntiPolish = true;//是否显示转换得到的逆波兰表达式
    static boolean ifshowMenu = false;//是否显示菜单
    static String inputAnchor = "$";//输入锚，用户输入时，位于最前面的提示输入的标志
    static File logPath = new File("data//log.txt");
    static File defaultReadPath = new File("data//defaultReadPath.txt");
    static File defaultSavePath = new File("data//defaultSavePath.txt");
    static Double PI = Math.PI;//圆周率π
    static Double Eu = Math.E;//自然常数e
    static ArrayList<String> mathExpression = new ArrayList<>();//数学表达式

    static void Initialize(){
        /* 设置所有变量的初始值为null */
        for(int i = 0;i < 26;i++){
            varSet_az_value[i] = null;
            varSet_AZ_value[i] = null;
        }
        /* 检查存储使用记录的文件是否存在 */
        if(!logPath.exists()){
            try{
                logPath.createNewFile();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!defaultReadPath.exists()){
            try{
                defaultReadPath.createNewFile();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!defaultSavePath.exists()){
            try{
                defaultSavePath.createNewFile();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* 存储所有使用记录 */
    static void logging(String log){
        try {
            FileWriter writer = new FileWriter(logPath,true);
            writer.write(log + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* 读取指定文件的变量集 */
    static String readVarSet(File readPath) throws IOException {
        String varSet = "";
        if(!readPath.exists()) {
            System.out.println("路径\"" + readPath + "\"不存在!");
            exit(0);
        }
        System.out.println("所读取的变量集：");
        Scanner scanner = new Scanner(readPath);
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            System.out.println(line);
            varSet = varSet.concat(line);
        }
        scanner.close();
        return varSet;
    }

    /* 读取 */
    static String read() throws IOException {
        System.out.println("当前所在路径：" + new File("").getAbsolutePath());
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入读取路径(输入default使用默认读取路径)：");
        String path = scanner.nextLine();
        if(path.contains("default")){
            if(!defaultReadPath.exists()){
                System.out.println("尚未设置默认读取路径！");
            }
            else
                path = defaultReadPath.getAbsolutePath();
        }
        File readPath = new File(path);
        /* 如果输入的路径是目录 */
        while(readPath.isDirectory()){
            System.out.println("\"" + readPath.getAbsolutePath() + "\"" + "为目录，无法对目录进行读取!");
            System.out.print("请输入新的路径：");
            path = scanner.nextLine();
            readPath = new File(path);
        }
        /* 如果输入的路径不存在 */
        while(!readPath.exists()){
            System.out.println("路径\"" + path + "\"不存在，是否新建该文件？(是/否)");
            System.out.print(inputAnchor);
            String option = scanner.nextLine();
            if(option.contains("是") || option.compareTo("")==0) {
                File file = new File(path);
                file.createNewFile();//创建该文件
                return null;
            }
            else{
                System.out.println("- - - - - - - - - - - - - - - -");
                System.out.println("重新输入新的路径进行读取       >>1");
                System.out.println("取消读取                    >>0");
                System.out.println("- - - - - - - - - - - - - - - -");
                System.out.print(inputAnchor);
                option = scanner.nextLine();
                if(option.contains("1") || option.compareTo("") == 0){
                    System.out.print("请输入新的保存路径：");
                    path = scanner.nextLine();
                    readPath = new File(path);
                }
                else {
                    System.out.println("已取消读取!");
                    logging("从路径\"" + path + "\"读取变量集失败，取消读取");
                    return null;
                }
            }
        }
        String varSet = readVarSet(readPath);
        submitVar(varSet);
        System.out.println("读取变量集成功!");
        logging(new Date() + " 读取变量集");
        logging("读取路径" + readPath.getAbsolutePath());
        logging("读取的变量集：\n" + varSet);
        return varSet;
    }

    /* 将程序中已经赋值的变量集保存到指定文件 */
    static void saveVarSet(File savePath) throws IOException {
        FileWriter writer = new FileWriter(savePath,true);//向后追加
        System.out.println("保存变量集：");
        writer.write("$\n");
        for(int i = 0; i < 26;i++){
            Object obj = varSet_az_value[i];
            if(obj != null) {
                writer.write((char)('a' + i));
                writer.write(" = " +  obj + "\n");
                System.out.print('a' + i);
                System.out.println(" = " + obj);
            }
        }
        for(int i = 0; i < 26;i++){
            Object obj = varSet_AZ_value[i];
            if(obj != null) {
                writer.write((char)('A' + i));
                writer.write(" = " +  obj + "\n");
                System.out.print('A' + i);
                System.out.println(" = " + obj);
            }
        }
        writer.write("$");
        writer.close();
    }

    /* 保存变量集 */
    static void save() throws IOException {
        System.out.println("当前所在路径：" + new File("").getAbsolutePath());
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入保存路径(输入default使用默认保存路径)：");
        String path = scanner.nextLine();
        if(path.contains("default")){
            if(!defaultReadPath.exists()){
                System.out.println("尚未设置默认保存路径！");
            }
            else
                path = defaultSavePath.getAbsolutePath();
        }
        File savePath = new File(path);
        /* 如果输入的路径是目录 */
        while(savePath.isDirectory()){
            System.out.println("\"" + savePath.getAbsolutePath() + "\"" + "为目录，无法对目录进行写入!");
            System.out.print("请输入新的路径：");
            path = scanner.nextLine();
            savePath = new File(path);
        }
        /* 如果输入的路径不存在 */
        while(!savePath.exists()){
            System.out.println("路径\"" + path + "\"不存在，是否新建该文件再保存？(是/否)");
            System.out.print(inputAnchor);
            String option = scanner.nextLine();
            if(option.contains("是") || option.compareTo("")==0)
                break;
            else{
                System.out.println("- - - - - - - - - - - - - - - -");
                System.out.println("重新输入新的路径进行保存       >>1");
                System.out.println("取消保存                    >>0");
                System.out.println("- - - - - - - - - - - - - - - -");
                System.out.print(inputAnchor);
                option = scanner.nextLine();
                if(option.contains("1") || option.compareTo("") == 0){
                    System.out.print("请输入新的保存路径：");
                    path = scanner.nextLine();
                    savePath = new File(path);
                }
                else{
                    System.out.println("已取消保存!");
                    return;
                }
            }
        }
        saveVarSet(savePath);
        System.out.println("保存变量集成功!");
        logging(new Date() + " 保存变量集");
        logging("保存路径" + savePath.getAbsolutePath());
        logging("保存的变量集：\n" + varSetToString());
    }

    /* 将当前变量集转为字符串 */
    static String varSetToString(){
        String varSet = "$\n";
        for(int i = 0;i < 26;i++){
            if(varSet_az_value[i] != null){
                varSet = varSet.concat((char)('a' + i ) + " = " + varSet_az_value[i] + "\n");
            }
        }
        for(int i = 0;i < 26;i++){
            if(varSet_AZ_value[i] != null){
                varSet = varSet.concat((char)('A' + i ) + " = " + varSet_AZ_value[i] + "\n");
            }
        }
        varSet = varSet.concat("$\n");
        return varSet;
    }

    /* 获取运算符的优先级，返回值越大优先级越大 */
    static int getLevel(String ch){
        if(ch.equals("+") || ch.equals("-"))  return 1;
        else if(ch.equals("*") || ch.equals("/") || ch.equals("%")) return 2;
        else if(ch.equals("^")) return 3;
        else if(ch.equals("(") || ch.equals("）")) return 4;
        else return 5;
    }

    static boolean isFunction(String str){
        String[] functions = {
                "sin","cos","tan","arcsin","arccos","arctan",
                "lg","ln","sh","ch","th",
        };
        int length = functions.length;
        for(int i = 0;i < length;i++)
            if(functions[i].equals(str)) return true;
        return false;
    }//优先级为2

    /* 判断一个字符串是否为计算表达式 */
    static boolean isExpression(String command){
        /* command在输入后就自动转换成小写形式了 */
        command = command.replaceAll(" ","");
        command = command.replaceAll("PI","").replaceAll("Eu","");
        command = command.replaceAll("\\d","");//移除command中的所有数字
        command = command.toLowerCase();//将command转换的字母全部转为小写
        String[] tokens = command.split("[+\\-*/%^!！()（）\\[\\]]");//以+-*/%^!！()（）[]这些符号分割字符串
        for(String token:tokens){
            if(token.isEmpty()) continue;
            //如果token全部由小写字母组成
            if(token.matches("[a-z]+")) {//判断是否由大小写字母组成：match("[a-zA-Z]+")
                if (!isFunction(token)) {
                    System.out.println(token + "不属于函数运算符！");
                    return false;
                }
            }
            else {
                System.out.println("无法识别\"" + token + "\"");
                return false;
            }
        }
        return true;
    }

    /* 判断一个字符是否是运算符 */
    static boolean isOperator(char ch){
        char[] operators = {
                '+','-','*','/','%','^','!', '！'
        };
        for (char operator : operators) if (ch == operator) return true;
        return false;
    }

    /* 显示所有变量及其赋值 */
    static void showAllVars(){
        boolean flag = false;
        System.out.println("进行赋值过的所有变量：");
        for(int i = 0;i < 26;i++){
            if(varSet_az_value[i] != null){
                System.out.printf("%c = ",'a' + i);
                System.out.println(varSet_az_value[i]);
                flag = true;
            }
        }
        for(int i = 0;i < 26;i++){
            if(varSet_AZ_value[i] != null){
                System.out.printf("%c = ",'A' + i);
                System.out.println(varSet_AZ_value[i]);
                flag = true;
            }
        }
        if(!flag){
            System.out.println("无变量被赋值。");
            logging("无变量被赋值。");
        }
    }

    /* 将list转String */
    static String listToString(List<Object> list){
        if(list.size() == 0) return null;
        String res = list.toString();
        //System.out.println(res);
        String sub = res.substring(1,res.length() - 1);
        /* 去除字符串中的逗号和空字符 */
        return sub.replaceAll("\\s","").replaceAll(","," ");
    }

    /* 计算阶乘 */
    static int fact(Object obj){
        int n = 1;
        int res = 1;
        if(obj instanceof Double) {
            n = (int) Math.round((double) obj);
        } else if(obj instanceof String) {
            n = Integer.parseInt((String) obj);
        } else if(obj instanceof Integer) {
            n = (int) obj;
        }
        for(int i = 1; i <= n; i++){
             res *= i;
        }
        return res;
    }

    /* 将一个数转为带π的式子 */
    static String toPIFraction(Object obj){
        boolean ifPositive = true;//是否是正数
        double num = 0;//传入的参数的值
        double coef;//转换成带π的式子中π的系数
        if(obj instanceof Integer){
            num = (int)((Integer)obj);
        }
        else if(obj instanceof Double){
            num = (Double)obj;
        }
        else if(obj instanceof String){
            num = Double.parseDouble((String) obj);
        }
        if(num == 0) return "0";//如果num为0，返回空字符串
        if(num < 0) {
            num = -num;//取绝对值
            ifPositive = false;//判断正负
        }
        coef = num / PI;
        int integerPart = (int)Math.floor(coef);//向下取整，整数部分
        double fractionalPart = coef - integerPart;//小数部分
        FractionConverter converter = new FractionConverter();
        int maxDenominator = 1000;//分母的最大值
        //正数
        if(ifPositive) {
            if (fractionalPart != 0) {
                if(integerPart != 0)
                    return (integerPart != 1 ? integerPart : "") + "π + (" + converter.simplifyFraction(fractionalPart, maxDenominator) + ")π";
                else
                    return "(" + converter.simplifyFraction(fractionalPart, maxDenominator) + ")π";
            }
            else
                return integerPart + "π";
        }
        //负数
        else {
            if (fractionalPart != 0)
                if(integerPart != 0)
                    return "-" + (integerPart != 1 ? integerPart : "") + "π - (" + converter.simplifyFraction(fractionalPart,maxDenominator) + ")π)";
                else
                    return "-(" + converter.simplifyFraction(fractionalPart,maxDenominator) + ")π";
            else
                return "-" + integerPart + "π";
        }
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
    /* 提交变量集 */
    static void submitVar(String varSet){
        int length = varSet.length();
        /* 以$为开头和结尾表示该字符串为变量集 */
        if(varSet.startsWith("$") && varSet.endsWith("$")){
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
               else if(ch == ' ' || ch == '\n' || ch == '\t'){
               }
               else {
                   System.out.println(varSet);
                   System.out.printf("在上面提交的变量集中%c不属于规定的变量名称，变量名称只能是大小写字母!",ch);
                   return;
               }
            }
        }
        else
            System.out.printf("变量集{%s}格式错误!",varSet);
    }

    /*
        计算表达式转逆波兰表达式的测试
        (3 + 5 % 6) * 7 + 6 / 3 - 4
            => 3 5 6 % + 7 * 6 3 / + 4 -

        ((a * c + 7) % b + 2) + c * (5 - d)
            => a c * 7 + b % 2 + c 5 d - * +

        (((a * b- 5) % c + 9) + d * 3 / e) + f % (g - h * 2 ) + 4
            => a b * 5 - c % 9 + d 3 * e / + f g h 2 * - % + 4 +

        (10 - (a * c - 5 / b) % d + 12) * 30 - 250 * e + f
            => 10 a c * 5 b / - d % - 12 + 30 * 250 e * - f +

        25 * a - ((340 * b - 1024) * 12 + 23 % c) - 7 * d / 6
            => 25 a * 340 b * 1024 - 12 * 23 c % + - 7 d * 6 / -

        [(3! ^ 2!) * 10 - 5 * (3 ^ 3 - 7) % (3! - 2!) + 9] + 5! ^ 2
            => 3 ! 2 ! ^ 10 * 5 3 3 ^ 7 - * 3 ! 2 ! - % - 9 + 5 ! 2 ^ +

        (2 * a^2 * b + 2 * a * b^2) - [2 * (a^2 * b - 1) + 3 * a * b^2 + 2]
            => 2 a 2 ^ * b * 2 a * b 2 ^ * + 2 a 2 ^ b * 1 - * 3 a * b 2 ^ * + 2 + -

        sin(6 * PI + cos(5 * PI / 2) - 1) + lg(tan(PI / 4)) - ln(2 * Eu)
            => 6 PI * 5 PI * 2 / cos + 1 - sin PI 4 / tan lg + 2 Eu * ln -

        [ln(3 * Eu + sin(3 * PI / 2) - cos(ln0 + PI/6))] - ch1 * sh2 - th(tan(PI /4)) - lg100
            => 3 Eu * 3 PI * 2 / sin + 0 ln PI 6 / + cos - ln 1 ch * 2 sh - PI 4 / tan th - 100 lg -

         [ln(
     */
    static List antiPolish(String expression){
        Stack stack = new Stack();
        ArrayList<Object> postfixNotation = new ArrayList<>();
        int length = expression.length();
        for(int i = 0;i < length;i++){
            char ch = expression.charAt(i);
            if(ch == ' ' || ch == '\t') {
            }

            /* 常量PI = Math.PI */
            else if(ch == 'P' && expression.charAt(i + 1) == 'I'){
                i++;
                postfixNotation.add("PI");
            }

            /* 常量e为欧拉数(Euler number),e = Math.E */
            else if(ch == 'E' && expression.charAt(i + 1) == 'u'){
                i++;
                postfixNotation.add("Eu");
            }

            /* 除PI和Eu外，连续出现两个字母判定为函数 */
            else if(Character.isLetter(ch) && i + 1 < length && Character.isLetter(expression.charAt(i + 1))){
                int flag = 0;
                String func1 = null,func2 = null,func3 = null,func5 = null;
                /* 所有函数都以小写处理 */
                if(i + 6 <= length) func5 = expression.substring(i,i + 6).toLowerCase();//arcsin、arccos、arctan
                if(i + 4 <= length) func3 = expression.substring(i,i + 4).toLowerCase();//arsh、arch、arth
                if(i + 3 <= length) func2 = expression.substring(i,i + 3).toLowerCase();//sin、cos、tan
                if(i + 2 <= length) func1 = expression.substring(i,i + 2).toLowerCase();//lg、ln、sh、ch、th
                if(func1 != null && (func1.equals("lg") || func1.equals("ln") || func1.equals("sh") || func1.equals("ch") || func1.equals("th")))
                    flag = 1;
                if(func2 != null && (func2.equals("sin") || func2.equals("cos") || func2.equals("tan")))
                    flag = 2;
                if(func3 != null && (func3.equals("arsh") || func3.equals("arch") || func3.equals("arth")))
                    flag = 3;
                if(func5 != null && (func5.equals("arcsin") || func5.equals("arccos") || func5.equals("arctan")))
                    flag = 5;
                if(flag != 0) {
                    /* 如果栈不为空 */
                    if (stack.size() != 0) {
                        String top = stack.peek().toString();
                        int level1 = 2;//扫描到的函数,默认函数的优先级为2
                        int level2 = getLevel(top);//栈顶的符号
                        /* 将优先级低于栈顶符号的符号入栈 */
                        if (level1 > level2 || top.equals("(") || top.equals("（") || top.equals("[")){}
                        else {//把优先级大于等于当前级别的符号全部出栈
                            while (level1 <= level2 && !top.equals("(") && !top.equals("（") && !top.equals("[")) {
                                postfixNotation.add(stack.pop());
                                if (stack.isEmpty()) break;
                                if(stack.peek() instanceof String)
                                    level2 = 2;
                                else
                                    level2 = getLevel(stack.peek().toString());
                                top = stack.peek().toString();
                            }
                        }
                    }
                    /*
                    测试对函数的识别
                    switch(flag){
                        case 1: System.out.println(func1); break;
                        case 2: System.out.println(func2); break;
                        case 3: System.out.println(func3); break;
                        case 5: System.out.println(func5); break;
                    }
                    */
                    i += flag;//索引移到函数最后一个字符
                    /* 入栈，如果栈空直接入栈 */
                    switch (flag){
                        case 5: stack.push(func5); break;
                        case 3: stack.push(func3); break;
                        case 2: stack.push(func2); break;
                        case 1: stack.push(func1); break;
                    }
                }
                else{
                    System.out.println("出现无法识别的符号！");
                    logging("\"" + expression.substring(i,i + flag + 1) + "\"中存在无法识别的符号！");
                    exit(0);
                }
            }

            /* 如果是变量 */
            else if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
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

            /* 如果是运算符 */
            else {
                if(stack.isEmpty()) stack.push(ch);//栈为空
                else if(ch == ')' || ch == '）') {
                    /* 一直出栈直到遇到'(' */
                    String top = stack.peek().toString();
                    while(!top.equals("(") && !top.equals("（")) {
                        postfixNotation.add(stack.pop());
                        top = stack.peek().toString();
                    }
                    stack.pop();
                }//中英文括号可以混合使用
                else if(ch == ']') {
                    /* 一直出栈直到遇到'[' */
                    while(!stack.peek().toString().equals("["))
                        postfixNotation.add(stack.pop());
                    stack.pop();
                }//方括号[]与()的作用相同
                else {
                    String top = stack.peek().toString();//栈顶的符号
                    int level1 = getLevel(Character.toString(ch));//扫描到的符号
                    int level2;//栈顶符号的优先级
                    if(stack.peek() instanceof String)
                        level2 = 2;
                    else
                        level2 = getLevel(top);
                    //System.out.println("level1:" + level1 + " " + "level2：" + level2 + " ch:" + ch);
                    /* 将优先级低于栈顶符号的符号入栈 */
                    if (level1 > level2 || top.equals("(") || top.equals("（") || top.equals("[")) {
                        stack.push(ch);
                    }
                    else{//把优先级大于等于当前级别的符号全部出栈
                        while (level1 <= level2 && !top.equals("(") && !top.equals("（") && !top.equals("[")) {
                            postfixNotation.add(stack.pop());
                            if (stack.isEmpty()) break;
                            if(stack.peek() instanceof String)
                                level2 = 2;
                            else
                                level2 = getLevel(stack.peek().toString());
                            top = stack.peek().toString();
                        }
                        stack.push(ch);//将当前扫描的符号入栈
                    }
                }
            }
            /*
                查看stack和antipolish两个链表中的所有元素
                System.out.println("stack:" + stack);
                System.out.println("antipolish:" + listToString(postfixNotation));
             */
        }

        /* 将栈中剩余符号pop */
        while(!stack.isEmpty()) {
            postfixNotation.add(stack.pop());
        }
        //将计算表达式转换为后缀表达式
        if(ifShowAntiPolish)
            System.out.println("转换为逆波兰表达式：" + listToString(postfixNotation));//将ArrayList result转换为字符串
        return postfixNotation;
    }

    /* 对逆波兰表达式进行求值 */
    static Object antiPolishEvaluate(ArrayList<Object> postfixNotation){
        LinkedList<Object> list= new LinkedList<>();
        LinkedList<Object> varTrack = new LinkedList<>();//用于跟踪变量，确定哪些值是变量
        int length = postfixNotation.size();
        if(length == 1) logging("无计算过程");
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
            else if(obj instanceof String){
                if(obj.equals("PI")) {
                    list.push(PI);//跟踪
                    varTrack.push(obj);//跟踪
                }
                else if(obj.equals("Eu")){
                    list.push(Eu);
                    varTrack.push(obj);//跟踪
                }
                /* sin、cos等等函数的处理 */
                else {
                    if(ifShowProcess) {
                        /* sin */
                        if (obj.equals("sin")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            if(Math.abs(Math.sin(value instanceof Double ? (Double)value : (Integer)value)) > 1e-15) {
                                list.push(Math.sin((Double) value));//push计算结果
                                varTrack.push(Math.sin((Double) value));//跟踪
                            }
                            //如果计算结果过小，将结果记作0
                            else {
                                list.push(0.0);//push计算结果
                                varTrack.push(0.0);//跟踪
                            }

                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("sin" + var + " = " + list.peek() + "   " + var + " = " + value);
                                logging("sin" + var + " = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("sin π" + " = " + list.peek() + "   π = " + PI);
                                    logging("sin π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("sin e" + " = " + list.peek() + "   e = " + toPIFraction(Eu));
                                    logging("sin e" + " = " + list.peek() + "   e = " + toPIFraction(Eu));
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("sin (" + toPIFraction(value) + ") = " + list.peek());
                                logging("sin (" + toPIFraction(value) + ") = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//sin

                        /* cos */
                        if (obj.equals("cos")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            if(Math.abs(Math.cos(value instanceof Double ? (Double)value : (Integer)value)) > 1e-15) {
                                list.push(Math.cos((Double) value));//push计算结果
                                varTrack.push(Math.cos((Double) value));//跟踪
                            }
                            //如果计算结果过小，将结果记作0
                            else {
                                list.push(0.0);//push计算结果
                                varTrack.push(0.0);//跟踪
                            }

                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("cos" + var + " = " + list.peek() + "   " + var + " = " + value);
                                logging("cos" + var + " = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("cos π" + " = " + list.peek() + "   π = " + PI);
                                    logging("cos π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("cos e" + " = " + list.peek() + "   e = " + toPIFraction(Eu));
                                    logging("cos e" + " = " + list.peek() + "   e = " + toPIFraction(Eu));
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("cos (" + toPIFraction(value) + ") = " + list.peek());
                                logging("cos (" + toPIFraction(value) + ") = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//cos


                    }
                }//输出计算过程
            }//处理函数
            /* 对象是字符的实例 */
            else if(obj instanceof Character){
                //System.out.println("Character:" + obj);
                char ch = (char)obj;
                if(ch >= 'a' && ch <=  'z'){
                    if(varSet_az_value[ch - 'a'] == null){
                        System.out.printf("变量%c未进行赋值！",ch);
                        return null;
                    }
                    list.push(varSet_az_value[ch - 'a']);
                    varTrack.push(ch);//跟踪
                }
                else if(ch >= 'A' && ch <= 'Z'){
                    if(varSet_AZ_value[ch - 'A'] == null){
                        System.out.printf("变量%c未进行赋值！",ch);
                        return null;
                    }
                    list.push(varSet_AZ_value[ch - 'A']);
                    varTrack.push(ch);//跟踪
                }
                else if(isOperator(ch)){
                    if(ch == '!' || ch == '！'){
                        Object value = list.pop();//获取计算值
                        Object var = varTrack.pop();//追踪
                        list.push(fact(value));//push计算结果
                        varTrack.push(fact(value));//跟踪
                        /* 输入计算过程 */
                        if(ifShowProcess){
                            if(var instanceof Character){
                                System.out.println(var + "(" + value + ")" + ch + " = " + list.peek());
                                logging(var + "(" + value + ")" + ch + " = " + list.peek());
                            }
                            /* 常量Math.PI或Math.E */
                            else if(var instanceof String){
                                if(var.equals("PI")) {
                                    System.out.println("π" + ch + " = " + list.peek() + "   π = " + PI);
                                    logging("π" + ch + " = " + list.peek() + "  π = " + PI);
                                }
                                else if(var.equals("Eu")){
                                    System.out.println("e" + ch + " = " + list.peek() + "   e = " + Eu);
                                    logging("e" + ch + " = " + list.peek() + "   e = " + Eu);
                                }
                                else{
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }
                            else {
                                System.out.println(value + "" + ch + " = " + list.peek());
                                logging(value + "" + ch + " = " + list.peek());
                            }
                        }
                    }
                    else {
                        int flag = 0;//用于判断value1和value2的类型
                        Object value1, value2;
                        value1 = list.pop();//权值1，Integer为1，Double为0
                        value2 = list.pop();//权值2，Integer为1，Double为0
                        Object obj1, obj2;
                        obj1 = varTrack.pop();//跟踪
                        obj2 = varTrack.pop();//跟踪
                        if (value1 instanceof Integer)
                            flag += 1;
                        if (value2 instanceof Integer)
                            flag += 2;
                        //System.out.println("flag = " + flag);
                        /*
                            LinkedList list = new LinkedList();
                            list.push(Double.parseDouble("3.14"));
                            Object var = list.pop();
                            list.push(((Double)var).doubleValue());
                            System.out.println(listToString(list));
                        */
                        switch (ch) {
                            case '+': {
                                switch (flag) {
                                    case 0: {
                                        list.push((Double) value2 + (Double) value1);
                                        break;
                                    }//value1：Double value2：Double
                                    case 1: {
                                        list.push((Double) value2 + (Integer) value1);
                                        break;
                                    }//value1：Integer value2：Double
                                    case 2: {
                                        list.push((Integer) value2 + (Double) value1);
                                        break;
                                    }//value1: Double value2：Integer
                                    case 3: {
                                        list.push((Integer) value2 + (Integer) value1);
                                        break;
                                    }//value：Integer value2：Integer
                                }
                                break;
                            }
                            case '-': {
                                switch (flag) {
                                    case 0: {
                                        list.push((Double) value2 - (Double) value1);
                                        break;
                                    }//value1：Double value2：Double
                                    case 1: {
                                        list.push((Double) value2 - (Integer) value1);
                                        break;
                                    }//value1：Integer value2：Double
                                    case 2: {
                                        list.push((Integer) value2 - (Double) value1);
                                        break;
                                    }//value1: Double value2：Integer
                                    case 3: {
                                        list.push((Integer) value2 - (Integer) value1);
                                        break;
                                    }//value：Integer value2：Integer
                                }
                                break;
                            }
                            case '*': {
                                switch (flag) {
                                    case 0: {
                                        list.push((Double) value2 * (Double) value1);
                                        break;
                                    }//value1：Double value2：Double
                                    case 1: {
                                        list.push((Double) value2 * (Integer) value1);
                                        break;
                                    }//value1：Integer value2：Double
                                    case 2: {
                                        list.push((Integer) value2 * (Double) value1);
                                        break;
                                    }//value1: Double value2：Integer
                                    case 3: {
                                        list.push((Integer) value2 * (Integer) value1);
                                        break;
                                    }//value：Integer value2：Integer
                                }
                                break;
                            }
                            case '/': {
                                switch (flag) {
                                    case 0: {
                                        list.push((Double) value2 / (Double) value1);
                                        break;
                                    }//value1：Double value2：Double
                                    case 1: {
                                        list.push((Double) value2 / (Integer) value1);
                                        break;
                                    }//value1：Integer value2：Double
                                    case 2: {
                                        list.push((Integer) value2 / (Double) value1);
                                        break;
                                    }//value1: Double value2：Integer
                                    case 3: {
                                        list.push((Integer) value2 / (Integer) value1);
                                        break;
                                    }//value：Integer value2：Integer
                                }
                                break;
                            }
                            case '%': {
                                switch (flag) {
                                    case 0: {
                                        list.push((Double) value2 / (Double) value1);
                                        break;
                                    }//value1：Double value2：Double
                                    case 1: {
                                        list.push((Double) value2 / (Integer) value1);
                                        break;
                                    }//value1：Integer value2：Double
                                    case 2: {
                                        list.push((Integer) value2 / (Double) value1);
                                        break;
                                    }//value1: Double value2：Integer
                                    case 3: {
                                        list.push((Integer) value2 % (Integer) value1);
                                        break;
                                    }//value：Integer value2：Integer
                                }
                                break;
                            }
                            case '^': {
                                switch (flag) {
                                    case 0: {
                                        list.push(Math.pow((Double) value2, (Double) value1));
                                        break;
                                    }//value1：Double value2：Double
                                    case 1: {
                                        list.push(Math.pow((Double) value2, (Integer) value1));
                                        break;
                                    }//value1：Integer value2：Double
                                    case 2: {
                                        list.push(Math.pow((Integer) value2, (Double) value1));
                                        break;
                                    }//value1: Double value2：Integer
                                    case 3: {
                                        list.push((int) Math.pow((Integer) value2, (Integer) value1));
                                        break;
                                    }//value：Integer value2：Integer
                                }
                                break;
                            }
                        }
                        varTrack.push(list.peek());//跟踪每一步计算求得的值
                        /* 输入计算过程 */
                        if (ifShowProcess) {
                            int status = 0;
                            if (obj1 instanceof Character) status += 1;//权值1
                            if (obj2 instanceof Character) status += 2;//权值2
                            switch (status) {
                                case 0: {
                                    System.out.print(value2 + " " + ch + " " + value1 + " = ");
                                    logging(value2 + " " + ch + " " + value1 + " = " + list.peek());
                                    break;
                                }
                                case 1: {
                                    System.out.print(value2 + " " + ch + " " + obj1 + "(" + value1 + ")" + " = ");
                                    logging(value2 + " " + ch + " " + obj1 + "(" + value1 + ")" + " = " + list.peek());
                                    break;
                                }
                                case 2: {
                                    System.out.print(obj2 + "(" + value2 + ")" + " " + ch + " " + value1 + " = ");
                                    logging(obj2 + "(" + value2 + ")" + " " + ch + " " + value1 + " = " + list.peek());
                                    break;
                                }
                                case 3: {
                                    System.out.print(obj2 + "(" + value2 + ")" + " " + ch + " " + obj1 + "(" + value1 + ")" + " = ");
                                    logging(obj2 + "(" + value2 + ")" + " " + ch + " " + obj1 + "(" + value1 + ")" + " = " + list.peek());
                                    break;
                                }
                            }
                            System.out.println(list.peek());
                        }
                    }
                }
                else {
                    System.out.printf("无法找到%c的定义\n",ch);
                    logging("无法找到" + ch + "的定义");
                    return null;
                }
            }
        }
        if(list.size() == 0){
            System.out.printf("逆波兰表达式\"%s\"存在错误!",listToString(postfixNotation));
            logging("逆波兰表达式\"" + listToString(postfixNotation) + "\"存在错误！");
            return null;
        }
        return list.pop();//将最后求得的值返回
    }

    static String inputVarSet(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入变量集($开启和结束输入)：");
        String varSet = "";
        String input = scanner.nextLine();
        if(!input.startsWith("$")){
            System.out.println("格式错误，变量集的书写必须以$开头并以$结束.");
            return null;
        }
        while(!input.endsWith("$")){
            varSet = varSet.concat("\n" + input);
            input = scanner.nextLine();
        }
        if(varSet.length() > 0)
            varSet = varSet.concat("\n" + input);
        else
            varSet = varSet.concat(input);
        System.out.println(varSet);
        return varSet;
    }

    static void ls(){
        for(String expression: mathExpression)
            System.out.println(expression);
    }

    static void calculate(){
        Scanner userInput = new Scanner(System.in);
        System.out.print("请输入数学计算表达式：");
        String expression = userInput.nextLine();//记录输入过的计算表达式
        /* 获取当前时间 */
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");// 定义时间格式
        String currentTimeString = currentTime.format(formatter);// 将当前时间格式化为字符串
        mathExpression.add(currentTimeString + "   " + expression);//记录输入的计算表达式
        logging(new Date() + " 计算数学表达式：" + expression);
        logging("计算过程：");
        System.out.println("- - - - - - - - - - - - - - - - - - - -");
        System.out.println("计算过程：");
        ArrayList  postfixNotation = (ArrayList) antiPolish(expression);
        Object result = antiPolishEvaluate(postfixNotation);
        if(result != null)
            System.out.println("结果是：" + result);
    }

    static void directCaculate(String expression){
        /* 获取当前时间 */
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");// 定义时间格式
        String currentTimeString = currentTime.format(formatter);// 将当前时间格式化为字符串
        mathExpression.add(currentTimeString + "   " + expression);//记录输入的计算表达式
        logging(new Date() + " 计算数学表达式：" + expression);
        logging("计算过程：");
        System.out.println("- - - - - - - - - - - - - - - - - - - -");
        System.out.println("计算过程：");
        ArrayList  postfixNotation = (ArrayList) antiPolish(expression);
        Object result = antiPolishEvaluate(postfixNotation);
        if(result != null)
            System.out.println("结果是：" + result);
    }

    static void help(){
        System.out.println("exit   退出程序");
        System.out.println("adcg   添加或设置变量，变量名只能是大小写字母，变量未赋值时初始为null，程序根据你提交的变量集对对应的变量进行赋值");
        System.out.println("read   将根据你输入的指定路径读取变量集并给对应的变量进行赋值");
        System.out.println("save   将根据你输入的指定路径读取变量集并给对应的变量进行赋值");
        System.out.println("var    显示所有已经进行赋值过的变量");
        System.out.println("""
                antipo 输入计算表达式进行计算，程序将会给出它的每一步计算过程以及计算结果，计算支持下列运算符：
                       加法+、减法-、乘法*、除法/、取余运算%、幂运算^、阶乘运算(!/！)、
                       正弦sin、余弦cos、正切tan、双曲正弦sh、双曲余弦ch、双曲正切th、
                       反正弦arcsin、反余弦arccos、反正切arctan、以10为底的对数lg、以e为底的对数ln
                       对于sin、cos、tan等等这些函数运算符，不限制大小写，程序会自动将字母统一转换成小写再处理
                       计算支持两大常数：圆周率π用PI表示、自然常数e用Eu表示
                       支持使用中英文圆括号()/（）、方括号[]、来改变运算顺序，注意中英文括号可以混合使用，例如左边为中文括号右边为英文括号。
                """);
        System.out.println("""
                ls     列出所有之前输入的计算表达式，以时间+计算表达式的形式呈现，例如：
                       11:14:38   sin PI + cos PI
                       11:15:59   sin(PI / 2 + 3.5 * PI) + cos(PI - 0.25 * PI
                """);
        System.out.println("""
                变量集   变量集的编写需要以$开始并以$结尾，中间赋值语句的书写格式为：变量名 = 值
                       在变量集中空格和回车等等一些空字符会被自动忽略，你可以自由使用空字符来使得你的变量集简洁、美观。
                       使用read命令可以直接编写变量集并提交给程序，save命令可以将程序中所有赋值的变量保存为.txt文件
                       变量集的默认格式为.txt，你只需要按变量集的编写规则编写文本并保存为.txt文件即可通过程序去读取该变量集。
                       下面是一个简单的变量集示例，它给变量a、b、c、d依次赋值为1、2、3、4：
                       $a = 1 b = 2 c = 3 d = 4$
                """);
        System.out.println("文件信息  程序所有产生的数据文件都在\"" + new File(".").getAbsolutePath() + "\"文件夹中\n" +
        "        在\"" + logPath.getAbsolutePath() + "\"可以查看您的所有使用日志。\n");
    }

    static void console() throws IOException {
        Scanner userInput = new Scanner(System.in);
        int option = 6;
        while(option != 0) {
            if(ifshowMenu) {
                System.out.println("                菜单");
                System.out.println("退出 - - - - - - - - - - - - - - - exit");
                System.out.println("添加或修改变量 - - - - - - - - - - - adcg");
                System.out.println("读取变量集- - - - - - - - - - - - - read");
                System.out.println("保存当前变量集 - - - - - - - - - - - save");
                System.out.println("查看所有变量 - - - - - - - - - - - - var");
                System.out.println("计算 - - - - - - - - - - - - - - - antipo");
                System.out.println("查看所有输入过的计算表达式 - - - - - - -ls");
                System.out.println("注：所有命令不限制字母的大小写");
            }
            System.out.print(inputAnchor);
            String command;
            command = userInput.nextLine();
            option = switch (command) {
                case "exit" -> 0;
                case "adcg" -> 1;
                case "read" -> 2;
                case "save" -> 3;
                case "var" -> 4;
                case "antipo" -> 5;
                case "ls" -> 6;
                case "help" -> 7;
                default -> 8;
            };
            /*
            try {
                option = userInput.nextInt();
            }catch (InputMismatchException ex){
                System.out.println("输入错误，输入必须为数字选项！");
                userInput.nextLine();
                System.out.print(inputAnchor);
                option = userInput.nextInt();
            }
            */
            switch (option){
                case 0: {
                    logging(new Date() + " 退出");
                    logging("*****************************************************************\n");
                    userInput.close();
                    System.out.println("感谢您的使用！在\"" + logPath.getAbsolutePath() + "\"可以查看您的所有使用日志。");
                    break;
                }
                case 1:{
                    String varSet = inputVarSet();
                    logging(new Date() + " 添加或读取变量：");
                    logging(varSet);
                    if(varSet == null) {
                        System.out.println("已退出变量集的输入.");
                        break;
                    }
                    submitVar(varSet);
                    System.out.println("提交成功!");
                    break;
                }
                case 2:{
                    read();
                    break;
                }
                case 3:{
                    save();
                    break;
                }
                case 4:{
                    logging(new Date() + " 查看所有变量：\n" + varSetToString());
                    showAllVars();
                    break;
                }
                case 5:{
                    calculate();
                    break;
                }
                case 6:{
                    ls();
                    break;
                }
                case 7:{
                    logging(new Date() + " 查看帮助信息");
                    help();
                    break;
                }
                default: {
                    /* 如果输入的命令是计算表达式，直接进行计算 */
                    if(isExpression(command))
                        directCaculate(command);
                    else {
                        System.out.println("无法识别命令！");
                        logging(new Date() + "无法识别命令：" + command);
                    }
                }
            }
            if(ifshowMenu) {
                System.out.print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
                System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            }
            logging("");
        }
    }

    public static void main(String[] args) throws IOException {
        Initialize();//变量初始化
        console();
        /*
        测试将数字转为带π的式子
        String str = Double.toString(8.5 * PI);
        System.out.println(toPIFraction(str));//0.3π
        System.out.println(toPIFraction(2.4 * PI));
        System.out.println(toPIFraction(5/12.0 * PI));
        System.out.println(toPIFraction(17.0/9 * PI));
        System.out.println(toPIFraction(3.9 * PI));
         */

    }
}
