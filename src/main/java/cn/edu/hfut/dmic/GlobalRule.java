package cn.edu.hfut.dmic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 家谱文件规则处理文件，包含对家谱中人物属性抽取的规则集
 */
public class GlobalRule {
    // 字辈，此处只给出了少数几个
    // 注意：每份家谱都不同，后面会考虑每份家谱都重新生成这个数组
    String generationRank[] = {"一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十",
            "二十一","二十二","二十三","二十四","二十五","二十六","二十七","二十八","二十九","三十","三十一","三十二","三十三","三十四","三十五",
            "三十六","三十七","三十八","三十九","四十"};
    // 孩子数，如女一、女二等
    // 注意：我们默认一个家庭中不会有超过二十个子女的情况
    String cNum[] = {"一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十"};
    //孩子的排行，如长子，次子等
    String rankNum[] = {"零","长","次","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十"};

    Map<String,String> attrPatternSet = new HashMap<String, String>();  //存储提取家谱人物各属性值的规则集

    /**构造函数*/
    public GlobalRule(){
        attrPatternSet.put("字", "字[\\u4E00-\\u9FA5][\\u4E00-\\u9FA5]");
        attrPatternSet.put("号", "号[\\u4E00-\\u9FA5][\\u4E00-\\u9FA5]");
    }

    /**获取当前部分人物在家谱中的代数，我们默认一份家谱不会有超过四十代的人数*/
    public int getGeneration(String line) {
        int num = -1;
        String pattern = "[第]?[一|二|廿|三|四|五|六|七|八|九|十]{1,3}世";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);
        if(m.find()) {
            if ("一世".equals(m.group(0)) || "第一世".equals(m.group(0))) {
                num = 1;
            } else if ("二世".equals(m.group(0)) || "第二世".equals(m.group(0))) {
                num = 2;
            } else if ("三世".equals(m.group(0)) || "第三世".equals(m.group(0))) {
                num = 3;
            } else if ("四世".equals(m.group(0)) || "第四世".equals(m.group(0))) {
                num = 4;
            } else if ("五世".equals(m.group(0)) || "第五世".equals(m.group(0))) {
                num = 5;
            } else if ("六世".equals(m.group(0)) || "第六世".equals(m.group(0))) {
                num = 6;
            } else if ("七世".equals(m.group(0)) || "第七世".equals(m.group(0))) {
                num = 7;
            } else if ("八世".equals(m.group(0)) || "第八世".equals(m.group(0))) {
                num = 8;
            } else if ("九世".equals(m.group(0)) || "第九世".equals(m.group(0))) {
                num = 9;
            } else if ("十世".equals(m.group(0)) || "第十世".equals(m.group(0))) {
                num = 10;
            } else if ("十一世".equals(m.group(0)) || "第十一世".equals(m.group(0))) {
                num = 11;
            } else if ("十二世".equals(m.group(0)) || "第十二世".equals(m.group(0))) {
                num = 12;
            } else if ("十三世".equals(m.group(0)) || "第十三世".equals(m.group(0))) {
                num = 13;
            } else if ("十四世".equals(m.group(0)) || "第十四世".equals(m.group(0))) {
                num = 14;
            } else if ("十五世".equals(m.group(0)) || "第十五世".equals(m.group(0))) {
                num = 15;
            } else if ("十六世".equals(m.group(0)) || "第十六世".equals(m.group(0))) {
                num = 16;
            } else if ("十七世".equals(m.group(0)) || "第十七世".equals(m.group(0))) {
                num = 17;
            } else if ("十八世".equals(m.group(0)) || "第十八世".equals(m.group(0))) {
                num = 18;
            } else if ("十九世".equals(m.group(0)) || "第十九世".equals(m.group(0))) {
                num = 19;
            } else if ("二十世".equals(m.group(0)) || "第二十世".equals(m.group(0)) || "廿世".equals(m.group(0))) {
                num = 20;
            } else if ("二十一世".equals(m.group(0)) || "第二十一世".equals(m.group(0)) || "廿一世".equals(m.group(0))) {
                num = 21;
            }  else if ("二十二世".equals(m.group(0)) || "第二十二世".equals(m.group(0)) || "廿二世".equals(m.group(0))) {
                num = 22;
            } else if ("二十三世".equals(m.group(0)) || "第二十三世".equals(m.group(0)) || "廿三世".equals(m.group(0))) {
                num = 23;
            } else if ("二十四世".equals(m.group(0)) || "第二十四世".equals(m.group(0)) || "廿四世".equals(m.group(0))) {
                num = 24;
            } else if ("二十五世".equals(m.group(0)) || "第二十五世".equals(m.group(0)) || "廿五世".equals(m.group(0))) {
                num = 25;
            } else if ("二十六世".equals(m.group(0)) || "第二十六世".equals(m.group(0)) || "廿六世".equals(m.group(0))) {
                num = 26;
            } else if ("二十七世".equals(m.group(0)) || "第二十七世".equals(m.group(0)) || "廿七世".equals(m.group(0))) {
                num = 27;
            } else if ("二十八世".equals(m.group(0)) || "第二十八世".equals(m.group(0)) || "廿八世".equals(m.group(0))) {
                num = 28;
            } else if ("二十九世".equals(m.group(0)) || "第二十九世".equals(m.group(0)) || "廿九世".equals(m.group(0))) {
                num = 29;
            } else if ("三十世".equals(m.group(0)) || "第三十世".equals(m.group(0))) {
                num = 30;
            } else if ("三十一世".equals(m.group(0)) || "第三十一世".equals(m.group(0))) {
                num = 31;
            } else if ("三十二世".equals(m.group(0)) || "第三十二世".equals(m.group(0))) {
                num = 32;
            } else if ("三十三世".equals(m.group(0)) || "第三十三世".equals(m.group(0))) {
                num = 33;
            } else if ("三十四世".equals(m.group(0)) || "第三十四世".equals(m.group(0))) {
                num = 34;
            } else if ("三十五世".equals(m.group(0)) || "第三十五世".equals(m.group(0))) {
                num = 35;
            } else if ("三十六世".equals(m.group(0)) || "第三十六世".equals(m.group(0))) {
                num = 36;
            } else if ("三十七世".equals(m.group(0)) || "第三十七世".equals(m.group(0))) {
                num = 37;
            }  else if ("三十八世".equals(m.group(0)) || "第三十八世".equals(m.group(0))) {
                num = 38;
            } else if ("三十九世".equals(m.group(0)) || "第三十九世".equals(m.group(0))) {
                num = 39;
            } else if ("四十世".equals(m.group(0)) ||"第四十世".equals(m.group(0))) {
                num = 40;
            } else {
                num = -1;
            }
        }
        return num;
    }
}
