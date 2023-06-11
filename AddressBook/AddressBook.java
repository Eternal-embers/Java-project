import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;//追加数据到文件中
import java.io.IOException;//文件输入输出异常
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;//输入不匹配异常处理
import java.util.Scanner;

public class AddressBook{

    Scanner input = new Scanner(System.in);//输入
    static Scanner userInput = new Scanner(System.in);//输入
    public AddressBook() {
    }//构造函数

    /************* 菜单 *************/
    public void menu() {
		/*
		System.out.println("\t______________________________________________________________");
		System.out.println("\t*                                                            *");
		System.out.println("\t*                ___________________________                 *");
		System.out.println("\t*                |                         |                 *");
		System.out.println("\t*                |         通讯录           11|                 *");
		System.out.println("\t*                |_________________________|                 *");
		System.out.println("\t*                菜单：                                       *");
		System.out.println("\t*                —————————————————————————————               *");
		System.out.println("\t*                *                           *               *");
		System.out.println("\t*                *  退出：- - - - - - - -  0  *               *");
		System.out.println("\t*                *                           *               *");
		System.out.println("\t*                *  输入：- - - - - - - -  1  *               *");
		System.out.println("\t*                *                           *               *");
		System.out.println("\t*                *  显示：- - - - - - - -  2  *               *");
		System.out.println("\t*                *                           *               *");
		System.out.println("\t*                *  修改：- - - - - - - -  3  *               *");
		System.out.println("\t*                *                           *               *");
		System.out.println("\t*                *  删除：- - - - - - - -  4  *               *");
		System.out.println("\t*                *                           *               *");
		System.out.println("\t*                *  查询：- - - - - - - -  5  *               *");
		System.out.println("\t*                —————————————————————————————               *");
		System.out.println("\t*                                                            *");
		System.out.println("\t*____________________________________________________________*");
		System.out.print("\t>>");
		*/

        System.out.println("\t                  ___________________________");
        System.out.println("\t                  |                         |");
        System.out.println("\t                  |          通信录          |");
        System.out.println("\t                  |_________________________|");
        System.out.println("\t                  菜单：");
        System.out.println("\t                  *  退出：- - - - - - - -  0\n");
        System.out.println("\t                  *  输入：- - - - - - - -  1\n");
        System.out.println("\t                  *  显示：- - - - - - - -  2\n");
        System.out.println("\t                  *  修改：- - - - - - - -  3\n");
        System.out.println("\t                  *  删除：- - - - - - - -  4\n");
        System.out.println("\t                  *  查询：- - - - - - - -  5\n");
        System.out.print("\t>>");

		/*
		System.out.println("\t\n");
		System.out.println("\t                菜单：");
		System.out.println("\t                * 退出：- - - - - - - -  0\n");
		System.out.println("\t                * 输入：- - - - - - - -  1\n");
		System.out.println("\t                * 显示：- - - - - - - -  2\n");
		System.out.println("\t                * 修改：- - - - - - - -  3\n");
		System.out.println("\t                * 删除：- - - - - - - -  4\n");
		System.out.println("\t                * 查询：- - - - - - - -  5\n");
		System.out.print("\t>>");
		*/
    }

    /************* 完整输入 *************/
    public Person completeInput() {
        String name;//姓名
        String address;//地址
        String postalCode;//邮政编码
        String phoneNumber;//电话号码
        String comment;//备注
        System.out.print("\t姓名：");
        name = input.nextLine();
        System.out.print("\t地址：");
        address = input.nextLine();
        System.out.print("\t邮政编码：");
        postalCode = input.nextLine();
        System.out.print("\t电话号码：");
        phoneNumber = input.nextLine();
        System.out.print("\t备注：");
        comment = input.nextLine();
        return new Person(name,address,postalCode,phoneNumber,comment);
    }
    /************* 快捷输入 *************/
    public Person quickInput() {
        String name;//姓名
        String phoneNumber;//电话号码
        String comment;//备注
        System.out.print("\t姓名：");
        name = input.nextLine();
        System.out.print("\t电话号码：");
        phoneNumber = input.nextLine();
        System.out.print("\t备注：");
        comment = input.nextLine();
        return new Person(name,phoneNumber,comment);
    }

