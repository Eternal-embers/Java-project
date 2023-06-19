import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class AutoRename {
    public static void main(String[] args) {
            File dir = new File("D://网页保存");
            File []list1 = dir.listFiles();
            ArrayList<File> list2 = new ArrayList<File>();
            for(File f: list1){
                if(f.getName().contains("page")) 
                    list2.add(f);
            }
            int len = list2.size();
            //获取章节数
            int[] num = new int[len];
            for(int i = 0;i < len;i++){
                num[i] = Integer.parseInt(list2.get(i).getName().split("[)]")[0].substring(5));
            }
            //选择排序
            int i,j,k;
            for(i = 0;i < len - 1;i++){
                k = i;
                for(j = i + 1;j < len;j++){
                    if(num[k] > num[j]) k = j;
                }
                if(k != i){
                    int t = num[k];
                    num[k] = num[i];
                    num[i] = t;
                    File file = list2.get(k);
                    list2.set(k,list2.get(i));
                    list2.set(i,file);
                }
            }
            int cnt = 103;
            for(i = 0;cnt!=150;i++,cnt++){
                list2.get(i).renameTo(new File("第" + cnt + "话.mht"));
                System.out.println(list2.get(i).getName());
            }
        }
}
