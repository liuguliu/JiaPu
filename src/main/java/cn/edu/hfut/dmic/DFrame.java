package cn.edu.hfut.dmic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目主文件，包含处理的整体逻辑
 */
public class DFrame {
    /**定义全局变量*/
    static GlobalRule GR = new GlobalRule(); // 一份家谱的规则集
    static int Generation = -1; // 记录正在处理的人物的世辈信息

    /**main函数*/
    public static void main(String[] args) {
        String jiapuString=""; // 用于存储读取的家谱文件
        DFrame dJiaFrame = new DFrame(); //用于调用DFrame类的函数
        Map<String, String> labelText = new HashMap<String, String>();  //用于标注的文本
        Map<String, String> labeledAttrMap = new HashMap<String, String>();  //用户标注的属性字典
        // 读取家谱文件
//        String filename="/src/main/java/resources/jiaPuData/source/吴氏始祖至道二公世系谱.docx";
        String filename= dJiaFrame.getPath("jiaPuData/source")+"/吴氏始祖至道二公世系谱.docx";
        jiapuString = dJiaFrame.ReadJiaPuFile(filename);
//        System.out.println("jiapuString: "+jiapuString);
        String[] jiapuList = jiapuString.split("\n");
        // 选取用于用户标注的文本
        labelText = dJiaFrame.atrrLabel(jiapuList);
        System.out.println("labelText: "+labelText);
        // 用户标注
        labeledAttrMap = dJiaFrame.userIntelligence(labelText.get("labelString"));
        System.out.println("labeledAttrMap: "+labeledAttrMap);
        //根据用户标注信息，学习家谱文件规则
        dJiaFrame.ruleLearning(labelText.get("labelString"),labeledAttrMap);
        //家谱人物属性抽取
        dJiaFrame.atrrExactor(jiapuList);
    }

    /**读取家谱文件*/
    private  String ReadJiaPuFile(String filename) {
        String buffer="";

        // 判断文件类型（doc\docx\txt）
        if(filename.substring(filename.length()-4).contains("txt")) {
            //读取txt类型家谱文件
            try {
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));
                String line="";
                while((line=bufferedreader.readLine())!=null){
                    buffer+=line+"\n";
                }
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("txt家谱文件读取失败！");
            }
        }else {
            //读取doc\docx类型家谱文件
            try {
                if (filename.endsWith(".doc")) {
                    InputStream is = new FileInputStream(new File(filename));
                    WordExtractor ex = new WordExtractor(is);
                    buffer = ex.getText();
                    ex.close();
                } else if (filename.endsWith(".docx")) {
                    FileInputStream fis = new FileInputStream(filename);
                    XWPFDocument xdoc = new XWPFDocument(fis);
                    XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
                    buffer = extractor.getText();
                    extractor.close();
                    fis.close();
                } else {
                    System.out.println("此文件不是word文件！");
                }
            } catch (Exception e) {
                System.out.println("word家谱文件读取失败！");
                e.printStackTrace();
            }
        }
        return buffer;
    }

    /**选取用于用户标注的文本*/
    private Map<String, String> atrrLabel(String[] jiapuList) {
        int i=0,j=0;
        String labelString="";
        String pattern = "[^生][长|次|三|四|五|六|七|八|九|十|十一|十二|十三|十四|十五|十六|十七|十八|十九|二十]子";
        Pattern r = Pattern.compile(pattern);
        for(;i<jiapuList.length;i++){
            if(GR.getGeneration(jiapuList[i])!=-1) {
                //世辈信息行
                Generation = GR.getGeneration(jiapuList[i]);
            }else if(jiapuList[i].trim().length()<2){
                //为空行
                continue;
            }else {
                //人物信息行
                Matcher m = r.matcher(jiapuList[i]);
                if (m.find()) { //当前行为一个新的人物信息行
                    j += 1;
                }
                if(j>1){ //已经读到了下一个人物信息
                    break;
                }else{
                    labelString = labelString+jiapuList[i]+"\n";
                }
            }
        }
        Map<String, String> m = new HashMap<String, String>();
        m.put("i", i+"");
        m.put("labelString",labelString);
        return m;
    }

    /**用户标注*/
    private Map<String,String> userIntelligence(String labelString) {
        String str=""; //读取用户标注文本
        Map<String,String> attrMap = new HashMap<String, String>(); //存储用户标注属性字典
        System.out.println(labelString);
        System.out.println();
        System.out.println();
        System.out.println("请根据以下提供的人物信息，标注人物的属性信息，如：‘姓名：太伯’，‘性别：男’，‘父亲姓名：古公亶父’，‘住址：岐山周原（今属陕西岐山县）’等等，");
        System.out.println("可标注属性信息为：姓名，性别，父亲姓名，母亲姓名，住址，字，号，曾用名，农历出生日期，农历过世日期，公历出生日期，公历过世日期，葬于，" +
                "职务（官职），所属姓氏，民族，辈份，第几世，简介，配偶姓名，配偶农历出生日期，配偶农历过世日期，配偶公历出生日期，配偶公历过世日期，配偶葬于，其他描述。");
        System.out.println("请进行标注（连续两次回车表示完成标注）：");
        System.out.println();
        System.out.println();

        /**从控制台输入*/
//        Scanner sc = new Scanner(System.in);
//        str = sc.nextLine(); //读取一行数据
//        while(str.length()!=0){
//            if(str.contains(":")){  //英文冒号
//                String[] attrTemp = str.split(":");
//                attrMap.put(attrTemp[0].trim(),attrTemp[1].trim());
//            }else if(str.contains("：")){    //中文冒号
//                String[] attrTemp = str.split("：");
//                attrMap.put(attrTemp[0].trim(),attrTemp[1].trim());
//            }else{  //该行为用户输入的无效行
////                continue;
//            }
//            str = sc.nextLine();
//        }
        /**从json文件读取*/
//        String path = "src/main/java/resources/test.json";
        String path = this.getClass().getClassLoader().getResource("test.json").getPath();
        str = JiaPuUtils.readJsonFile(path);
        JSONObject jobj = JSON.parseObject(str);
        for(String key : jobj.keySet()){
//            System.out.println(key+":"+jobj.get(key));
            attrMap.put(key, jobj.get(key)+"");
        }
        return attrMap;
    }

    /**基本规则学习*/
    private String autoRuleLearning() {
        System.out.println("");
        return "";  //返回属性提取规则集
    }

    /**根据用户标注信息，学习家谱文件规则*/
    private String ruleLearning(String labelString, Map<String,String> labeledAttrMap) {
        System.out.println("");
        return "";  //返回属性提取规则集
    }

    /**家谱人物属性抽取(可参考原提取方法)*/
    private String atrrExactor(String[] jiapuList) {
        String PeopleString = "";
        for(int i=0;i<jiapuList.length;i++){
            if(GR.getGeneration(jiapuList[i])!=-1) {
                //世辈信息行
                Generation = GR.getGeneration(jiapuList[i]);
            }else if(jiapuList[i].trim().length()<2){
                //为空行
                continue;
            }else {
                //人物信息行
            }
//            System.out.println(i+": "+jiapuList[i]);
        }
        return "";
    }

    /**获取文件路径*/
    public String getPath(String path){
        return this.getClass().getClassLoader().getResource(path).getPath();
    }
}