    /************* 保存数据 ***************/
    public void save(FileWriter output,Person person) throws IOException{
        /*
         * 保存顺序：name->address->postalCode->phoneNumber->creationDate->comment
         */
        output.write(person.getName() + "\n");
        output.write(person.getAddress() + "\n");
        output.write(person.getPostalCode() + "\n");
        output.write(person.getPhoneNumber() + "\n");
        output.write(person.getcreationDate() + "\n");
        output.write(person.getComment() + "\n");
        output.flush();
    }//追加

    public void cover(PrintWriter output,Person person) {
        /*
         * 保存顺序：name->address->postalCode->phoneNumber->creationDate->comment
         */
        output.write(person.getName() + "\n");
        output.write(person.getAddress() + "\n");
        output.write(person.getPostalCode() + "\n");
        output.write(person.getPhoneNumber() + "\n");
        output.write(person.getcreationDate() + "\n");
        output.write(person.getComment() + "\n");
        output.flush();
    }//覆盖

    /************* 用户输入 **************/
    public void input() throws IOException,InputMismatchException{
        Person[] person = new Person[50];//person对象
        FileWriter output = new FileWriter("D:\\programming\\eclipse\\AddressBook\\data\\addressBook.txt",true);
        int size = 0;
        System.out.println("\t快捷输入:姓名 - 电话号码 - 备注  >>1");
        System.out.println("\t完整输入:姓名 - 地址 - 邮政编号 - 电话号码 - 备注  >>2");
        System.out.println("\t返回 >>3");
        int flag = 0;
        try {
            System.out.print("\t>>");
            flag = userInput.nextInt();
        }
        catch(InputMismatchException ex) {
            System.out.println("\t>>>>>>> 输入错误，请重新输入！ <<<<<<<");
            System.out.print("\t>>");
            userInput.nextLine();//清空缓冲区
            flag = userInput.nextInt();
        }
        if(flag == 1) {//快捷输入
            while(flag != 0){
                System.out.println("\t————————————————————————————————————"
                        + "————————————————————————————————————");
                person[size++] = quickInput();//输入一组数据
                try {
                    System.out.print("\t是否继续输入？(继续1/结束0) >>");
                    flag = userInput.nextInt();
                }
                catch(InputMismatchException ex) {
                    System.out.println("\t>>>>>>> 输入错误，请重新输入！ <<<<<<<");
                    System.out.print("\t是否继续输入？(继续1/结束0) >>");
                    userInput.nextLine();//清空缓冲区
                    flag = userInput.nextInt();
                }
            }
            System.out.println("\t>>> 输入结束 <<<");
            System.out.println("\t>>>>>>> 输入情况  <<<<<<<");
            //输出输入的信息
            for(int i = 0;i < size;i++) {
                System.out.println("\t————————————————————————————————————"
                        + "————————————————————————————————————");
                person[i].showPerson();
            }
            //写入到文件中
            for(int i = 0;i < size;i++) {
                save(output,person[i]);
            }
            return;
        }
        else if(flag == 2) {//完整输入
            while(flag != 0){
                System.out.println("\t————————————————————————————————————"
                        + "————————————————————————————————————");
                person[size++] = completeInput();//输入一组数据
                try {
                    System.out.print("\t是否继续输入？(继续1/结束0) >>");
                    flag = userInput.nextInt();
                }
                catch(InputMismatchException ex) {
                    System.out.println("\t>>>>>>> 输入错误，请重新输入！ <<<<<<<");
                    System.out.print("\t是否继续输入？(继续1/结束0) >>");
                    userInput.nextLine();//清空缓冲区
                    flag = userInput.nextInt();
                }
            }
            System.out.println("\t>>> 输入结束 <<<");
            System.out.println("\t>>>>>>> 输入情况  <<<<<<<");
            //输出输入的信息
            for(int i = 0;i < size;i++) {
                System.out.println("\t————————————————————————————————————"
                        + "————————————————————————————————————");
                person[i].showPerson();
            }
            //写入到文件中
            for(int i = 0;i < size;i++) {
                save(output,person[i]);
            }
            output.close();//关闭文件输出
        }
        else if(flag == 3) {
            return;
        }
        else {//输入错误
            System.out.println("\t输入错误，已自动返回主菜单。");
        }
        output.close();//关闭文件输出
    }

