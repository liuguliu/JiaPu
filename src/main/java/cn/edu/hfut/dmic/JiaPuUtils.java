package cn.edu.hfut.dmic;

import java.io.*;

public class JiaPuUtils {

    public static String[] attrNameListC = {"姓名","性别","父亲姓名","母亲姓名","住址","字","号","曾用名","农历出生日期","农历过世日期","公历出生日期","公历过世日期","葬于","职务（官职）","所属姓氏","民族","辈份","第几世","简介","配偶姓名","配偶农历出生日期","配偶农历过世日期","配偶公历出生日期","配偶公历过世日期","配偶葬于","其他描述"};

    /** 读取json文件,返回json串*/
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
