import Polynomial.PolynomialCalculator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.System.exit;


public class AntiPolish {
    static Object[] varSet_az_value = new Object[26];//索引index处的值表示变量('a'+index)的值
    static Object[] varSet_AZ_value = new Object[26];//索引index处的值表示变量('A'+index)的值
    static boolean ifShowAntiPolish = true;//是否显示转换得到的逆波兰表达式
    static boolean ifShowProcess = true;//是否显示每一步计算过程
    static boolean ifShowMenu = true;//是否显示菜单
    static String inputAnchor = "$";//输入锚，用户输入时，位于最前面的提示输入的标志
    static File data = new File("data");
    static File configPath = new File("data//config.txt");
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
        if(!data.exists()){
            try{
                if(!data.mkdir())
                    System.out.println("创建数据文件夹\"" + data.getAbsolutePath() + "\"失败！");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(!configPath.exists()){
            try{
                if(!configPath.createNewFile())
                    System.out.println("创建配置文件\"" + configPath.getAbsolutePath() + "\"失败！");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!logPath.exists()){
            try{
                if(!logPath.createNewFile())
                    System.out.println("创建日志文件\"" + logPath.getAbsolutePath() + "\"失败！");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!defaultReadPath.exists()){
            try{
                if(!defaultReadPath.createNewFile())
                    System.out.println("创建变量集默认读取文件\"" + defaultReadPath.getAbsolutePath() + "\"失败！");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!defaultSavePath.exists()){
            try{
                if(!defaultSavePath.createNewFile())
                    System.out.println("创建变量集默认保存文件\"" + defaultSavePath.getAbsolutePath() + "\"失败！");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        readConfig();//读取配置信息
    }

    /* 设置配置项的值 */
    static boolean setConfigItem(String item,String value){
        if(item.contains("ifShowProcess")){
            if(value.contains("true"))
                ifShowProcess = true;
            else
                ifShowProcess = false;
        }
        else if(item.contains("ifShowAntiPolish")){
            if(value.contains("true"))
                ifShowAntiPolish = true;
            else
                ifShowAntiPolish = false;
        }
        else if(item.equals("ifShowMenu")){
            if(value.contains("true"))
                ifShowMenu = true;
            else
                ifShowMenu = false;
        }
        else if(item.contains("inputAnchor")){
            inputAnchor = value;
        }
        else if(item.contains("data")){
            data = new File(value);
        }
        else if(item.contains("configPath")){
            configPath = new File(value);
        }
        else if(item.contains("logPath")){
            logPath = new File(value);
        }
        else if(item.contains("defaultReadPath")){
            logPath = new File(value);
        }
        else if(item.contains("defaultSavePath")){
            defaultSavePath = new File(value);
        }
        else if(item.contains(("maxDenominator"))){
            maxDenominator = Integer.parseInt(value);
        }
        else return false;
        return true;
    }

    /* 读取配置信息，配置文件不同字段之间必须用空格隔开 */
    static void readConfig() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(configPath);
        }catch(Exception ex){
            try {
                configPath.createNewFile();
            } catch (IOException e) {
                System.out.println("读取配置文件失败！请重新启动程序");
            }
        }
        while(scanner.hasNext()){
            String item = scanner.next();
            String str = scanner.next();//跳过"="
            String value = scanner.next();
            if(!setConfigItem(item,value)){
                System.out.println("配置文件中\"" + item + " " +  str + " " +  value + "\"中存在错误");
            }
        }
        scanner.close();
    }

    /* 保存配置信息 */
    static void saveConfig(){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(configPath);
        }catch(Exception ex){
            try {
                configPath.createNewFile();
            } catch (IOException e) {
                System.out.println("保存配置文件失败！请重新尝试");
            }
        }
        writer.println("ifShowAntiPolish = " + ifShowAntiPolish);
        writer.println("ifShowProcess = " + ifShowProcess);
        writer.println("ifShowMenu = " + ifShowMenu);
        writer.println("inputAnchor = " + inputAnchor);
        writer.println("data = " + data);
        writer.println("configPath = " + configPath);
        writer.println("logPath = " + logPath);
        writer.println("defaultReadPath = " + defaultReadPath);
        writer.println("defaultSavePath = " + defaultSavePath);
        writer.println("maxDenominator = " + maxDenominator);
        writer.close();
    }

    /* 修改配置信息 */
    static void config(){
        Scanner input = new Scanner(System.in);
        String option = "10";
        String value = null;
        boolean flag = false;
        while(true){
            System.out.println("+ —————————————————————————————————————————配置信息————————————————————————————————————————— +");
            System.out.printf(" %-100s \n", "[1]是否显示计算过程(true/false)        ifShowProcess = " + ifShowProcess);
            System.out.printf(" %-100s \n", "[2]是否显示逆波兰表达式(true/false)     ifShowAntiPolish  = " + ifShowAntiPolish);
            System.out.printf(" %-100s \n", "[3]是否显示菜单(true/false)           ifShowMenu = " + ifShowMenu);
            System.out.printf(" %-100s \n", "[4]输入锚(一段提示符)                  inputAnchor = " + inputAnchor);
            System.out.printf(" %-100s \n", "[5]数据文件夹的路径(文件路径)           data = " + data);
            System.out.printf(" %-100s \n", "[6]配置文件的路径(文件路径)             configPath = " + configPath.getAbsolutePath());
            System.out.printf(" %-100s \n", "[7]日志文件的路径(文件路径)             logPath = " + logPath.getAbsolutePath());
            System.out.printf(" %-100s \n", "[8]默认变量集读取路径(文件路径)          defaultReadPath = " + defaultReadPath.getAbsolutePath());
            System.out.printf(" %-100s \n", "[9]默认变量集保存路径(文件路径)          defaultSavePath = " + defaultSavePath.getAbsolutePath());
            System.out.printf(" %-100s \n", "[10]小数转分式中的最大分母(整数)          maxDenominator = " + maxDenominator);
            System.out.println("+ ————————————————————————————————————————————————————————————————————————————————————————— +");
            System.out.print("请输入需要修改的配置项的配置序号(输入0退出)：");
            try {
                option = input.nextLine();
            }catch(Exception ex){
                input.nextLine();
            }
            if(option.equals("0")){
                break;
            }
            else if(option.equals("1")){
                System.out.print("ifShowProcess = " + ifShowProcess + " -> ifShowProcess = " );
                value = input.nextLine();
                flag = setConfigItem("ifShowProcess",value);
            }
            else if(option.equals("2")){
                System.out.print("ifShowAntiPolish = " + ifShowAntiPolish + " -> ifShowAntiPolish = " );
                value = input.nextLine();
                flag = setConfigItem("ifShowAntiPolish",value);
            }
            else if(option.equals("3")){
                System.out.print("ifShowMenu = " + ifShowMenu + " -> ifShowMenu = " );
                value = input.nextLine();
                flag = setConfigItem("ifShowMenu",value);
            }
            else if(option.equals("4")){
                System.out.print("inputAnchor = " + inputAnchor + " -> inputAnchor = " );
                value = input.nextLine();
                flag = setConfigItem("inputAnchor",value);
            }
            else if(option.equals("5")){
                System.out.print("data = " + data.getAbsolutePath() + " -> data = " );
                value = input.nextLine();
                flag = setConfigItem("data",value);
            }
            else if(option.equals("6")){
                System.out.print("configPath = " + configPath.getAbsolutePath() + " -> configPath = " );
                value = input.nextLine();
                flag = setConfigItem("configPath",value);
            }
            else if(option.equals("7")){
                System.out.print("logPath = " + logPath.getAbsolutePath() + " -> logPath = " );
                value = input.nextLine();
                flag = setConfigItem("logPath",value);
            }
            else if(option.equals("8")){
                System.out.print("defaultReadPath = " + defaultReadPath.getAbsolutePath() + " -> defaultReadPath = " );
                value = input.nextLine();
                flag = setConfigItem("defaultReadPath",value);
            }
            else if(option.equals("9")){
                System.out.print("defaultSavePath = " + defaultSavePath.getAbsolutePath() + " -> defaultSavePath = " );
                value = input.nextLine();
                flag = setConfigItem("defaultSavePath",value);
            }
            else if(option.equals("10")){
                System.out.print("maxDenominator = " + maxDenominator + " -> maxDenominator = " );
                value = input.nextLine();
                flag = setConfigItem("maxDenominator",value);
            }
            else{
                System.out.print("选项输入错误，请重新输入！");
                input.nextLine();
                continue;
            }
            if(flag){
                System.out.println("修改成功！");
            }
            else {
                System.out.println("修改失败");
            }
            System.out.println("");
        }
        saveConfig();//保存当前配置信息到配置文件中
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
        System.out.println("请输入变量集(#结束输入)：");
        String varSet = "";
        String input = scanner.nextLine();
        while(!input.endsWith("#")){
            varSet = varSet.concat(input);
            input = scanner.nextLine();
        }
        varSet = varSet.concat(input);
        return varSet.substring(0,varSet.length() - 1) + " ";
    }

    /*
        变量集由多条赋值语句构成，例如a=1b=2c=3d=4可以将a、b、c、d分别赋值1、2、3、4
        赋值语句的语法：变量名 = 值
        在书写变量集的时候允许任意使用空字符，变量名称必须使用变量集里面的变量名称，可以是大小写字母
    */
    /* 提交变量集 */
    static void submitVar(String varSet){
        int length = varSet.length();
        for(int i = 0;i < length;i++) {
            char ch = varSet.charAt(i);//逐字处理变量集中的字符
            if (ch >= 'a' && ch <= 'z') {
                int start, end;
                /* 向后找到第一个=的位置 */
                start = varSet.indexOf("=", i);
                while (!(varSet.charAt(start) >= '0' && varSet.charAt(start) <= '9'))
                    start++;
                end = start + 1;
                while ((varSet.charAt(end) >= '0') && (varSet.charAt(end) <= '9') || varSet.charAt(end) == '.')
                    end++;
                String sub = varSet.substring(start, end);
                Object value;
                //如果sub中有空格下面将报错
                if (sub.contains("."))
                    value = Double.parseDouble(sub);
                else
                    value = Integer.parseInt(sub);
                varSet_az_value[ch - 'a'] = value;//变量赋值
                i = end - 1;
            } else if (ch >= 'A' && ch <= 'Z') {
                int start, end;
                /* 向后找到第一个=的位置 */
                start = varSet.indexOf("=", i);
                while (!(varSet.charAt(start) >= '0' && varSet.charAt(start) <= '9'))
                    start++;
                end = start + 1;
                while ((varSet.charAt(end) >= '0') && (varSet.charAt(end) <= '9') || varSet.charAt(end) == '.')
                    end++;
                String sub = varSet.substring(start, end);
                Object value;
                //如果sub中有空格下面将报错
                if (sub.contains("."))
                    value = Double.parseDouble(sub);
                else
                    value = Integer.parseInt(sub);
                varSet_AZ_value[ch - 'A'] = value;//变量赋值
                i = end - 1;
            } else if (ch == ' ' || ch == '\n' || ch == '\t') {
            } else {
                System.out.println(varSet);
                System.out.printf("在上面提交的变量集中%c不属于规定的变量名称，变量名称只能是大小写字母!", ch);
                return;
            }
        }
    }

    static void removeVar(String vars){
        ArrayList<Object> removed = new ArrayList<>();
        int length = vars.length();
        for(int i = 0;i < length;i++){
            char ch = vars.charAt(i);
            if(ch >= 'a' && ch <= 'z') {
                varSet_az_value[ch - 'a'] = null;
                removed.add(ch);
            }
            else if(ch >= 'A' && ch <= 'Z') {
                varSet_az_value[ch - 'A'] = null;
                removed.add(ch);
            }
            else
                System.out.printf(ch + "不属于变量名，已自动跳过该字符");
        }
        if(removed.size() != 0){
            int size = removed.size();
            System.out.print("程序已将变量");
            for(int i = 0;i < size - 1;i++)
                System.out.print("'" + removed.get(i) + "'、");
            System.out.print("'" + removed.get(size - 1));//最后一个变量后面不加‘、’
            System.out.println("移除");
            logging(removed.size() + "个变量\"" + listToString(removed) + "\"被移除");
        }
        else {
            System.out.println("没有变量被移除");
            logging("没有变量被移除");
        }
    }

    static void remove(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入所有需要移除的变量名(#结束输入)：");
        String vars = "";
        String input = scanner.nextLine();
        while(!input.endsWith("#")){
            vars = vars.concat(input);
            input = scanner.nextLine();
        }
        vars = vars.concat(input);
        removeVar(vars.substring(0,vars.length() - 1));
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
            if(option.contains("是")) {
                File file = new File(path);
                if (!file.createNewFile())//创建该文件
                    System.out.println("创建文件\"" + file.getAbsolutePath() + "\"失败！");
                return null;
            }
            else if(option.compareTo("")==0){
                System.out.println("已取消读取变量");
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
        int cnt = 0;
        System.out.println("进行赋值过的所有变量：");
        for(int i = 0;i < 26;i++){
            if(varSet_az_value[i] != null){
                System.out.printf("%c = ",'a' + i);
                System.out.print(varSet_az_value[i] + "   ");
                flag = true;
                cnt++;
                if(cnt % 6 == 0) System.out.println();
            }
        }
        for(int i = 0;i < 26;i++){
            if(varSet_AZ_value[i] != null){
                System.out.printf("%c = ",'A' + i);
                System.out.print(varSet_AZ_value[i] + "   ");
                flag = true;
                cnt++;
                if(cnt % 6 == 0) System.out.println();
            }
        }
        if(!flag){
            System.out.println("null");
            logging("null");
        }
        if(cnt % 6 != 0)
            System.out.println();
    }

    /* 将当前变量集转为字符串 */
    static String varSetToString(){
        String varSet = "";
        int cnt = 0;
        for(int i = 0;i < 26;i++){
            if(varSet_az_value[i] != null){
                varSet = varSet.concat((char)('a' + i ) + " = " + varSet_az_value[i] + "   ");
                cnt++;
            }
            if(cnt % 6 == 0) varSet = varSet + "\n";
        }
        for(int i = 0;i < 26;i++){
            if(varSet_AZ_value[i] != null){
                varSet = varSet.concat((char)('A' + i ) + " = " + varSet_AZ_value[i] + "   ");
                cnt++;
            }
            if(cnt % 6 == 0) varSet = varSet + "\n";
        }
        if(cnt % 6 != 0) varSet = varSet + "\n";
        return varSet;
    }

    /* 获取运算符的优先级，返回值越大优先级越大 */
    static int getLevel(String ch){
        return switch (ch) {
            case "?", "？" -> 0;
            case "+", "-" -> 1;
            case "*", "/", "%" -> 2;
            case "^" -> 3;
            case "!", "！","~" -> 4;
            case "(", ")", "（", "）", "[", "]" -> 5;
            default -> 5;
        };
    }

    static boolean isFunction(String str){
        String[] functions = {
                "lg","ln","sh","ch","th",
                "sin","cos","tan","abs","exp","gcd","lcm","sec","csc","cot",
                "arsh","arch","arth","pify","frac","sqrt","ceil",
                "floor","round","prime",
                "arcsin","arccos","arctan","factor"
        };
        for (String function : functions) if (function.equals(str)) return true;
        return false;
    }//优先级为4

    /* 判断一个字符串是否为计算表达式 */
    static boolean isExpression(String command){
        command = command.replace(" ","");
        command = command.replace("PI","").replace("Eu","");
        command = command.replaceAll("\\d","");//移除command中的所有数字
        command = command.replace(".","");//移除小数点
        command = command.toLowerCase();//将command转换的字母全部转为小写
        String[] tokens = command.split("[?？+\\-*/%^!！~()（）\\[\\]]");//以+-*/%^!！()（）[]这些符号分割字符串
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
                '?','？','+','-','*','/','%','^','!', '！','~','(',')','（','）','[',']'
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

    /*  计算阶乘,n! = 1*2*3*...*n,定义域[0,∞)，值域[1,∞) */
    static double fact(Object obj){
        int n = 1;
        double res = 1;
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

    /*  sh,计算双曲正弦,sh x = (e^x - 1/e^x) / 2，定义域(-∞,∞)，值域(-∞,∞)，  */
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

    /*  ch，计算双曲余弦,ch x = (e^x + 1/e^x) / 2，定义域(-∞,∞)，值域[1,∞]  */
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

    /*  th,计算双曲正切，th = sh / ch = (e^x - 1/e^x)/(e^x + 1/e^x)，定义域(-∞,∞)，值域(-1,1)  */
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

    /*  lg，计算以10为底的对数，定义域(0,∞)，值域(-∞,∞)  */
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

    /*  ln，计算以自然常数e为底的对数，定义域(0,∞)，值域(-∞,∞)  */
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

    /* sin，三角正弦函数,定义域(-∞,∞)，值域[-1,1] */
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

    /* cos，三角余弦函数,定义域(-∞,∞)，值域[-1,1] */
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

    /* tan，三角正切函数,定义域{x|x != π/2 + kπ,k可取所有整数} 值域(-∞,∞)  */
    static double tan(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        if(Math.cos(x) == 0)
            return Double.POSITIVE_INFINITY;//无穷大
        return Math.tan(x);
    }

    /* sec，正割函数,sec x = 1 / cos x,定义域(-∞,∞)，值域[-∞,-1]和[1,∞] */
    static double sec(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        if(Math.cos(x) == 0) {
            return Double.POSITIVE_INFINITY;//无穷大
        }
        return 1.0 / Math.cos(x);
    }//sec

    /* csc，余割函数,csc x = 1 / sin x,定义域(-∞,∞)，值域[∞,-1]和[1,∞] */
    static double csc(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        if(Math.sin(x) == 0)
            return Double.POSITIVE_INFINITY;//无穷大
        return 1.0 / Math.sin(x);
    }//csc

    /* 余切，余切函数,cot x = 1 / tan x,定义域{x|x != kπ,x!= π/2 + kπ,k可取所有整数} 值域(-∞,∞) */
    static double cot(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        if(Math.tan(x) == 0)
            return Double.POSITIVE_INFINITY;//无穷大
        return 1.0 / Math.tan(x);
    }//cot

    /* abs,取绝对值，定义域(-∞,∞)，值域[0,∞) */
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

    /*  exp,计算e的x次方，exp(x) = e ^ x,定义域(-∞,∞)，值域(0,∞)  */
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

    /*  max,?用于比较两个数大小,x ? y返回x、y之间的最大值  */
    static double max(Object obj1,Object obj2){
        double x = 0,y = 0;

        if(obj1 instanceof Double)
            x = (Double)obj1;
        else if(obj1 instanceof Integer)
            x = (Integer)obj1;
        else if(obj1 instanceof String)
            x = Double.parseDouble((String) obj1);

        if(obj2 instanceof Double)
            y = (Double)obj2;
        else if(obj2 instanceof Integer)
            y = (Integer)obj2;
        else if(obj2 instanceof String)
            y = Double.parseDouble((String) obj2);
        return Math.max(x,y);
    }//max

    /* gcd 求两个数的最大公约数,如果参数为小数，那么将自动将该参数向下取整再进行计算 */
    static int gcd(Object obj1,Object obj2){
        int x = 0,y = 0;

        if(obj1 instanceof Double)
            x = (int)((double)((Double)obj1));
        else if(obj1 instanceof Integer)
            x = (Integer)obj1;
        else if(obj1 instanceof String)
            x = Integer.parseInt((String) obj1);

        if(obj2 instanceof Double)
            y = (int)((double)((Double)obj1));
        else if(obj2 instanceof Integer)
            y = (Integer)obj2;
        else if(obj2 instanceof String)
            y = Integer.parseInt((String) obj2);

        int temp;
        if (y > x) {
            temp = x;
            x = y;
            y = temp;
        }
        while (y != 0) {
            temp = y;
            y = x % y;
            x = temp;
        }
        return x;
    }//gcd

    /* lcm 求两个数的最小公倍数,如果参数为小数，那么将自动将该参数向下取整再进行计算 */
    static int lcm(Object obj1,Object obj2){
        int x = 0,y = 0;

        if(obj1 instanceof Double)
            x = (int)((double)((Double)obj1));
        else if(obj1 instanceof Integer)
            x = (Integer)obj1;
        else if(obj1 instanceof String)
            x = Integer.parseInt((String) obj1);

        if(obj2 instanceof Double)
            y = (int)((double)((Double)obj1));
        else if(obj2 instanceof Integer)
            y = (Integer)obj2;
        else if(obj2 instanceof String)
            y = Integer.parseInt((String) obj2);

        int gcd = gcd(x,y);
        return (x * y) / gcd;
    }//lcm

    /*  pify    将一个数转为以π为因子的式子,例如pify(1.75*π) = π + (3/4)*π  */
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
        int integerPart = (int)Math.floor(coef);//向下取整，整数部分anti
        double fractionalPart = coef - integerPart;//小数部分

        //正数
        if(ifPositive) {
            if (fractionalPart != 0) {
                if(integerPart != 0)
                    return (integerPart != 1 ? integerPart : "") + "π + (" + frac(fractionalPart, maxDenominator) + ")π";
                else
                    return "(" + frac(fractionalPart, maxDenominator) + ")π";
            }
            else
                return (integerPart != 1 ? integerPart : "") + "π";
        }

        //负数
        else {
            if (fractionalPart != 0)
                if(integerPart != 0)
                    return "-" + (integerPart != 1 ? integerPart : "") + "π - (" + frac(fractionalPart,maxDenominator) + ")π)";
                else
                    return "-(" + frac(fractionalPart,maxDenominator) + ")π";
            else
                return "-" + (integerPart != 1 ? integerPart : "") + "π";
        }
    }

    /* frac    将一个数转换成分式，例如frac(3.75) = 15/4,frac(0.4166666666) = 5/12 */
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

    /*  sqrt,sqrt x = x ^ 0.5,计算正平方根  */
    static double sqrt(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.sqrt(x);
    }//sqrt

    /*  arsh,反双曲正弦函数,arsh = ln(x + sqrt(x ^ 2 + 1)),定义域(-∞,∞)，值域(-∞,∞)  */
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

    /*  arch,反双曲余弦函数,arch = ln(x + sqrt(x ^ 2 - 1)),定义域(-∞,∞)，值域[0,∞)  */
    static double arch(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return ln(x + Math.sqrt(x * x - 1));//arch = ln(x + sqrt(x ^ 2 - 1));
    }//arch

    /*  arth,反三角正切函数,arth = ln[(1+x)/(1-x)] / 2,定义域[-1,1]，值域(-∞,∞)  */
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

    /*  向上取整,ceil x得到最接近x且大于x的整数,例如：ceil 3.1 = 4,ceil(-3.5) = -3  */
    static int ceil(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return (int)Math.ceil(x);
    }//ceil

    /*  floor   向下取整,ceil x得到最接近x且小于x的整数,例如：floor 3.9 = 3,floor(-3.1) = -4  */
    static int floor(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return (int)Math.floor(x);
    }//floor

    /*  round   四舍五入,例如：round 2.4 = 2,round 2.5 = 3,round(-2.5) = -2  */
    static int round(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return (int)Math.round(x);
    }//round

    /*  arcsin,反三角正弦函数,定义域(-∞,∞)，值域(-π/2,π/2)  */
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


    /*  arccos,反三角余弦函数,定义域(-∞,∞)，值域(-π/2,π/2)  */
    static double arccos(Object obj){
        double x = 0;
        if(obj instanceof Double)
            x = (Double)obj;
        else if(obj instanceof Integer)
            x = (Integer)obj;
        else if(obj instanceof String)
            x = Double.parseDouble((String) obj);
        return Math.acos(x);
    }//arccos

    /*  arctan,反三角正切函数,定义域{x|x!= π/2 + kπ,k可取所有整数},值域(-∞,∞)   */
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

    /* prime    判断一个数是否为素数 */
    static boolean prime(int x){
        int i;
        double t = Math.sqrt(x);
        for(i = 2;i < t;i++)
            if(x % i == 0) break;
        if(i > t) return true;
        return false;
    }

    /* factor 素因子分解，将一个数分解成多个素数的乘积,例如factor (72) = (2^3) * (3^2) */
    static String factor(int num) {
        StringBuilder result = new StringBuilder();
        List<Integer> factors = new ArrayList<>();
        List<Integer> powers = new ArrayList<>();
        if(num == 1) return "1";
        for(int i = 2;i < num;i++){
            if(num % i == 0 && prime(i)){
                factors.add(i);//记录素因子
                int power = 0;//记录素因子的次方
                while(num > 0 && num % i == 0){
                    num /= i;
                    power++;
                }
                powers.add(power);//记录最终素因子的次方
            }
        }
        if(num != 1){
            factors.add(num);
            powers.add(1);
        }
        for(int i = 0;i < factors.size();i++){
            int factor = factors.get(i);
            int power = powers.get(i);
            if(power != 1)
                result.append((i == 0 ? "" : " * ") + "(" + factor + "^" + power + ")");
            else
                result.append((i == 0 ? "" : " * ") + factor);
        }

        return result.toString();
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
     */
    static List antiPolish(String expression) throws Exception{
        Stack stack = new Stack();
        ArrayList<Object> postfixNotation = new ArrayList<>();
        int length = expression.length();
        try {
            for (int i = 0; i < length; i++) {
                char ch = expression.charAt(i);
                if (ch == ' ' || ch == '\t') {
                }

                /* 常量PI = Math.PI */
                else if (ch == 'P' && expression.charAt(i + 1) == 'I') {
                    i++;
                    postfixNotation.add("PI");
                }

                /* 常量e为欧拉数(Euler number),e = Math.E */
                else if (ch == 'E' && expression.charAt(i + 1) == 'u') {
                    i++;
                    postfixNotation.add("Eu");
                }

                /* 除PI和Eu外，连续出现两个字母判定为函数 */
                else if (Character.isLetter(ch) && i + 1 < length && Character.isLetter(expression.charAt(i + 1))) {
                    int flag = 0;
                    String func1 = null, func2 = null, func3 = null, func4 = null,func5 = null;
                    /* 所有函数都以小写处理 */
                    //lg、ln、sh、ch、th
                    if (i + 2 <= length) func1 = expression.substring(i, i + 2).toLowerCase();

                    //sin、cos、tan、sec、csc、cot、abs、exp、max、gcd、lcm
                    if (i + 3 <= length) func2 = expression.substring(i, i + 3).toLowerCase();

                    //pify、frac、arsh、arch、arth、ceil
                    if (i + 4 <= length) func3 = expression.substring(i, i + 4).toLowerCase();

                    //floor、round、prime
                    if(i + 5 <= length) func4 = expression.substring(i,i + 5).toLowerCase();

                    //arcsin、arccos、arctan、factor
                    if (i + 6 <= length) func5 = expression.substring(i, i + 6).toLowerCase();

                    /* 根据函数字母个数进行分类 */
                    if (func1 != null && ( func1.equals("sh") || func1.equals("ch") || func1.equals("th")
                            || func1.equals("lg") || func1.equals("ln")))
                        flag = 1;

                    if (func2 != null && ( func2.equals("sin") || func2.equals("cos") || func2.equals("tan")
                            || func2.equals("sec") || func2.equals("csc") || func2.equals("cot")
                            || func2.equals("abs") || func2.equals("exp") || func2.equals("gcd")
                            || func2.equals("lcm") ))
                        flag = 2;

                    if (func3 != null && ( func3.equals("pify") || func3.equals("frac") || func3.equals("sqrt")
                            || func3.equals("arsh") || func3.equals("arch") || func3.equals("arth")
                            || func3.equals("ceil") ))
                        flag = 3;

                    if(func4 != null && ( func4.equals("floor") || func4.equals("round") || func4.equals("prime") ))
                        flag = 4;

                    if (func5 != null && ( func5.equals("arcsin") || func5.equals("arccos") || func5.equals("arctan")
                            || func5.equals("factor") ))
                        flag = 5;

                    if (flag != 0) {
                        /* 如果栈不为空 */
                        if (stack.size() != 0) {
                            String top = stack.peek().toString();
                            int level1 = 4;//扫描到的函数,默认函数的优先级为4
                            int level2 = getLevel(top);//栈顶的符号
                            /* 将优先级低于栈顶符号的符号入栈 */
                            if (level1 > level2 || top.equals("(") || top.equals("（") || top.equals("[")) {
                            } else {//把优先级大于等于当前级别的符号全部出栈
                                while (level1 <= level2 && !top.equals("(") && !top.equals("（") && !top.equals("[")) {
                                    postfixNotation.add(stack.pop());
                                    if (stack.isEmpty()) break;
                                    if (stack.peek() instanceof String)
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
                        switch (flag) {
                            case 5 -> stack.push(func5);
                            case 3 -> stack.push(func3);
                            case 4 -> stack.push(func4);
                            case 2 -> stack.push(func2);
                            case 1 -> stack.push(func1);
                        }
                    } else {
                        System.out.println("出现无法识别的符号！");
                        logging("\"" + expression.substring(i, i + flag + 1) + "\"中存在无法识别的符号！");
                        exit(0);
                    }
                }

                /* 如果是变量 */
                else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
                    postfixNotation.add(ch);

                /* 如果是数字 */
                else if (ch >= '0' && ch <= '9') {
                    int j = i;
                    char num;
                    Object number;
                    do {
                        j++;
                        if (j >= length) break;
                        num = expression.charAt(j);
                    } while (num >= '0' && num <= '9' || num == '.');
                    String sub = expression.substring(i, j);
                    if (sub.contains(".")) {
                        number = Double.parseDouble(sub);
                    } else number = Integer.parseInt(sub);//将数字字符串解析为Int变量
                    i = j - 1;//将下一个扫描的位置移到数字后一个字符
                    postfixNotation.add(number);
                }

                /* 如果是运算符 */
                else {
                    if (stack.isEmpty()) stack.push(ch);//栈为空
                    else if (ch == ')' || ch == '）') {
                        /* 一直出栈直到遇到'(' */
                        String top = stack.peek().toString();
                        while (!top.equals("(") && !top.equals("（")) {
                            postfixNotation.add(stack.pop());
                            top = stack.peek().toString();
                        }
                        stack.pop();//pop掉(或（
                    }//中英文括号可以混合使用
                    else if (ch == ']') {
                        /* 一直出栈直到遇到'[' */
                        while (!stack.peek().toString().equals("["))
                            postfixNotation.add(stack.pop());
                        stack.pop();//pop掉[
                    }//方括号[]与()的作用相同
                    else {
                        String top = stack.peek().toString();//栈顶的符号
                        int level1 = getLevel(Character.toString(ch));//扫描到的符号
                        int level2;//栈顶符号的优先级
                        if (stack.peek() instanceof String)
                            level2 = 4;//函数的优先级
                        else
                            level2 = getLevel(top);
                        //System.out.println("level1:" + level1 + " " + "level2：" + level2 + " ch:" + ch);
                        /* 将优先级低于栈顶符号的符号入栈 */
                        if (level1 > level2 || top.equals("(") || top.equals("（") || top.equals("[")) {
                            stack.push(ch);
                        } else {//把优先级大于等于当前级别的符号全部出栈
                            while (level1 <= level2 && !top.equals("(") && !top.equals("（") && !top.equals("[")) {
                                postfixNotation.add(stack.pop());
                                if (stack.isEmpty()) break;
                                if (stack.peek() instanceof String)
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
        }catch(Exception ex){
            throw ex;
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
    static Object antiPolishEvaluate(ArrayList<Object> postfixNotation) throws Exception {
        LinkedList<Object> list = new LinkedList<>();
        LinkedList<Object> varTrack = new LinkedList<>();//用于跟踪变量，确定哪些值是变量
        int length = postfixNotation.size();
        if (length == 1) logging("无计算过程");
        try {
            for (int i = 0; i < length; i++) {
            /*
            System.out.println("list：" + listToString(list));
            System.out.println("varTrack：" + listToString(varTrack));
             */
                Object obj = postfixNotation.get(i);
                /* 对象是Double的实例 */
                if (obj instanceof Double) {
                    list.push(obj);
                    varTrack.push(obj);//跟踪
                    //System.out.println("Double:" + obj);
                }

                /* 对象是Integer的实例 */
                else if (obj instanceof Integer) {
                    list.push(obj);
                    varTrack.push(obj);//跟踪
                }

                else if (obj instanceof String) {
                    if (obj.equals("PI")) {
                        list.push(PI);//跟踪
                        varTrack.push(obj);//跟踪
                    } else if (obj.equals("Eu")) {
                        list.push(Eu);
                        varTrack.push(obj);//跟踪
                    }
                    /* sin、cos等等函数的处理 */
                    else {
                        /* sh */
                        if (obj.equals("sh")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(sh(value) - Math.round(sh(value))) < 1e-15) {
                                double value_round = Math.round(sh(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(sh(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("sh" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("sh" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("sh π" + " = " + list.peek() + "   π = " + PI);
                                        logging("sh π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("sh e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("sh e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("sh (" + value + ") = " + list.peek());
                                    logging("sh (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//sh

                        /* ch */
                        else if (obj.equals("ch")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(ch(value) - Math.round(ch(value))) < 1e-15) {
                                double value_round = Math.round(ch(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(ch(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("ch" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("ch" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("ch π" + " = " + list.peek() + "   π = " + PI);
                                        logging("ch π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("ch e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("ch e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("ch (" + value + ") = " + list.peek());
                                    logging("ch (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//ch

                        /* th */
                        else if (obj.equals("th")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(th(value) - Math.round(th(value))) < 1e-15) {
                                double value_round = Math.round(th(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(th(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("th" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("th" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("th π" + " = " + list.peek() + "   π = " + PI);
                                        logging("th π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("th e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("th e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("th (" + value + ") = " + list.peek());
                                    logging("th (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//th

                        /* lg */
                        else if (obj.equals("lg")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* value的取值范围是(0, ∞) */
                            double x = value instanceof Double ? (Double) value : (Integer) value;
                            if (x <= 0) {
                                System.out.println("lg的作用对象只能是(0,∞)的数，\"" + value + "\"不在范围之内！");
                                return null;
                            }

                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(lg(value) - Math.round(lg(value))) < 1e-15) {
                                double value_round = Math.round(lg(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(lg(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("lg" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("lg" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("lg π" + " = " + list.peek() + "   π = " + PI);
                                        logging("lg π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("lg e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("lg e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("lg (" + value + ") = " + list.peek());
                                    logging("lg (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }
                        }//lg

                        /* ln */
                        else if (obj.equals("ln")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* value的取值范围是(0, ∞) */
                            double x = value instanceof Double ? (Double) value : (Integer) value;
                            if (x <= 0) {
                                System.out.println("ln的作用对象只能是(0,∞)的数，\"" + value + "\"不在范围之内！");
                                return null;
                            }

                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(ln(value) - Math.round(ln(value))) < 1e-15) {
                                double value_round = Math.round(ln(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(ln(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("ln" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("ln" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("ln π" + " = " + list.peek() + "   π = " + PI);
                                        logging("ln π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("ln e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("ln e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("ln (" + value + ") = " + list.peek());
                                    logging("ln (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//ln

                        /* sin */
                        else if (obj.equals("sin")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(sin(value) - Math.round(sin(value))) < 1e-15) {
                                double value_round = Math.round(sin(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(sin(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                String value_PI = pify(value);//以π为因子的值
                                if (var instanceof Character) {
                                    System.out.println("sin" + var + " = " + list.peek() + "   " + var + "=" + value + "   " + var + "=" + value_PI);
                                    logging("sin" + var + " = " + list.peek() + "   " + var + "=" + value + "   " + var + "=" + value + PI);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("sin π" + " = " + list.peek() + "   π = " + PI);
                                        logging("sin π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("sin e" + " = " + list.peek() + "   e = " + pify(Eu));
                                        logging("sin e" + " = " + list.peek() + "   e = " + pify(Eu));
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("sin (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                    logging("sin (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//sin

                        /* cos */
                        else if (obj.equals("cos")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(cos(value) - Math.round(cos(value))) < 1e-15) {
                                double value_round = Math.round(cos(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(cos(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                String value_PI = pify(value);//以π为因子的值
                                if (var instanceof Character) {
                                    System.out.println("cos" + var + " = " + list.peek() + "   " + var + " = " + value + "   " + var + "=" + value + PI);
                                    logging("cos" + var + " = " + list.peek() + "   " + var + " = " + value + "   " + var + "=" + value + PI);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("cos π" + " = " + list.peek() + "   π = " + PI);
                                        logging("cos π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("cos e" + " = " + list.peek() + "   e = " + pify(Eu));
                                        logging("cos e" + " = " + list.peek() + "   e = " + pify(Eu));
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("cos (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                    logging("cos (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//cos

                        /* tan */
                        else if (obj.equals("tan")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(tan(value) - Math.round(tan(value))) < 1e-15) {
                                double value_round = Math.round(tan(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(tan(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                String value_PI = pify(value);//以π为因子的值
                                if (var instanceof Character) {
                                    System.out.println("tan" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("tan" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("tan π" + " = " + list.peek() + "   π = " + PI);
                                        logging("tan π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("tan e" + " = " + list.peek() + "   e = " + pify(Eu));
                                        logging("tan e" + " = " + list.peek() + "   e = " + pify(Eu));
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("tan (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                    logging("tan (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//tan

                        /* sec */
                        else if (obj.equals("sec")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(sec(value) - Math.round(sec(value))) < 1e-15) {
                                double value_round = Math.round(sec(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(sec(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                String value_PI = pify(value);//以π为因子的值
                                if (var instanceof Character) {
                                    System.out.println("sec" + var + " = " + list.peek() + "   " + var + " = " + value + "   " + var + "=" + value + PI);
                                    logging("sec" + var + " = " + list.peek() + "   " + var + " = " + value + "   " + var + "=" + value + PI);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("sec π" + " = " + list.peek() + "   π = " + PI);
                                        logging("sec π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("sec e" + " = " + list.peek() + "   e = " + pify(Eu));
                                        logging("sec e" + " = " + list.peek() + "   e = " + pify(Eu));
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("sec (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                    logging("sec (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//sec

                        /* csc */
                        else if (obj.equals("csc")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(csc(value) - Math.round(csc(value))) < 1e-15) {
                                double value_round = Math.round(csc(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(csc(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                String value_PI = pify(value);//以π为因子的值
                                if (var instanceof Character) {
                                    System.out.println("csc" + var + " = " + list.peek() + "   " + var + " = " + value + "   " + var + "=" + value + PI);
                                    logging("csc" + var + " = " + list.peek() + "   " + var + " = " + value + "   " + var + "=" + value + PI);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("csc π" + " = " + list.peek() + "   π = " + PI);
                                        logging("csc π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("csc e" + " = " + list.peek() + "   e = " + pify(Eu));
                                        logging("csc e" + " = " + list.peek() + "   e = " + pify(Eu));
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("csc (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                    logging("csc (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//csc

                        /* cot */
                        else if (obj.equals("cot")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(cot(value) - Math.round(cot(value))) < 1e-15) {
                                double value_round = Math.round(cot(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(cot(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                String value_PI = pify(value);//以π为因子的值
                                if (var instanceof Character) {
                                    System.out.println("cot" + var + " = " + list.peek() + "   " + var + " = " + value + "   " + var + "=" + value + PI);
                                    logging("cot" + var + " = " + list.peek() + "   " + var + " = " + value + "   " + var + "=" + value + PI);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("cot π" + " = " + list.peek() + "   π = " + PI);
                                        logging("cot π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("cot e" + " = " + list.peek() + "   e = " + pify(Eu));
                                        logging("cot e" + " = " + list.peek() + "   e = " + pify(Eu));
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("cot (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                    logging("cot (" + value_PI + ") = " + list.peek() + "   " + value_PI + " = " + value);
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//cot

                        /* abs */
                        else if (obj.equals("abs")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(abs(value) - Math.round(abs(value))) < 1e-15) {
                                double value_round = Math.round(abs(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(abs(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("|" + var + "| = " + list.peek() + "   " + var + " = " + value);
                                    logging("|" + var + "| = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("|π|" + " = " + list.peek() + "   π = " + PI);
                                        logging("|π|" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("|e|" + " = " + list.peek() + "   e = " + Eu);
                                        logging("|e|" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("|" + value + "| = " + list.peek());
                                    logging("|" + value + "| = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//abs

                        /* exp */
                        else if (obj.equals("exp")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(exp(value) - Math.round(exp(value))) < 1e-15) {
                                double value_round = Math.round(exp(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(exp(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("exp" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("exp" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("exp π" + " = " + list.peek() + "   π = " + PI);
                                        logging("exp π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("exp e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("exp e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("exp (" + value + ") = " + list.peek());
                                    logging("exp (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//exp

                        /* gcd */
                        else if (obj.equals("gcd")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value1 = list.pop();//获取计算值
                            Object var1 = varTrack.pop();//追踪
                            Object value2 = list.pop();//获取计算值
                            Object var2 = varTrack.pop();//追踪
                            list.push(gcd(value2, value1));//push计算结果
                            varTrack.push(list.peek());//跟踪

                            if(ifShowProcess) {
                                /* 输出计算结果 */
                                int flag = 0;
                                if (var1 instanceof Character) flag += 1;//权重为1
                                if (var2 instanceof Character) flag += 2;//权重为2
                                int y = value1 instanceof Integer ? (Integer) value1 : (int) ((double) ((Double) value1));
                                int x = value2 instanceof Integer ? (Integer) value2 : (int) ((double) ((Double) value2));
                                switch (flag) {
                                    case 0: {
                                        System.out.println(x + " gcd " + y + " = " + list.peek());
                                        logging(x + " gcd " + y + " = " + list.peek());
                                        break;
                                    }
                                    case 1: {
                                        System.out.println(var2 + "(" + x + ") gcd " + y + " = " + list.peek());
                                        logging(var2 + "(" + x + ") gcd " + y + " = " + list.peek());
                                        break;
                                    }
                                    case 2: {
                                        System.out.println(x + " gcd " + var1 + "（" + y + ") = " + list.peek());
                                        logging(x + " gcd " + var1 + "（" + y + ") = " + list.peek());
                                        break;
                                    }
                                    case 3: {
                                        System.out.println(var2 + "(" + x + ") gcd " + var1 + "（" + y + ") = " + list.peek());
                                        logging(var2 + "(" + x + ") gcd " + var1 + "（" + y + ") = " + list.peek());
                                        break;
                                    }
                                }
                            }//ifShowProcess
                        }//gcd

                        /* lcm */
                        else if (obj.equals("lcm")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value1 = list.pop();//获取计算值
                            Object var1 = varTrack.pop();//追踪
                            Object value2 = list.pop();//获取计算值
                            Object var2 = varTrack.pop();//追踪
                            list.push(lcm(value2, value1));//push计算结果
                            varTrack.push(list.peek());//跟踪

                            if(ifShowProcess) {
                                /* 输出计算结果 */
                                int flag = 0;
                                if (var1 instanceof Character) flag += 1;//权重为1
                                if (var2 instanceof Character) flag += 2;//权重为2
                                int y = value1 instanceof Integer ? (Integer) value1 : (int) ((double) ((Double) value1));
                                int x = value2 instanceof Integer ? (Integer) value2 : (int) ((double) ((Double) value2));
                                switch (flag) {
                                    case 0: {
                                        System.out.println(x + " lcm " + y + " = " + list.peek());
                                        logging(x + " lcm " + y + " = " + list.peek());
                                        break;
                                    }
                                    case 1: {
                                        System.out.println(var2 + "(" + x + ") lcm " + y + " = " + list.peek());
                                        logging(var2 + "(" + x + ") lcm " + y + " = " + list.peek());
                                        break;
                                    }
                                    case 2: {
                                        System.out.println(x + " lcm " + var1 + "（" + y + ") = " + list.peek());
                                        logging(x + " lcm " + var1 + "（" + y + ") = " + list.peek());
                                        break;
                                    }
                                    case 3: {
                                        System.out.println(var2 + "(" + x + ") lcm " + var1 + "（" + y + ") = " + list.peek());
                                        logging(var2 + "(" + x + ") lcm " + var1 + "（" + y + ") = " + list.peek());
                                        break;
                                    }
                                }
                            }//ifShowProcess
                        }//lcm

                        /* pify,将值转换成以PI为因子的式子，pify只能在最后处理结果时使用 */
                        else if (obj.equals("pify")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            String finalResult = pify(value);//以π为因子的值

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("pify" + var + " = " + finalResult + "   " + var + " = " + value);
                                    logging("pify" + var + " = " + finalResult + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("pify π" + " = " + finalResult);
                                        logging("pify π" + " = " + finalResult);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("pify e" + " = " + finalResult + "   e = " + Eu);
                                        logging("pify e" + " = " + finalResult + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("pify (" + value + ") = " + finalResult);
                                    logging("pify (" + value + ") = " + finalResult);
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess

                            if (i < length - 1)
                                System.out.println("pify只能在最后处理结果时使用或单独使用！程序将自动将此时的结果作为最终结果.");
                            return finalResult;
                        }//pify

                        /* frac,将值转换成分式，frac只能在最后处理结果时使用 */
                        else if (obj.equals("frac")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* value的取值范围是[0,∞) */
                            double x = value instanceof Double ? (Double) value : (Integer) value;
                            if (x < 0) {
                                System.out.println("负数无法开根号，frac的作用对象只能是[0,∞)的数，\"" + value + "\"不在范围之内！");
                                return null;
                            }

                            String finalResult = frac(value, maxDenominator);//以π为因子的值

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("frac" + var + " = " + finalResult + "   " + var + " = " + value);
                                    logging("frac" + var + " = " + finalResult + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("frac π" + " = " + finalResult + "   π = " + PI);
                                        logging("frac π" + " = " + finalResult + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("frac e" + " = " + finalResult + "   e = " + Eu);
                                        logging("frac e" + " = " + finalResult + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("frac (" + value + ") = " + finalResult);
                                    logging("frac (" + value + ") = " + finalResult);
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess

                            if (i < length - 1)
                                System.out.println("frac只能在最后处理结果时使用或单独使用！程序将自动将此时的结果作为最终结果.");
                            return finalResult;
                        }//frac

                        /* sqrt */
                        else if (obj.equals("sqrt")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* value的取值范围是[0, ∞] */
                            double x = value instanceof Double ? (Double) value : (Integer) value;
                            if (x < 0) {
                                System.out.println("sqrt的作用对象只能是[0,∞)的数，\"" + value + "\"不在范围之内！");
                                return null;
                            }

                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(sqrt(value) - Math.round(sqrt(value))) < 1e-15) {
                                double value_round = Math.round(sqrt(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(sqrt(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("sqrt" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("sqrt" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("sqrt π" + " = " + list.peek() + "   π = " + PI);
                                        logging("sqrt π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("sqrt e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("sqrt e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("sqrt (" + value + ") = " + list.peek());
                                    logging("sqrt (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//sqrt

                        /* arsh */
                        else if (obj.equals("arsh")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(arsh(value) - Math.round(arsh(value))) < 1e-15) {
                                double value_round = Math.round(arsh(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            list.push(arsh(value));//push计算结果
                            varTrack.push(list.peek());//跟踪

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("arsh" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("arsh" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("arsh π" + " = " + list.peek() + "   π = " + PI);
                                        logging("arsh π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("arsh e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("arsh e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("arsh (" + value + ") = " + list.peek());
                                    logging("arsh (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//arsh

                        /* arch */
                        else if (obj.equals("arch")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(arch(value) - Math.round(arch(value))) < 1e-15) {
                                double value_round = Math.round(arch(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            list.push(arch(value));//push计算结果
                            varTrack.push(list.peek());//跟踪

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("arch" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("arch" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("arch π" + " = " + list.peek() + "   π = " + PI);
                                        logging("arch π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("arch e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("arch e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("arch (" + value + ") = " + list.peek());
                                    logging("arch (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//arch

                        /* arth */
                        else if (obj.equals("arth")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* value的取值范围是[-1,1] */
                            double x = value instanceof Double ? (Double) value : (Integer) value;
                            if (x < -1 || x > 1) {
                                System.out.println("arth的作用对象只能是[-1,1]的数，\"" + value + "\"不在范围之内！");
                                return null;
                            }
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(arth(value) - Math.round(arth(value))) < 1e-15) {
                                double value_round = Math.round(arth(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            list.push(arth(value));//push计算结果
                            varTrack.push(list.peek());//跟踪

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("arth" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("arth" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("arth π" + " = " + list.peek() + "   π = " + PI);
                                        logging("arth π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("arth e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("arth e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("arth (" + value + ") = " + list.peek());
                                    logging("arth (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//arth

                        /* ceil */
                        else if (obj.equals("ceil")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            list.push(ceil(value));//push计算结果
                            varTrack.push(list.peek());//跟踪

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("ceil" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("ceil" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("ceil π" + " = " + list.peek() + "   π = " + PI);
                                        logging("ceil π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("ceil e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("ceil e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("ceil (" + value + ") = " + list.peek());
                                    logging("ceil (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//ceil

                        /* floor */
                        else if (obj.equals("floor")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            list.push(floor(value));//push计算结果
                            varTrack.push(list.peek());//跟踪

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("floor" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("floor" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("floor π" + " = " + list.peek() + "   π = " + PI);
                                        logging("floor π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("floor e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("floor e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("floor (" + value + ") = " + list.peek());
                                    logging("floor (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//floor

                        /* round */
                        else if (obj.equals("round")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            list.push(round(value));//push计算结果
                            varTrack.push(list.peek());//跟踪

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("round" + var + " = " + list.peek() + "   " + var + " = " + value);
                                    logging("round" + var + " = " + list.peek() + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("round π" + " = " + list.peek() + "   π = " + PI);
                                        logging("round π" + " = " + list.peek() + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("round e" + " = " + list.peek() + "   e = " + Eu);
                                        logging("round e" + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("round (" + value + ") = " + list.peek());
                                    logging("round (" + value + ") = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//round

                        /* prime,判断一个数是否是质数 */
                        else if (obj.equals("prime")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* value的取值范围是[0,∞) */
                            int x;
                            if (value instanceof Double) {
                                System.out.println(value + "为小数，程序将自动对其向下取整再判断它是否是整数");
                                x = (int) ((double) ((Double) value));
                            } else x = (Integer) value;
                            if (x < 0) {
                                System.out.println("prime的作用对象必须是正数，" + value + "不是正数！");
                                return null;
                            }

                            boolean isPrime = prime(x);

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("frac" + var + " = " + isPrime + "   " + var + " = " + value);
                                    logging("frac" + var + " = " + isPrime + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("prime π" + " = " + isPrime + "   π = " + PI);
                                        logging("prime π" + " = " + isPrime + "   π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("prime e" + " = " + isPrime + "   e = " + Eu);
                                        logging("prime e" + " = " + isPrime + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("prime (" + value + ") = " + isPrime);
                                    logging("prime (" + value + ") = " + isPrime);
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess

                            if (i < length - 1)
                                System.out.println("prime只能在最后处理结果时使用或单独使用！程序将自动将此时的结果作为最终结果.");
                            return isPrime;
                        }//prime

                        /* arcsin */
                        else if (obj.equals("arcsin")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* value的取值范围是[-1,1] */
                            double x = value instanceof Double ? (Double) value : (Integer) value;
                            if (x < -1 || x > 1) {
                                System.out.println("arcsin的作用对象只能是[-1,1]的数，\"" + value + "\"不在范围之内！");
                                return null;
                            }
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(arcsin(value) - Math.round(arcsin(value))) < 1e-15) {
                                double value_round = Math.round(th(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            }
                            list.push(arcsin(value));//push计算结果
                            varTrack.push(list.peek());//跟踪

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                String res = pify(list.peek());
                                if (var instanceof Character) {
                                    System.out.println("arcsin" + var + " = " + res + "   " + var + "=" + value + "   " + res + "=" + list.peek());
                                    logging("arcsin" + var + " = " + res + "   " + var + "=" + value + "   " + res + "=" + list.peek());
                                }//参数为变量的处理
                                else {
                                    System.out.println("arcsin (" + value + ") = " + res + "   " + res + " = " + list.peek());
                                    logging("arcsin (" + value + ") = " + res + "   " + res + " = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//arcsin

                        /* arccos */
                        else if (obj.equals("arccos")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            /* value的取值范围是[-1,1] */
                            double x = value instanceof Double ? (Double) value : (Integer) value;
                            if (x < -1 || x > 1) {
                                System.out.println("arccos的作用对象只能是[-1,1]的数，\"" + value + "\"不在范围之内！");
                                return null;
                            }
                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(arccos(value) - Math.round(arccos(value))) < 1e-15) {
                                double value_round = Math.round(th(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(arccos(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                String res = pify(list.peek());//以π为因子的值
                                if (var instanceof Character) {
                                    System.out.println("arccos" + var + " = " + res + "   " + var + "=" + value + "   " + res + "=" + list.peek());
                                    logging("arccos" + var + " = " + res + "   " + var + "=" + value + "   " + res + "=" + list.peek());
                                }//参数为变量的处理
                                else {
                                    System.out.println("arccos (" + value + ") = " + res + "   " + res + " = " + list.peek());
                                    logging("arccos (" + value + ") = " + res + "   " + res + " = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//arccos

                        /* arctan */
                        else if (obj.equals("arctan")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            /* 如果计算结果无限接近于某个整数，进行取整值 */
                            if (Math.abs(arctan(value) - Math.round(arctan(value))) < 1e-15) {
                                double value_round = Math.round(arctan(value));
                                list.push(value_round);
                                varTrack.add(value_round);
                            } else {
                                list.push(arctan(value));//push计算结果
                                varTrack.push(list.peek());//跟踪
                            }

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                String res = pify(list.peek());//以π为因子的值
                                if (var instanceof Character) {
                                    System.out.println("arctan" + var + " = " + res + "   " + var + "=" + value + "   " + res + "=" + list.peek());
                                    logging("arctan" + var + " = " + res + "   " + var + "=" + value + "   " + res + "=" + list.peek());
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("arctan π" + " = " + res + "   " + res + "=" + list.peek());
                                        logging("arctan π" + " = " + res + "   " + res + "=" + list.peek());
                                    } else if (var.equals("Eu")) {
                                        System.out.println("arctan e" + " = " + res + "   " + res + "=" + list.peek());
                                        logging("arctan e" + " = " + res + "   " + res + "=" + list.peek());
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("arctan (" + value + ") = " + res + "   " + res + " = " + list.peek());
                                    logging("arctan (" + value + ") = " + res + "   " + res + " = " + list.peek());
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess
                        }//arctan

                        /* factor,素因子分解，factor只能单独使用 */
                        else if (obj.equals("factor")) {
                            /* 获取计算值和函数的参数对象 */
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪

                            int x;
                            if (value instanceof Double) {
                                System.out.println(value + "为小数，程序将把" + value + "向下取整再进行素因子分解");
                                x = floor(value);
                            } else
                                x = (Integer) value;

                            if (x < 0) {
                                System.out.println(x + "为负数，程序将把" + x + "取绝对值再进行素因子分解");
                                x = -x;
                            }

                            String finalResult = factor(x);//素因子分解

                            if(ifShowProcess) {
                                /* 输出计算过程 */
                                if (var instanceof Character) {
                                    System.out.println("factor" + var + " = " + finalResult + "   " + var + " = " + value);
                                    logging("factor" + var + " = " + finalResult + "   " + var + " = " + value);
                                }//参数为变量的处理
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("factor π" + " = " + finalResult);
                                        logging("factor π" + " = " + finalResult);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("factor e" + " = " + finalResult + "   e = " + Eu);
                                        logging("factor e" + " = " + finalResult + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                }//参数为π和e的处理
                                else {
                                    System.out.println("factor (" + value + ") = " + finalResult);
                                    logging("factor (" + value + ") = " + finalResult);
                                }//参数为double或int数值类型的处理
                            }//ifShowProcess

                            if (i < length - 1)
                                System.out.println("factor只能单独使用！程序将自动将此时的结果作为最终结果.");
                            return finalResult;
                        }//factor
                    }//输出所有函数的计算过程
                }//处理函数

                /* 对象是字符的实例 */
                else if (obj instanceof Character) {
                    //System.out.println("Character:" + obj);
                    char ch = (char) obj;
                    if (ch >= 'a' && ch <= 'z') {
                        if (varSet_az_value[ch - 'a'] == null) {
                            System.out.printf("变量%c未进行赋值！\n", ch);
                            return null;
                        }
                        list.push(varSet_az_value[ch - 'a']);
                        varTrack.push(ch);//跟踪
                    } else if (ch >= 'A' && ch <= 'Z') {
                        if (varSet_AZ_value[ch - 'A'] == null) {
                            System.out.printf("变量%c未进行赋值！\n", ch);
                            return null;
                        }
                        list.push(varSet_AZ_value[ch - 'A']);
                        varTrack.push(ch);//跟踪
                    } else if (isOperator(ch)) {
                        if (ch == '!' || ch == '！') {
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            //value必须大于等于0
                            if ((value instanceof Integer ? (Integer) value : (Double) value) < 0) {
                                System.out.println("阶乘只能应用于大于0的数，\"" + value + "\"为小于0的数！");
                                return null;
                            }
                            list.push(fact(value));//push计算结果
                            varTrack.push(fact(value));//跟踪
                            /* 输出计算过程 */
                            if (ifShowProcess) {
                                if (var instanceof Character) {
                                    System.out.println(var + "(" + value + ")" + ch + " = " + list.peek());
                                    logging(var + "(" + value + ")" + ch + " = " + list.peek());
                                }
                                /* 常量Math.PI或Math.E */
                                else if (var instanceof String) {
                                    if (var.equals("PI")) {
                                        System.out.println("π" + ch + " = " + list.peek() + "   π = " + PI);
                                        logging("π" + ch + " = " + list.peek() + "  π = " + PI);
                                    } else if (var.equals("Eu")) {
                                        System.out.println("e" + ch + " = " + list.peek() + "   e = " + Eu);
                                        logging("e" + ch + " = " + list.peek() + "   e = " + Eu);
                                    } else {
                                        System.out.println("出现无法识别的符号：" + var);
                                        logging("出现无法识别的符号：" + var);
                                        return null;
                                    }
                                } else {
                                    System.out.println(value + "" + ch + " = " + list.peek());
                                    logging(value + "" + ch + " = " + list.peek());
                                }
                            }
                        }
                        /* 如果为负号，~表示负号 */
                        else if(ch == '~'){
                            Object value = list.pop();//获取计算值
                            Object var = varTrack.pop();//追踪
                            Integer intValue = null;
                            Double doubleValue = null;
                            if(value instanceof Integer)
                                intValue = (Integer) value;
                            else
                                doubleValue = (Double) value;
                            if(intValue != null) {
                                list.push(-intValue);//push计算结果
                                varTrack.push(-intValue);//跟踪
                            }
                            else {
                                list.push(-doubleValue);//push计算结果
                                varTrack.push(-doubleValue);//跟踪
                            }
                        }
                        /* 处理出现的负数，当减号单独使用时表示负数 */
                        else {
                            int flag = 0;//用于判断value1和value2的类型
                            Object value1, value2 = null;
                            Object obj1, obj2;
                            value1 = list.pop();//权值1，Integer为1，Double为0
                            obj1 = varTrack.pop();//跟踪
                            if (!list.isEmpty()) {
                                value2 = list.pop();//权值2，Integer为1，Double为0
                                obj2 = varTrack.pop();//跟踪
                            } else {
                                System.out.println("警告：此处\'" + ch +"\'运算无法取得足够参数！仅有一个参数[" + value1 +"]无法通过\'" + ch +"\'进行运算");
                                continue;//此时无法取得足够参数，跳过计算，继续获取参数
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
                                case '?', '？':{
                                    list.push(max(value2,value1));
                                    break;
                                }
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
                                    if (abs(value1) < 1e-6) {
                                        System.out.println("除数不能为0！在\"" + value2 + " / " + value1 + "\"的计算中除数为0.");
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
                                    if (value2 instanceof Double)
                                        System.out.println("在" + value2 + " % " + value1 + "的计算中，\"" + value2 + "\"为小数，已自动将计算转为除法运算.");
                                    if (value2 instanceof Double)
                                        System.out.println("在" + value2 + " % " + value1 + "的计算中，\"" + value1 + "\"为小数，已自动将计算转为除法运算.");
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
                            /* 输出计算过程 */
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
                    } else {
                        System.out.printf("无法找到%c的定义\n", ch);
                        logging("无法找到" + ch + "的定义");
                        return null;
                    }
                }

            }//遍历逆波兰表达式
        }//try块
        catch(Exception ex){
            throw ex;
        }
        if(list.size() == 0){
            System.out.println("逆波兰表达式\"" + listToString(postfixNotation) + "\"存在错误！");
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
        if(ifShowProcess) {
            System.out.println("计算过程：");
            logging("计算过程：");
        }
        ArrayList postfixNotation;
        try {
            postfixNotation = (ArrayList) antiPolish(expression);
        }catch(Exception ex){
            System.out.println("您所输入的计算表达式\"" + expression + "\"无法正确转换为逆波兰表达式！");
            return;
        }

        Object result;
        try {
            result = antiPolishEvaluate(postfixNotation);
        }catch(Exception ex){
            System.out.println("您所输入的计算表达式\"" + expression + "\"无法计算！");
            return;
        }

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
        if(ifShowProcess) {
            logging("计算过程：");
            System.out.println("计算过程：");
        }
        ArrayList  postfixNotation;
        try {
            postfixNotation = (ArrayList) antiPolish(expression);
        }catch(Exception ex){
            System.out.println("您所输入的计算表达式\"" + expression + "\"无法正确转换为逆波兰表达式！");
            return;
        }

        Object result;
        try {
            result = antiPolishEvaluate(postfixNotation);
        }catch(Exception ex){
            System.out.println("您所输入的计算表达式\"" + expression + "\"无法计算！");
            return;
        }

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

    static void func(){
        System.out.println("sh      双曲正弦函数,sh x = (e^x - 1/e^x) / 2，定义域(-∞,∞)，值域(-∞,∞)");
        System.out.println("ch      双曲余弦函数,ch x = (e^x + 1/e^x) / 2，定义域(-∞,∞)，值域[1,∞]");
        System.out.println("th      双曲正切，th = sh / ch = (e^x - 1/e^x)/(e^x + 1/e^x)，定义域(-∞,∞)，值域(-1,1)");
        System.out.println("lg      以10为底的对数，定义域(0,∞)，值域(-∞,∞)");
        System.out.println("ln      以自然常数e为底的对数，定义域(0,∞)，值域(-∞,∞)");
        System.out.println("sin     三角正弦函数,定义域(-∞,∞)，值域[-1,1]");
        System.out.println("cos     三角余弦函数,定义域(-∞,∞)，值域[-1,1]");
        System.out.println("tan     三角正切函数,定义域{x|x != π/2 + kπ,k可取所有整数} 值域(-∞,∞)");
        System.out.println("sec     正割函数,sec x = 1 / cos x定义域(-∞,∞)，值域[-∞,-1]和[1,∞]");
        System.out.println("csc     余割函数,csc x = 1 / sin x,定义域(-∞,∞)，值域[∞,-1]和[1,∞]");
        System.out.println("cot     余切函数,cot x = 1 / tan x,定义域{x|x != kπ,x != π/2 + kπ,k可取所有整数} 值域(-∞,∞)");
        System.out.println("abs     取绝对值，abs x = |x|,定义域(-∞,∞)，值域[0,∞)");
        System.out.println("exp     计算e的x次方，exp(x) = e ^ x,定义域(-∞,∞)，值域(0,∞)");
        System.out.println("gcd     求两个数的最大公约数,如果参数为小数，那么将自动将该参数向下取整再进行计算");
        System.out.println("lcm     求两个数的最小公倍数,如果参数为小数，那么将自动将该参数向下取整再进行计算");
        System.out.println("pify    将一个数转为以π为因子的式子,例如pify(1.75*π) = π + (3/4)*π,pify只能单独使用");
        System.out.println("frac    将一个数转换成分式，例如frac(3.75) = 15/4,frac(0.4166666666) = 5/12,frac只能单独使用");
        System.out.println("sqrt    sqrt x = x ^ 0.5,计算正平方根,例如sqrt(36) = 6");
        System.out.println("arsh    反双曲正弦函数,arsh = ln(x + sqrt(x ^ 2 + 1)),定义域(-∞,∞)，值域(-∞,∞)");
        System.out.println("arch    反双曲余弦函数,arch = ln(x + sqrt(x ^ 2 - 1)),定义域(-∞,∞)，值域[0,∞)");
        System.out.println("arth    反三角正切函数,arth = ln[(1+x)/(1-x)] / 2,定义域[-1,1]，值域(-∞,∞)");
        System.out.println("ceil    向上取整,ceil x得到最接近x且大于x的整数,例如：ceil 3.1 = 4,ceil(-3.5) = -3");
        System.out.println("floor   向下取整,ceil x得到最接近x且小于x的整数,例如：floor 3.9 = 3,floor(-3.1) = -4");
        System.out.println("round   四舍五入,例如：round 2.4 = 2,round 2.5 = 3,round(-2.5) = -2");
        System.out.println("prime   判断一个数是否为素数,prime只能单独使用");
        System.out.println("arcsin  反三角正弦函数,定义域(-∞,∞)，值域(-π/2,π/2)");
        System.out.println("arccos  反三角余弦函数,定义域(-∞,∞)，值域(-π/2,π/2)");
        System.out.println("arctan  反三角正切函数,定义域{x|x!= π/2 + kπ,k可取所有整数}，值域(-∞,∞)");
        System.out.println("factor  素因子分解，将一个数分解成多个素数的乘积,例如factor (72) = (2^3) * (3^2),factor只能单独使用");
        System.out.println("""
                sh、ch、th、lg、ln、sin、cos、tan、sec、csc、cot、abs、exp、pify、frac、sqrt、arsh、arch、arth、ceil、floor、
                round、prime、arcos、arctan、facrot(即除gcd、lcm以外的全部函数)这些函数的使用方式都是："函数名 参数"的形式，只需要在函数名后面跟一个属于它的参数即可，
                中间的空格可以取消，例如：arctan1、lg100、pify20等等。使用案例：
                求以10为底，100的对数：lg 100  计算结果：2
                求以e为底，e平方的对数：ln (Eu ^ 2) 或者 ln(Eu * Eu)  计算结果：2
                求根号10：sqrt 10  计算结果：3.1622776601683795
                判断247是否是素数：prime 247  计算结果：false
                求π/6的正弦值：sin(PI/6)  计算结果：0.49999999999999994
                求-3.5的绝对值：abs(-3.5)  计算结果：3.5
                将247进行素因子分解：factor 247  计算结果：13 * 19
                将0.4166666666666667转换为分式：frac 0.4166666666666667  计算结果：5/12
                将1.3089969389957472转换为带π的分式：pify 1.3089969389957472  计算结果：(5/12)π
                在全部的函数中，只有gcd和lcm为二元函数，它们的使用方式是："参数1 函数名 参数2",空格同样可以忽略，使用案例：
                求72与36的最大公约数：108 gcd 60或者60 gcd 108  计算结果：12
                求120与24的最小公倍数：35 lcm 28或者28 lcm 35  计算结果：140
                所有函数中，prime、pify、frac、factor这些函数的使用有限制，由于它们的计算结果不能继续参与运算，所有它们一般单独使用或者
                在计算式的最后调用，例如：
                单独使用：prime 247、pify Eu、frac 2.75、factor 284
                在计算式的最后调用：
                prime(23 * 7 + lg 100)  计算结果：true
                pify(arcsin(sqrt(3) / 2))  计算结果：(1/3)π
                factor(12 * 7 + 4 ^ 5 - round(Eu * PI))  计算结果：7 * 157
                """);
    }

    static void help(){
        System.out.println("                                     逆波计算器的使用说明");
        System.out.println("==========================================================================================================");
        System.out.println("计算器通过输入命令调用不同的功能，共有11个命令：exit、adcg、read、save、var、antipo、ls、func、poly、help、config、");
        System.out.println("exit    退出程序");
        System.out.println("""
                adcg    添加或设置变量，变量名只能是大小写字母，变量未赋值时初始为null，程序根据你提交的变量集对对应的变量进行赋值
                        输入命令adcg后，只需要输入赋值语句即可，在赋值语句最后输入一个#表示结束输入，
                        赋值语句语法： 变量名 = 值
                        例如输入：a = 1 b = 2 c = 3 d = 4#   这将给变量a、b、c、d分别赋值1、2、3、4
                        在输入中可以随意使用空格、tab键、换行，程序都会自动忽视
                        例如：
                        ”a = 1
                         b = 2           c = 3
                                  d = 4"
                         与"a=1b=2c=3d=4”是等效的。
                        输入adcg，输入多条赋值语句，最后在其最后面输入#结束输入，程序便会会自动提交你输入的变量集。
                """);
        System.out.println("read    将根据你输入的指定路径读取变量集并给对应的变量进行赋值，输入read后根据程序的提示来操作即可");
        System.out.println("save    将根据你输入的指定路径读取变量集并给对应的变量进行赋值，输入save后根据程序的提示来操作即可");
        System.out.println("var     显示所有已经进行赋值过的变量");
        System.out.println("""
                antipo  输入计算表达式进行计算，程序将会给出它的每一步计算过程以及计算结果
                        程序计算支持下列运算符：{
                             比较大小(?/？), 加法+, 减法-, 乘法*, 除法/, 取余运算%, 幂运算^,阶乘运算(!/！)
                        }
                        同时支持下列函数运算符：{
                             以10为底的对数lg, 以e为底的对数ln, 双曲正弦函数sh, 双曲余弦函数ch, 双曲正切th
                             三角正弦函数sin, 三角余弦函数cos, 三角正切函数tan, 正割函数sec, 余割函数csc, 余切函数cot、
                             取绝对值abs, 计算e的x次方exp, 求最大公约数gcd, 求最小公倍数lcm,
                             将一个数转为以π为因子的式子pify, 将一个数转为分式frac, 正平方根sqrt,
                             反双曲正弦函数arsh, 反双曲余弦函数arch, 反双曲正切函数arth,
                             向上取整ceil, 向下取整floor, 四舍五入round, 判断素数prime,
                             反三角正弦函数arcsin, 反三角余弦函数arccos, 反三角正切函数arctan
                        }
                        对于sin、cos、tan等等这些函数运算符，不限制大小写，程序会自动将字母统一转换成小写再处理
                        运算优先级：{? ？} < {+ -} < {* / %} < {^} < {!} = {所有函数运算符}
                        计算支持两大常数：圆周率π用PI表示、自然常数e用Eu表示
                        支持使用中英文圆括号()/（）、方括号[]、来改变运算顺序，注意中英文括号可以混合使用，例如可以左边为中文括号右边为英文括号。
                """);
        System.out.println("""
                ls      列出所有之前输入的计算表达式，以时间+计算表达式的形式呈现，例如：
                        11:14:38   sin PI + cos PI
                        11:15:59   sin(PI / 2 + 3.5 * PI) + cos(PI - 0.25 * PI)
                """);
        System.out.println("func     查看所有函数的详细信息");
        System.out.println("poly     使用多项式计算器，支持多项式的加、减、乘、幂运算");
        System.out.println("help     查看帮助信息");
        System.out.println("config   修改配置信息");
        System.out.println("==========================================================================================================");
        System.out.println("快捷计算   当你输入的命令是一条计算表达式且其中不存在变量时，程序会自动将其识别出并计算该计算表达式");
        System.out.println("""
                运算符   中英文问号(?,？)可以比较两个数的大小，x ? y得到x、y之间的最大值,例如ln(10 ? 20)得到的是ln 20
                        除问号外，加法+, 减法-, 乘法*, 除法/, 取余运算%, 幂运算^,阶乘运算(!/！)这些运算符的使用方法与其在数学中的使用方法相同。
                """);
        System.out.println("""
                变量     程序将大小写字母都视作变量，变量可以通过adcg命令进行赋值和修改值，未赋值的变量不能使用，
                        如果计算中使用了未赋值的变量，将会导致计算中断。
                """);
        System.out.println("""
                变量集   在变量集中空格和回车等等一些空字符会被自动忽略，你可以自由使用空字符来使得你的变量集简洁、美观。
                        使用read命令可以直接编写变量集并提交给程序，save命令可以将程序中所有赋值的变量保存为.txt文件
                        变量集的默认格式为.txt，你只需要按变量集的编写规则编写文本并保存为.txt文件即可通过程序去读取该变量集。
                        下面是一个简单的变量集示例，它给变量a、b、c、d依次赋值为1、2、3、4：
                        a = 1 b = 2 c = 3 d = 4
                """);
        System.out.println("""
                函数    函数的基本使用方法：函数名 参数
                       除()、（）、[]外，函数的优先级是最高的，所有函数拥有相同的优先级。
                       绝大多数函数自动从其右边获取参数，例如lg 2 + 5中2即为lg的参数
                       或者你可以将一个式子作为函数的参数：lg (10 * 10) + 5中(10 * 10)即lg的参数
                       可以通过函数的嵌套将函数的结果作为另一个函数的参数，例如lg[ln(4 * 23)]中ln(4 * 23)为lg的参数
                       一般需要用函数的参数用括号例如()、（）、[]括起来，如果不加括号函数只把它右边的一个值作为参数
                       例如lg 100 + 10中lg函数将100作为它的参数而不是100 + 10，如果想将100 + 10作为lg的参数，应该改成：
                       lg(100 + 10)，括号可以使用中英文括号或方括号。
                """);
        System.out.println("文件信息  程序所有产生的数据文件都在\"" + new File("data").getAbsolutePath() + "\"文件夹中\n" +
                "        在\"" + logPath.getAbsolutePath() + "\"可以查看您的所有使用日志。\n");
        System.out.println("==========================================================================================================");
    }

    static void usage(String key){
        if(key.equals("+")){
            System.out.println("""
                    '+'为加法运算符，使用格式：参数1 + 参数2
                    用例：9 + 4        计算结果：13
                    """);
        }
        else if(key.equals("-")) {
            System.out.println("""
                    '-'为减法运算符，使用格式：参数1 - 参数2
                    用例：9 - 4        计算结果：5
                    """);
        }
        else if(key.equals("*")){
            System.out.println("""
                    '*'为乘法运算符，使用格式：参数1 * 参数2
                    用例：3 * 3        计算结果：9      
                    """);
        }
        else if(key.equals("/")){
            System.out.println("""
                    '/'为除法运算符，使用格式：参数1 / 参数2
                    这里的'/'与数学上的除法有些不同：
                    1.当参数1和参数2都为整数时，进行的是带余除法
                    例如：7 / 3        计算结果：2
                         -12 / 5      计算结果：-2
                         1 / 5        计算结果：0
                    """);
        }
        else if(key.equals("?")) {
            System.out.println("""
                    '?'为比较大小的运算符，使用格式：参数1 ？ 参数2,得到参数1和参数2之间的最大值
                    用例：5。0/12 ？ 0.5  计算结果：0.5
                         Eu ？ 2.7      计算结果：2.718281828459045
                         lg100 ? 2      计算结果：2
                    """);
        }
    }

    static void console() throws IOException {
        Scanner userInput = new Scanner(System.in);
        int option = 6;
        while(option != 0) {
            if(ifShowMenu) {
                System.out.println("                菜单");
                System.out.println("退出 - - - - - - - - - - - - - - - exit");
                System.out.println("添加或修改变量 - - - - - - - - - - - adcg");
                System.out.println("移除变量- - - - - - - - - - - - - - rm");
                System.out.println("读取变量集- - - - - - - - - - - - - read");
                System.out.println("保存当前变量集 - - - - - - - - - - - save");
                System.out.println("查看所有变量 - - - - - - - - - - - - var");
                System.out.println("计算 - - - - - - - - - - - - - - - antipo");
                System.out.println("查看所有输入过的计算表达式 - - - - - - -ls");
                System.out.println("查看所有函数 - - - - - - - - - - - - func");
                System.out.println("多项式计算器 - - - - - - - - - - - - -poly");
                System.out.println("帮助 - - - - - - - - - - - - - - - -help");
                System.out.println("修改配置信息 - - - - - - - - - - - - -config");
                System.out.println("获取使用方法 - - - - - - - - - - - - -usage [key]");
                System.out.println("注：所有命令不限制字母的大小写");
            }
            System.out.print(inputAnchor);
            String command,expression;
            command = userInput.nextLine();
            expression = command;//如果输入的是计算表达式那么保留原始输入的字符串
            command = command.toLowerCase();//命令不限大小写
            if(command.contains("usage")){
                String key = command.replace("usage","");
                usage(key);
                continue;//跳过后面内容，进行下一次命令输入
            }
            option = switch (command) {
                case "exit" -> 0;
                case "adcg" -> 1;
                case "rm" -> 2;
                case "read" -> 3;
                case "save" -> 4;
                case "var" -> 5;
                case "antipo" -> 6;
                case "ls" -> 7;
                case "func" -> 8;
                case "poly" -> 9;
                case "help" -> 10;
                case "config" -> 11;
                default -> 12;
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
                    try {
                        submitVar(varSet);
                    }catch(Exception ex){
                        System.out.println("变量集\"" + varSet + "\"中存在错误！已撤销提交该变量集");
                        break;
                    }
                    System.out.println("提交成功!");
                    break;
                }
                case 2:{
                    remove();
                    logging(new Date() + " 移除变量");
                    break;
                }
                case 3:{
                    read();
                    break;
                }
                case 4:{
                    save();
                    break;
                }
                case 5:{
                    logging(new Date() + " 查看所有变量：\n" + varSetToString());
                    showAllVars();
                    break;
                }
                case 6:{
                    calculate();
                    break;
                }
                case 7:{
                    ls();
                    break;
                }
                case 8:{
                    func();
                    break;
                }
                case 9:{
                    logging(new Date() + "使用多项式计算器");
                    try {
                        PolynomialCalculator.console();
                    }catch(Exception ex){
                        System.out.println("多项式计算器出现错误！程序已安全退出");
                    }
                    break;
                }
                case 10:{
                    logging(new Date() + " 查看帮助信息");
                    help();
                    break;
                }
                case 11:{
                    config();
                    logging(new Date() + "修改配置信息");
                    break;
                }
                default: {
                    /* 如果输入的命令是计算表达式，直接进行计算 */
                    if(isExpression(expression))
                        directCaculate(expression);
                    else {
                        System.out.println("无法识别命令！");
                        logging(new Date() + "无法识别命令：" + command);
                    }
                }
            }
            if(ifShowMenu) {
                System.out.print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
                System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            }
            logging("");
        }
    }

    public static void main(String[] args) throws IOException {
        Initialize();//变量初始化
        console();
    }
}