    public void show(File file) throws FileNotFoundException {
        //判断file是否存在
        try {
            Scanner temp = new Scanner(file);
            temp.close();
        }
        catch (FileNotFoundException ex) {
            //如果文件不存在，则用户手动输入文件绝对地址
            System.out.println("文件不存在！请输入新的文件地址(绝对地址):");
            file = new File(userInput.nextLine());
        }
        Scanner fileInput = new Scanner(file);//此处可能抛出文件不存在异常，未处理
        String name;//姓名
        String address;//地址
        String postalCode;//邮政编码
        String phoneNumber;//电话号码
        String creationDate;//创建日期
        String comment;//备注
        while(fileInput.hasNextLine()) {
            name = fileInput.nextLine();
            address = fileInput.nextLine();
            postalCode = fileInput.nextLine();
            phoneNumber = fileInput.nextLine();
            creationDate = fileInput.nextLine();
            comment = fileInput.nextLine();
            System.out.println("\t————————————————————————————————————————————————");
            System.out.printf("\t姓名：%s(%s)\n", name,comment);
            if(address.compareTo("null") != 0) System.out.println("\t地址：" + address);
            if(postalCode.compareTo("null") != 0)System.out.println("\t邮政编码：" + postalCode);
            System.out.println("\t电话号码：" + phoneNumber);
            System.out.println("\t创建日期：" + creationDate);
        }
        System.out.println("\t————————————————————————————————————————————————");
        fileInput.close();
        userInput.nextLine();//等待用户回车
        userInput.nextLine();//等待用户回车
    }

    public void change(File file) throws IOException {
        ArrayList<Person> personArray = new ArrayList<>();
        Scanner fileInput = new Scanner(file);
        while(fileInput.hasNextLine()) {
            personArray.add(new Person(fileInput.nextLine(),fileInput.nextLine(),fileInput.nextLine(),
                    fileInput.nextLine(),fileInput.nextLine(),fileInput.nextLine()));
        }
        fileInput.close();//关闭文件输入
        for(int i = 0;i < personArray.size();i++) {
            System.out.println("\t————————————————————————————————————————————————");
            System.out.printf("\t< %d >\n",i);
            personArray.get(i).showPerson();
        }
        System.out.println("\t————————————————————————————————————————————————");
        int flag = 1;
        int number = -1;
        while(flag != 0){
            try {
                System.out.print("\t请输入要修改的数据组编号(输入负数返回主菜单):");
                number = userInput.nextInt();
            }
            catch(InputMismatchException ex) {
                System.out.println("\t>>>>>>> 输入错误，请重新输入！ <<<<<<<");
                System.out.print("\t请输入要修改的数据组编号(输入负数返回主菜单):");
                userInput.nextLine();//清空缓冲区
                number = userInput.nextInt();
            }
            if(number < 0) {
                System.out.println("\t返回主菜单成功,您之前所有修改操作已被取消！");
                return;
            }
            Person t = personArray.get(number);//t用来获取person对象的引用
            userInput.nextLine();
            System.out.println("\t************************************************");
            System.out.print("\t姓名：" + t.getName() + "->" );
            t.setName(userInput.nextLine());
            System.out.print("\t地址：" + t.getAddress() + "->");
            t.setAddress(userInput.nextLine());
            System.out.print("\t邮政编码：" + t.getPostalCode() + "->");
            t.setPostalCode(userInput.nextLine());
            System.out.print("\t电话号码：" + t.getPhoneNumber() + "->");
            t.setPhoneNumber(userInput.nextLine());
            System.out.print("\t创建日期：" + t.getcreationDate() + "->");
            t.setCreationDate(userInput.nextLine());
            System.out.print("\t备注：" + t.getComment() + "->");
            t.setComment(userInput.nextLine());
            System.out.println("\t************************************************");
            try {
                System.out.print("\t是否继续输入？(继续1/结束0) >>");
                flag = userInput.nextInt();
            }
            catch(InputMismatchException ex) {
                System.out.println("\t>>>>>>> 输入错误，请重新输入！ <<<<<<<");
                System.out.print("\t是否继续输入？(继续1/结束0) >>");
                userInput.nextLine();//清空缓冲区
                flag = userInput.nextInt();
            }
        }
        System.out.println("\t>>>>>>>  修改成功!  <<<<<<<");
        //将修改后的数据写入到文件中，PrintWriter实现覆盖
        PrintWriter writer = new PrintWriter(file);//覆盖原先数据
        for (Person person : personArray) {
            cover(writer, person);
        }
        writer.close();//关闭文件写入
    }

