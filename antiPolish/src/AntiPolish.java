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
    static int maxDenominator = 100000;//将一个数转为分式时分母的最大取值

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

    /* 输入变量集 */
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
        else if(ch.equals("!") || ch.equals("！")) return 4;
        else if(ch.equals("(") || ch.equals(")") || ch.equals("（") || ch.equals("）")
                || ch.equals("[") || ch.equals("]")) return 5;
        else return 5;
    }

    static boolean antiPolishTest(String expression){
        Stack stack = new Stack();
        int length = expression.length();
        for(int i = 0;i < length;i++){
            char ch = expression.charAt(i);
            if(ch == ' ' || ch == '\t') {
            }

            /* 常量PI = Math.PI */
            else if(ch == 'P' && expression.charAt(i + 1) == 'I'){
                i++;
            }

            /* 常量e为欧拉数(Euler number),e = Math.E */
            else if(ch == 'E' && expression.charAt(i + 1) == 'u'){
                i++;
            }

            /* 除PI和Eu外，连续出现两个字母判定为函数 */
            else if(Character.isLetter(ch) && i + 1 < length && Character.isLetter(expression.charAt(i + 1))){
                int flag = 0;
                String func1 = null,func2 = null,func3 = null,func5 = null;
                /* 所有函数都以小写处理 */
                if(i + 6 <= length) func5 = expression.substring(i,i + 6).toLowerCase();//arcsin、arccos、arctan
                if(i + 4 <= length) func3 = expression.substring(i,i + 4).toLowerCase();//arsh、arch、arth、pify、frac
                if(i + 3 <= length) func2 = expression.substring(i,i + 3).toLowerCase();//sin、cos、tan、abs、exp
                if(i + 2 <= length) func1 = expression.substring(i,i + 2).toLowerCase();//lg、ln、sh、ch、th
                if(func1 != null && (func1.equals("lg") || func1.equals("ln") || func1.equals("sh") || func1.equals("ch") || func1.equals("th")))
                    flag = 1;
                if(func2 != null && (func2.equals("sin") || func2.equals("cos") || func2.equals("tan"))
                        || func2.equals("abs") || func2.equals("exp"))
                    flag = 2;
                if(func3 != null && (func3.equals("arsh") || func3.equals("arch") || func3.equals("arth")
                        || func3.equals("pify") || func3.equals("frac")))
                    flag = 3;
                if(func5 != null && (func5.equals("arcsin") || func5.equals("arccos") || func5.equals("arctan")))
                    flag = 5;
                if(flag != 0) {
                    /* 如果栈不为空 */
                    if (stack.size() != 0) {
                        String top = stack.peek().toString();
                        int level1 = 4;//扫描到的函数,默认函数的优先级为4
                        int level2 = getLevel(top);//栈顶的符号
                        /* 将优先级低于栈顶符号的符号入栈 */
                        if (level1 > level2 || top.equals("(") || top.equals("（") || top.equals("[")){}
                        else {//把优先级大于等于当前级别的符号全部出栈
                            while (level1 <= level2 && !top.equals("(") && !top.equals("（") && !top.equals("[")) {
                                if (stack.isEmpty()) break;
                                if(stack.peek() instanceof String)
                                    level2 = 4;
                                else
                                    level2 = getLevel(stack.peek().toString());
                                top = stack.peek().toString();
                            }
                        }
                    }
                    i += flag;//索引移到函数最后一个字符
                    /* 入栈，如果栈空直接入栈 */
                    switch (flag){
                        case 5: stack.push(func5); break;
                        case 3: stack.push(func3); break;
                        case 2: stack.push(func2); break;
                        case 1: stack.push(func1); break;
                    }
                }
                else
                    return false;
            }

            /* 如果是变量 */
            else if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
            }

            /* 如果是数字 */
            else if(ch >= '0' && ch <= '9'){
                int j = i;
                char num;
                do{
                    j++;
                    if(j >= length) break;
                    num = expression.charAt(j);
                }while(num >= '0' && num <= '9' || num == '.');
                i = j - 1;//将下一个扫描的位置移到数字后一个字符
            }

            /* 如果是运算符 */
            else if(isOperator(ch)){
                if(stack.isEmpty()) stack.push(ch);//栈为空
                else if(ch == ')' || ch == '）') {
                    /* 一直出栈直到遇到'(' */
                    String top = stack.peek().toString();
                    while(!top.equals("(") && !top.equals("（")) {
                        top = stack.peek().toString();
                    }
                    stack.pop();//pop掉(或（
                }//中英文括号可以混合使用
                else if(ch == ']') {
                    /* 一直出栈直到遇到'[' */
                    while(!stack.peek().toString().equals("[")){
                    }
                    stack.pop();//pop掉[
                }//方括号[]与()的作用相同
                else {
                    String top = stack.peek().toString();//栈顶的符号
                    int level1 = getLevel(Character.toString(ch));//扫描到的符号
                    int level2;//栈顶符号的优先级
                    if(stack.peek() instanceof String)
                        level2 = 4;//函数的优先级
                    else
                        level2 = getLevel(top);
                    //System.out.println("level1:" + level1 + " " + "level2：" + level2 + " ch:" + ch);
                    /* 将优先级低于栈顶符号的符号入栈 */
                    if (level1 > level2 || top.equals("(") || top.equals("（") || top.equals("[")) {
                        stack.push(ch);
                    }
                    else{//把优先级大于等于当前级别的符号全部出栈
                        while (level1 <= level2 && !top.equals("(") && !top.equals("（") && !top.equals("[")) {
                            if (stack.isEmpty()) break;
                            if(stack.peek() instanceof String)
                                level2 = 4;//函数的优先级
                            else
                                level2 = getLevel(stack.peek().toString());
                            top = stack.peek().toString();
                        }
                        stack.push(ch);//将当前扫描的符号入栈
                    }
                }
            }

            /* 未知字符 */
            else
                return false;
        }//遍历计算表达式

        /* 将栈中剩余符号pop */
        while(!stack.isEmpty())
           stack.pop();
       return true;
    }

    static boolean isFunction(String str){
        String[] functions = {
                "sin","cos","tan","abs","exp",
                "arsh","arch","arth","pify","frac",
                "arcsin","arccos","arctan",
                "lg","ln","sh","ch","th",
        };
        int length = functions.length;
        for(int i = 0;i < length;i++)
            if(functions[i].equals(str)) return true;
        return false;
    }//优先级为4

    /* 判断一个字符串是否为计算表达式 */
    static boolean isExpression(String command){
        command = command.replace(" ","");
        command = command.replace("PI","").replace("Eu","");
        command = command.replaceAll("\\d","");//移除command中的所有数字
        command = command.replace(".","");//移除小数点
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
                '+','-','*','/','%','^','!', '！','(',')','（','）','[',']'
        };
        for (char operator : operators) if (ch == operator) return true;
        return false;
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

    /*  计算阶乘,n! = 1*2*3*...*n  */
    static int fact(Object obj){
        int n = 1;
        int res = 1;
        if(obj instanceof Double)
            n = (int) Math.round((double) obj);
        else if(obj instanceof Integer)
            n = (int) obj;
        else if(obj instanceof String)
            n = Integer.parseInt((String) obj);
        for(int i = 1; i <= n; i++){
             res *= i;
        }
        return res;
    }

    /* sin，三角正弦函数 */
    static double sin(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.sin(x);
    }

    /* cos，三角余弦函数 */
    static double cos(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.cos(x);
    }

    /* tan，三角正切函数 */
    static double tan(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.tan(x);
    }

    /* abs,取绝对值 */
    static double abs(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.abs(x);
    }

    /*  exp,计算e的x次方，exp(x) = e ^ x  */
    static double exp(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.exp(x);
    }//exp

    /*  sh,计算双曲正弦,sh x = (e^x - 1/e^x) / 2  */
    static double sh(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.sinh(x);//sh x = (e^x - 1/e^x) / 2
    }//sh

    /*  ch，计算双曲余弦,ch x = (e^x + 1/e^x) / 2  */
    static double ch(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.cosh(x);//ch x = (e^x + 1/e^x) / 2
    }//ch

    /*  th,计算双曲正切，th = sh / ch = (e^x - 1/e^x)/(e^x + 1/e^x)  */
    static double th(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.tanh(x);//th = sh / ch = (e^x - 1/e^x)/(e^x + 1/e^x)
    }//th

    /*  log10，计算以10为底的对数  */
    static double lg(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.log10(x);
    }//lg

    /*  ln，计算以自然常数e为底的对数  */
    static double ln(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.log(x);
    }//ln

    /*  arcsin,反三角正弦函数  */
    static double arcsin(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.asin(x);
    }//arcsin


    /*  arccos,反三角余弦函数  */
    static double arccos(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.asin(x);
    }//arccos

    /*  arctan,反三角正切函数  */
    static double arctan(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.atan(x);
    }//arctan

    /* 将一个数转为以π为因子的式子 */
    static String pify(Object obj){
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

        //正数
        if(ifPositive) {
            if (fractionalPart != 0) {
                if(integerPart != 0)
                    return (integerPart != 1 ? integerPart : "") + "π + (" + converter.simplifyFraction(fractionalPart, maxDenominator) + ")π";
                else
                    return "(" + converter.simplifyFraction(fractionalPart, maxDenominator) + ")π";
            }
            else
                return (integerPart != 1 ? integerPart : "") + "π";
        }

        //负数
        else {
            if (fractionalPart != 0)
                if(integerPart != 0)
                    return "-" + (integerPart != 1 ? integerPart : "") + "π - (" + converter.simplifyFraction(fractionalPart,maxDenominator) + ")π)";
                else
                    return "-(" + converter.simplifyFraction(fractionalPart,maxDenominator) + ")π";
            else
                return "-" + (integerPart != 1 ? integerPart : "") + "π";
        }
    }

    /* frac,将一个数转换成分式 */
    static String frac(Object obj,int maxDenominator){
        double num = 0;
        if(obj instanceof Integer)
            num = (Integer)obj;
        else if(obj instanceof Double)
            num = (Double)obj;
        else if(obj instanceof String)
            num = Double.parseDouble((String) obj);

        double EPSILON = 1e-15; // 定义一个极小值作为误差限制

        double error = Math.abs(num); // 初始化误差
        int bestNumerator = 0; // 存储最佳近似的分子
        int bestDenominator = 1; // 存储最佳近似的分母

        for (int i = 1; i <= maxDenominator; i++) {
            int currentNumerator = (int) Math.round(num * i); // 计算当前分子
            double currentError = Math.abs(num - (double) currentNumerator / i); // 计算当前误差

            if (currentError < error - EPSILON) {
                // 如果当前误差小于之前的误差，更新最佳近似的分子、分母和误差
                error = currentError;
                bestNumerator = currentNumerator;
                bestDenominator = i;
            }
        }

        return bestNumerator + "/" + bestDenominator;//返回转换得到的分式
    }//frac

    /*  arsh,反双曲正弦函数  */
    static double arsh(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return ln(x + Math.sqrt(x * x + 1));//arsh = ln(x + sqrt(x ^ 2 + 1));
    }//arsh

    /*  arch,反双曲余弦函数  */
    static double arch(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return ln(x + Math.sqrt(x * x - 1));//arsh = ln(x + sqrt(x ^ 2 - 1));
    }//arch

    /*  arth,反三角正切函数  */
    static double arth(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return 0.5 * ln((1 + x) / (1 - x));//arth = ln[(1+x)/(1-x)] / 2
    }//arth

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
    static List antiPolish(String expression) throws Exception{
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
                if(i + 4 <= length) func3 = expression.substring(i,i + 4).toLowerCase();//arsh、arch、arth、pify、frac
                if(i + 3 <= length) func2 = expression.substring(i,i + 3).toLowerCase();//sin、cos、tan、abs、exp
                if(i + 2 <= length) func1 = expression.substring(i,i + 2).toLowerCase();//lg、ln、sh、ch、th
                if(func1 != null && (func1.equals("lg") || func1.equals("ln") || func1.equals("sh") || func1.equals("ch") || func1.equals("th")))
                    flag = 1;
                if(func2 != null && (func2.equals("sin") || func2.equals("cos") || func2.equals("tan"))
                        || func2.equals("abs") || func2.equals("exp"))
                    flag = 2;
                if(func3 != null && (func3.equals("arsh") || func3.equals("arch") || func3.equals("arth")
                        || func3.equals("pify") || func3.equals("frac")))
                    flag = 3;
                if(func5 != null && (func5.equals("arcsin") || func5.equals("arccos") || func5.equals("arctan")))
                    flag = 5;
                if(flag != 0) {
                    /* 如果栈不为空 */
                    if (stack.size() != 0) {
                        String top = stack.peek().toString();
                        int level1 = 4;//扫描到的函数,默认函数的优先级为4
                        int level2 = getLevel(top);//栈顶的符号
                        /* 将优先级低于栈顶符号的符号入栈 */
                        if (level1 > level2 || top.equals("(") || top.equals("（") || top.equals("[")){}
                        else {//把优先级大于等于当前级别的符号全部出栈
                            while (level1 <= level2 && !top.equals("(") && !top.equals("（") && !top.equals("[")) {
                                postfixNotation.add(stack.pop());
                                if (stack.isEmpty()) break;
                                if(stack.peek() instanceof String)
                                    level2 = 4;
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
                    stack.pop();//pop掉(或（
                }//中英文括号可以混合使用
                else if(ch == ']') {
                    /* 一直出栈直到遇到'[' */
                    while(!stack.peek().toString().equals("["))
                        postfixNotation.add(stack.pop());
                    stack.pop();//pop掉[
                }//方括号[]与()的作用相同
                else {
                    String top = stack.peek().toString();//栈顶的符号
                    int level1 = getLevel(Character.toString(ch));//扫描到的符号
                    int level2;//栈顶符号的优先级
                    if(stack.peek() instanceof String)
                        level2 = 4;//函数的优先级
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
                                level2 = 4;//函数的优先级
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
        }//遍历计算表达式

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
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( sin(value) - Math.round(sin(value)) )< 1e-15 ){
                                double value_round = Math.round(sin(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            else {
                                list.push(sin(value));//push计算结果
                                varTrack.push(sin(value));//跟踪
                            }

                            String value_PI = pify(value);//以π为因子的值
                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("sin" + var + " = " + list.peek() + "   " + var + "=" + value + "   " + var + "=" + value_PI);
                                logging("sin" + var + " = " + list.peek() + "   " + var + "=" + value + "   " + var + "=" + value+PI);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("sin π" + " = " + list.peek() + "   π = " + PI);
                                    logging("sin π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("sin e" + " = " + list.peek() + "   e = " + pify(Eu));
                                    logging("sin e" + " = " + list.peek() + "   e = " + pify(Eu));
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("sin (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                logging("sin (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                            }//参数为double或int数值类型的处理
                        }//sin

                        /* cos */
                        else if (obj.equals("cos")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( cos(value) - Math.round(cos(value)) ) < 1e-15 ){
                                double value_round = Math.round(cos(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            else {
                                list.push(cos(value));//push计算结果
                                varTrack.push(cos(value));//跟踪
                            }

                            String value_PI = pify(value);//以π为因子的值
                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("cos" + var + " = " + list.peek() + "   " + var + " = " + value + "   " + var + "=" + value+PI);
                                logging("cos" + var + " = " + list.peek() + "   " + var + " = " + value + "   " + var + "=" + value+PI);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("cos π" + " = " + list.peek() + "   π = " + PI);
                                    logging("cos π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("cos e" + " = " + list.peek() + "   e = " + pify(Eu));
                                    logging("cos e" + " = " + list.peek() + "   e = " + pify(Eu));
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("cos (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                logging("cos (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                            }//参数为double或int数值类型的处理
                        }//cos

                        /* tan */
                        else if (obj.equals("tan")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( tan(value) - Math.round(tan(value)) ) < 1e-15 ){
                                double value_round = Math.round(tan(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            else {
                                list.push(tan(value));//push计算结果
                                varTrack.push(tan(value));//跟踪
                            }

                            String value_PI = pify(value);//以π为因子的值
                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("tan" + var + " = " + list.peek() + "   " + var + " = " + value);
                                logging("tan" + var + " = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("tan π" + " = " + list.peek() + "   π = " + PI);
                                    logging("tan π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("tan e" + " = " + list.peek() + "   e = " + pify(Eu));
                                    logging("tan e" + " = " + list.peek() + "   e = " + pify(Eu));
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("tan (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                logging("tan (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                            }//参数为double或int数值类型的处理
                        }//tan

                        /* abs */
                        else if (obj.equals("abs")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( abs(value) - Math.round(abs(value)) ) < 1e-15 ){
                                double value_round = Math.round(abs(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            else {
                                list.push(abs(value));//push计算结果
                                varTrack.push(abs(value));//跟踪
                            }

                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("|" + var + "| = " + list.peek() + "   " + var + " = " + value);
                                logging("|" + var + "| = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("|π|" + " = " + list.peek() + "   π = " + PI);
                                    logging("|π|" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("|e|" + " = " + list.peek() + "   e = " + Eu);
                                    logging("|e|" + " = " + list.peek() + "   e = " + Eu);
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("|" + value + "| = " + list.peek());
                                logging("|" + value + "| = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//abs

                        /* exp */
                        else if (obj.equals("exp")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( exp(value) - Math.round(exp(value)) ) < 1e-15 ){
                                double value_round = Math.round(exp(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            else {
                                list.push(exp(value));//push计算结果
                                varTrack.push(exp(value));//跟踪
                            }

                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("exp" + var + " = " + list.peek() + "   " + var + " = " + value);
                                logging("exp" + var + " = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("exp π" + " = " + list.peek() + "   π = " + PI);
                                    logging("exp π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("exp e" + " = " + list.peek() + "   e = " + Eu);
                                    logging("exp e" + " = " + list.peek() + "   e = " + Eu);
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("exp (" + value + ") = " + list.peek());
                                logging("exp (" + value + ") = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//exp

                        /* sh */
                        else if (obj.equals("sh")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( sh(value) - Math.round(sh(value)) ) < 1e-15 ){
                                double value_round = Math.round(sh(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            else {
                                list.push(sh(value));//push计算结果
                                varTrack.push(sh(value));//跟踪
                            }

                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("sh" + var + " = " + list.peek() + "   " + var + " = " + value);
                                logging("sh" + var + " = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("sh π" + " = " + list.peek() + "   π = " + PI);
                                    logging("sh π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("sh e" + " = " + list.peek() + "   e = " + Eu);
                                    logging("sh e" + " = " + list.peek() + "   e = " + Eu);
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("sh (" + value + ") = " + list.peek());
                                logging("sh (" + value + ") = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//sh

                        /* ch */
                        else if (obj.equals("ch")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( ch(value) - Math.round(ch(value)) ) < 1e-15 ){
                                double value_round = Math.round(ch(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            else {
                                list.push(ch(value));//push计算结果
                                varTrack.push(ch(value));//跟踪
                            }

                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("ch" + var + " = " + list.peek() + "   " + var + " = " + value);
                                logging("ch" + var + " = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("ch π" + " = " + list.peek() + "   π = " + PI);
                                    logging("ch π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("ch e" + " = " + list.peek() + "   e = " + Eu);
                                    logging("ch e" + " = " + list.peek() + "   e = " + Eu);
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("ch (" + value + ") = " + list.peek());
                                logging("ch (" + value + ") = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//ch

                        /* th */
                        else if (obj.equals("th")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( th(value) - Math.round(th(value)) ) < 1e-15 ){
                                double value_round = Math.round(th(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            else {
                                list.push(th(value));//push计算结果
                                varTrack.push(th(value));//跟踪
                            }

                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("th" + var + " = " + list.peek() + "   " + var + " = " + value);
                                logging("th" + var + " = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("th π" + " = " + list.peek() + "   π = " + PI);
                                    logging("th π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("th e" + " = " + list.peek() + "   e = " + Eu);
                                    logging("th e" + " = " + list.peek() + "   e = " + Eu);
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("th (" + value + ") = " + list.peek());
                                logging("th (" + value + ") = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//th

                        /* lg */
                        else if (obj.equals("lg")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( lg(value) - Math.round(lg(value)) ) < 1e-15 ){
                                double value_round = Math.round(lg(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            else {
                                list.push(lg(value));//push计算结果
                                varTrack.push(lg(value));//跟踪
                            }

                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("lg" + var + " = " + list.peek() + "   " + var + " = " + value);
                                logging("lg" + var + " = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("lg π" + " = " + list.peek() + "   π = " + PI);
                                    logging("lg π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("lg e" + " = " + list.peek() + "   e = " + Eu);
                                    logging("lg e" + " = " + list.peek() + "   e = " + Eu);
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("lg (" + value + ") = " + list.peek());
                                logging("lg (" + value + ") = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//lg

                        /* ln */
                        else if (obj.equals("ln")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( ln(value) - Math.round(ln(value)) ) < 1e-15 ){
                                double value_round = Math.round(ln(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            else {
                                list.push(ln(value));//push计算结果
                                varTrack.push(ln(value));//跟踪
                            }

                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("ln" + var + " = " + list.peek() + "   " + var + " = " + value);
                                logging("ln" + var + " = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("ln π" + " = " + list.peek() + "   π = " + PI);
                                    logging("ln π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("ln e" + " = " + list.peek() + "   e = " + Eu);
                                    logging("ln e" + " = " + list.peek() + "   e = " + Eu);
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("ln (" + value + ") = " + list.peek());
                                logging("ln (" + value + ") = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//ln

                        /* arcsin */
                        else if (obj.equals("arcsin")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* value的取值范围是[-1,1] */
                            double x = value instanceof Double ? (Double)value : (Integer)value;
                            if(x < -1 || x > 1){
                                System.out.println("arcsin的作用对象只能是[-1,1]的数，\"" + value + "\"不在范围之内！");
                                return null;
                            }
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( arcsin(value) - Math.round(arcsin(value)) ) < 1e-15 ){
                                double value_round = Math.round(th(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            list.push(arcsin(value));//push计算结果
                            varTrack.push(arcsin(value));//跟踪

                            String res = pify(list.peek());
                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("arcsin" + var + " = " + res + "   " + var + "=" + value + "   " + res + "=" + list.peek());
                                logging("arcsin" + var + " = " + res + "   " + var + "=" + value + "   " + res + "=" + list.peek());
                            }//参数为变量的处理
                            else {
                                System.out.println("arcsin (" + value + ") = " + res + "   " + res + " = " + list.peek());
                                logging("arcsin (" + value + ") = " + res + "   " + res + " = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//arcsin

                        /* arccos */
                        else if (obj.equals("arccos")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* value的取值范围是[-1,1] */
                            double x = value instanceof Double ? (Double)value : (Integer)value;
                            if(x < -1 || x > 1){
                                System.out.println("arccos的作用对象只能是[-1,1]的数，\"" + value + "\"不在范围之内！");
                                return null;
                            }
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( arccos(value) - Math.round(arccos(value)) ) < 1e-15 ){
                                double value_round = Math.round(th(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            else {
                                list.push(arccos(value));//push计算结果
                                varTrack.push(arccos(value));//跟踪
                            }

                            String res = pify(list.peek());//以π为因子的值
                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("arccos" + var + " = " + res + "   " + var + "=" + value + "   " + res + "=" + list.peek());
                                logging("arccos" + var + " = " + res + "   " + var + "=" + value + "   " + res + "=" + list.peek());
                            }//参数为变量的处理
                            else {
                                System.out.println("arccos (" + value + ") = " + res + "   " + res + " = " + list.peek());
                                logging("arccos (" + value + ") = " + res + "   " + res + " = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//arccos

                        /* arctan */
                        else if (obj.equals("arctan")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* value的取值范围是[-1,1] */
                            double x = value instanceof Double ? (Double)value : (Integer)value;
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( arctan(value) - Math.round(arctan(value)) ) < 1e-15 ){
                                double value_round = Math.round(arctan(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            else {
                                list.push(arctan(value));//push计算结果
                                varTrack.push(arctan(value));//跟踪
                            }

                            String res = pify(list.peek());//以π为因子的值
                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("arctan" + var + " = " + res + "   " + var + "=" + value + "   " + res + "=" + list.peek());
                                logging("arctan" + var + " = " + res + "   " + var + "=" + value + "   " + res + "=" + list.peek());
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("arctan π" + " = " + res +  "   " + res + "=" + list.peek());
                                    logging("arctan π" + " = " + res + "   " + res + "=" + list.peek());
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("arctan e" + " = " + res + "   " + res + "=" + list.peek());
                                    logging("arctan e" + " = " + res + "   " + res + "=" + list.peek());
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("arctan (" + value + ") = " + res + "   " + res + " = " + list.peek());
                                logging("arctan (" + value + ") = " + res + "   " + res + " = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//arctan

                        /* pify,将值转换成以PI为因子的式子，pify只能在最后处理结果时使用 */
                        else if (obj.equals("pify")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            String finalResult = pify(value);//以π为因子的值
                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("pify" + var + " = " + finalResult + "   " + var + " = " + value);
                                logging("pify" + var + " = " + finalResult + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("pify π" + " = " + finalResult);
                                    logging("pify π" + " = " + finalResult);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("pify e" + " = " + finalResult + "   e = " + Eu);
                                    logging("pify e" + " = " + finalResult + "   e = " + Eu);
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("pify (" + value + ") = " + finalResult);
                                logging("pify (" + value + ") = " + finalResult);
                            }//参数为double或int数值类型的处理
                            if(i < length - 1)
                                System.out.println("pify只能在最后处理结果时使用！程序将自动将此时的结果作为最终结果.");
                            return finalResult;
                        }//pify

                        /* frac,将值转换成分式，frac只能在最后处理结果时使用 */
                        else if (obj.equals("frac")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            String finalResult = frac(value,maxDenominator);//以π为因子的值
                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("frac" + var + " = " + finalResult + "   " + var + " = " + value);
                                logging("frac" + var + " = " + finalResult + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("frac π" + " = " + finalResult);
                                    logging("frac π" + " = " + finalResult);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("frac e" + " = " + finalResult + "   e = " + Eu);
                                    logging("frac e" + " = " + finalResult + "   e = " + Eu);
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("frac (" + value + ") = " + finalResult);
                                logging("frac (" + value + ") = " + finalResult);
                            }//参数为double或int数值类型的处理
                            if(i < length - 1)
                                System.out.println("frac只能在最后处理结果时使用！程序将自动将此时的结果作为最终结果.");
                            return finalResult;
                        }//frac

                        /* arsh */
                        else if (obj.equals("arsh")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* value的取值范围是[-1,1] */
                            double x = value instanceof Double ? (Double)value : (Integer)value;
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( arsh(value) - Math.round(arsh(value)) ) < 1e-15 ){
                                double value_round = Math.round(arsh(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            list.push(arsh(value));//push计算结果
                            varTrack.push(arsh(value));//跟踪

                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("arsh" + var + " = " + list.peek() + "   " + var + " = " + value);
                                logging("arsh" + var + " = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("arsh π" + " = " + list.peek() + "   π = " + PI);
                                    logging("arsh π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("arsh e" + " = " + list.peek() + "   e = " + Eu);
                                    logging("arsh e" + " = " + list.peek() + "   e = " + Eu);
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("arsh (" + value + ") = " + list.peek());
                                logging("arsh (" + value + ") = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//arsh

                        /* arch */
                        else if (obj.equals("arch")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* value的取值范围是[-1,1] */
                            double x = value instanceof Double ? (Double)value : (Integer)value;
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( arch(value) - Math.round(arch(value)) ) < 1e-15 ){
                                double value_round = Math.round(arch(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            list.push(arch(value));//push计算结果
                            varTrack.push(arch(value));//跟踪

                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("arch" + var + " = " + list.peek() + "   " + var + " = " + value);
                                logging("arch" + var + " = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("arch π" + " = " + list.peek() + "   π = " + PI);
                                    logging("arch π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("arch e" + " = " + list.peek() + "   e = " + Eu);
                                    logging("arch e" + " = " + list.peek() + "   e = " + Eu);
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("arch (" + value + ") = " + list.peek());
                                logging("arch (" + value + ") = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//arch

                        /* arth */
                        else if (obj.equals("arth")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* value的取值范围是[-1,1] */
                            double x = value instanceof Double ? (Double)value : (Integer)value;
                            if(x < -1 || x > 1){
                                System.out.println("arth的作用对象只能是[-1,1]的数，\"" + value + "\"不在范围之内！");
                                return null;
                            }
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if(Math.abs( arth(value) - Math.round(arth(value)) ) < 1e-15 ){
                                double value_round = Math.round(arth(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            list.push(arth(value));//push计算结果
                            varTrack.push(arth(value));//跟踪

                            /* 输出计算过程 */
                            if (var instanceof Character) {
                                System.out.println("arth" + var + " = " + list.peek() + "   " + var + " = " + value);
                                logging("arth" + var + " = " + list.peek() + "   " + var + " = " + value);
                            }//参数为变量的处理
                            else if (var instanceof String) {
                                if (var.equals("PI")) {
                                    System.out.println("arth π" + " = " + list.peek() + "   π = " + PI);
                                    logging("arth π" + " = " + list.peek() + "   π = " + PI);
                                }
                                else if (var.equals("Eu")) {
                                    System.out.println("arth e" + " = " + list.peek() + "   e = " + Eu);
                                    logging("arth e" + " = " + list.peek() + "   e = " + Eu);
                                }
                                else {
                                    System.out.println("出现无法识别的符号：" + var);
                                    logging("出现无法识别的符号：" + var);
                                    return null;
                                }
                            }//参数为π和e的处理
                            else {
                                System.out.println("arth (" + value + ") = " + list.peek());
                                logging("arth (" + value + ") = " + list.peek());
                            }//参数为double或int数值类型的处理
                        }//arth

                    }
                }//输出计算过程
            }//处理函数
            /* 对象是字符的实例 */
            else if(obj instanceof Character){
                //System.out.println("Character:" + obj);
                char ch = (char)obj;
                if(ch >= 'a' && ch <=  'z'){
                    if(varSet_az_value[ch - 'a'] == null){
                        System.out.printf("变量%c未进行赋值！\n",ch);
                        return null;
                    }
                    list.push(varSet_az_value[ch - 'a']);
                    varTrack.push(ch);//跟踪
                }
                else if(ch >= 'A' && ch <= 'Z'){
                    if(varSet_AZ_value[ch - 'A'] == null){
                        System.out.printf("变量%c未进行赋值！\n",ch);
                        return null;
                    }
                    list.push(varSet_AZ_value[ch - 'A']);
                    varTrack.push(ch);//跟踪
                }
                else if(isOperator(ch)){
                    if(ch == '!' || ch == '！'){
                        Object value = list.pop();//获取计算值
                        Object var = varTrack.pop();//追踪
                        //value必须大于等于0
                        if((value instanceof Integer ? (Integer)value : (Double)value) < 0){
                            System.out.println("阶乘只能应用于大于0的数，\"" + value + "\"为小于0的数！");
                            return null;
                        }
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
                        Object obj1, obj2;
                        value1 = list.pop();//权值1，Integer为1，Double为0
                        obj1 = varTrack.pop();//跟踪
                        if(!list.isEmpty()) {
                            value2 = list.pop();//权值2，Integer为1，Double为0
                            obj2 = varTrack.pop();//跟踪
                        }
                        else{
                            value2 = 0;//这里实现当只出现一个负号时可以表示为负数
                            obj2 = 0;//跟踪
                        }
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
                                /* 当除数为0 */
                                if((value1 instanceof Integer ? (Integer)value1 : (Double)value1) < 0){
                                    System.out.println("除数不能为0！在\"" + value2 + "/" + value1 + "\"的计算中除数为0.");
                                    return null;
                                }
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
                                /* 当取余运算中出现小数 */
                                if(value2 instanceof Double)
                                    System.out.println("在" + value2 + "%" + value1 + "的计算中，\"" + value2 + "\"为小数，已自动将计算转为除法运算." );
                                if(value2 instanceof Double)
                                    System.out.println("在" + value2 + "%" + value1 + "的计算中，\"" + value1 + "\"为小数，已自动将计算转为除法运算." );
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
        System.out.println("计算过程：");
        logging("计算过程：");
        ArrayList postfixNotation;
        try {
            postfixNotation = (ArrayList) antiPolish(expression);
        }catch(Exception ex){
            System.out.println("您所输入的表达式\"" + expression + "\"无法计算！");
            return;
        }
        Object result = antiPolishEvaluate(postfixNotation);
        if(result != null) {
            System.out.print("计算结果是：");
            if(result instanceof Integer)
                System.out.println(result);
            else if(result instanceof Double){
                double res = (Double)result;
                if(Math.abs(res - Math.round(res)) > 1e-15)
                    System.out.println(res);
                else
                    System.out.println(Math.round(res));
            }
            else
                System.out.println(result);//String
        }
    }

    static void directCaculate(String expression){
        /* 获取当前时间 */
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");// 定义时间格式
        String currentTimeString = currentTime.format(formatter);// 将当前时间格式化为字符串
        mathExpression.add(currentTimeString + "   " + expression);//记录输入的计算表达式
        logging(new Date() + " 计算数学表达式：" + expression);
        logging("计算过程：");
        System.out.println("计算过程：");
        ArrayList  postfixNotation;
        try {
            postfixNotation = (ArrayList) antiPolish(expression);
        }catch(Exception ex){
            System.out.println("您所输入的表达式\"" + expression + "\"无法计算！");
            return;
        }
        Object result = antiPolishEvaluate(postfixNotation);
        if(result != null) {
            System.out.print("计算结果是：");
            if(result instanceof Integer)
                System.out.println(result);
            else if(result instanceof Double){
                double res = (Double)result;
                if(Math.abs(res - Math.round(res)) > 1e-15)
                    System.out.println(res);
                else
                    System.out.println(Math.round(res));
            }
            else
                System.out.println(result);//String
        }
    }

    /* 列出所有输入过的计算表达式 */
    static void ls(){
        for(String expression: mathExpression)
            System.out.println(expression);
    }

    static void help(){
        System.out.println("计算    当你输入的命令是一条计算表达式时，程序会自动将其识别出并计算该计算表达式");
        System.out.println("exit   退出程序");
        System.out.println("adcg   添加或设置变量，变量名只能是大小写字母，变量未赋值时初始为null，程序根据你提交的变量集对对应的变量进行赋值");
        System.out.println("read   将根据你输入的指定路径读取变量集并给对应的变量进行赋值");
        System.out.println("save   将根据你输入的指定路径读取变量集并给对应的变量进行赋值");
        System.out.println("var    显示所有已经进行赋值过的变量");
        System.out.println("""
                antipo 输入计算表达式进行计算，程序将会给出它的每一步计算过程以及计算结果
                       程序计算支持下列符号运算符：
                       加法+、减法-、乘法*、除法/、取余运算%、幂运算^、阶乘运算(!/！)、
                       同时支持下列函数运算符：
                       正弦sin、余弦cos、正切tan、取绝对值abs = |x|、
                       双曲正弦sh x = (e^x - e ^ -x)/2、双曲余弦ch x = (e ^ x + e ^ -x)/2、双曲正切th x = sh x / chx、
                       反正弦arcsin、反余弦arccos、反正切arctan、以10为底的对数lg、以e为底的对数ln
                       对于sin、cos、tan等等这些函数运算符，不限制大小写，程序会自动将字母统一转换成小写再处理
                       运算优先级：{+ -} < {* / %} < {^} < {!} = {sin、cos等等所有函数运算符}
                       计算支持两大常数：圆周率π用PI表示、自然常数e用Eu表示
                       支持使用中英文圆括号()/（）、方括号[]、来改变运算顺序，注意中英文括号可以混合使用，例如左边为中文括号右边为英文括号。
                """);
        System.out.println("""
                ls     列出所有之前输入的计算表达式，以时间+计算表达式的形式呈现，例如：
                       11:14:38   sin PI + cos PI
                       11:15:59   sin(PI / 2 + 3.5 * PI) + cos(PI - 0.25 * PI)
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
            String command,expression;
            command = userInput.nextLine();
            expression = command;//如果输入的是计算表达式那么保留原始输入的字符串
            command = command.toLowerCase();//命令不限大小写
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
                    if(antiPolishTest(expression))
                        directCaculate(expression);
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
        System.out.println(pify(str));//0.3π
        System.out.println(pify(2.4 * PI));
        System.out.println(pify(5/12.0 * PI));
        System.out.println(pify(17.0/9 * PI));
        System.out.println(pify(3.9 * PI));
         */

    }
}