    public void delete(File file) throws IOException {
        ArrayList<Person> personArray = new ArrayList<>();
        Scanner fileInput = new Scanner(file);
        while(fileInput.hasNextLine()) {
            personArray.add(new Person(fileInput.nextLine(),fileInput.nextLine(),fileInput.nextLine(),
                    fileInput.nextLine(),fileInput.nextLine(),fileInput.nextLine()));
        }
        fileInput.close();//关闭文件输入
        int flag = 1;
        while(flag != 0){
            for(int i = 0;i < personArray.size();i++) {
                System.out.println("\t————————————————————————————————————————————————");
                System.out.printf("\t< %d >\n",i);
                personArray.get(i).showPerson();
            }
            System.out.println("\t————————————————————————————————————————————————");
            int number = -1;
            try {
                System.out.print("\t请输入要删除的数据组编号(输入负数返回主菜单):");
                number = userInput.nextInt();
            }
            catch(InputMismatchException ex) {
                System.out.println("\t>>>>>>> 输入错误，请重新输入！ <<<<<<<");
                System.out.print("\t请输入要删除的数据组编号(输入负数返回主菜单):");
                userInput.nextLine();//清空缓冲区
                number = userInput.nextInt();
            }
            if(number < 0) {
                System.out.println("\t返回主菜单成功,您之前所有删除操作已被取消！");
                return;
            }
            personArray.remove(number);//删除编号为number的数据组
            try {
                System.out.print("\t是否继续输入？(继续1/结束0) >>");
                flag = userInput.nextInt();
            }
            catch(InputMismatchException ex) {
                System.out.println("\t>>>>>>> 输入错误，请重新输入！ <<<<<<<");
                System.out.print("\t是否继续输入？(继续1/结束0) >>");
                userInput.nextLine();//清空缓冲区
                flag = userInput.nextInt();
            }
        }
        System.out.println("\t>>>>>>>  删除成功!  <<<<<<<");
        //将修改后的数据写入到文件中，PrintWriter实现覆盖
        PrintWriter writer = new PrintWriter(file);//覆盖原先数据
        for (Person person : personArray) {
            cover(writer, person);
        }
        writer.close();//关闭文件写入
    }

    public boolean isContain(Person data,String keywords) {
        if(data.getName().contains(keywords)) return true;
        if(data.getAddress().contains(keywords)) return true;
        if(data.getPostalCode().contains(keywords)) return true;
        if(data.getPhoneNumber().contains(keywords)) return true;
        if(data.getcreationDate().contains(keywords)) return true;
        if(data.getComment().contains(keywords)) return true;
        return false;
    }
    public void search(File file) throws IOException {
        ArrayList<Person> personArray = new ArrayList<Person>();
        Scanner fileInput = new Scanner(file);
        while(fileInput.hasNextLine()) {
            personArray.add(new Person(fileInput.nextLine(),fileInput.nextLine(),fileInput.nextLine(),
                    fileInput.nextLine(),fileInput.nextLine(),fileInput.nextLine()));
        }
        fileInput.close();
        userInput.nextLine();
        System.out.println("\t请输入关键词(输入end返回主菜单):");
        System.out.print("\t>>>");
        String keywords = userInput.nextLine();
        if(keywords.contains("end")) {
            System.out.println("\t返回主菜单成功!");
            return;
        }
        boolean flag =  false;//判断搜索是否有结果
        for (Person person : personArray) {
            if (isContain(person, keywords)) {
                System.out.println("\t————————————————————————————————————————————————");
                person.showPerson();
                System.out.println("\t————————————————————————————————————————————————");
                flag = true;
            }
        }
        if(flag) {
            System.out.println("\t>>>>>>>  查询成功!  <<<<<<<");
        }
        else System.out.println("\t>>>>>>>  搜索无结果!  <<<<<<<");
    }

    public static void main(String []args) throws InputMismatchException, IOException {
        AddressBook addressBook = new AddressBook();//addressBook对象
        File file = new File("D:\\programming\\eclipse\\AddressBook\\data\\addressBook.txt");//文件
        int option = 0;
        do{
            addressBook.menu();
            option = userInput.nextInt();
            System.out.println();
            switch (option) {
                case 0:
                    System.out.println("\t>>>>>>> 感谢您的使用！ <<<<<<<");
                    break;
                case 1:
                    addressBook.input();
                    break;
                case 2:
                    addressBook.show(file);
                    break;
                case 3:
                    addressBook.change(file);
                    break;
                case 4:
                    addressBook.delete(file);
                    break;
                case 5:
                    addressBook.search(file);
                    break;
                default:
                    System.out.println("\t输入错误！");
                    break;
            }
        }while(option != 0);
        userInput.close();
    }
}
