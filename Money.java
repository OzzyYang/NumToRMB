public class Money {
    private double money_value;
    private static Money money = new Money();

    private Money() {
        this.money_value = 0;
    }

    public void setMoneyValue(double value) {
        this.money_value = value;
    }

    public static Money getMoneyInstance() {
        return Money.money;
    }

    private String[] RMB_bit = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };// 人民币每一位的大写数字
    private String[] RMB_unit = { "", "拾", "佰", "仟" };// 人民币每一位的单位
    private String[] RMB_part = { "亿", "万", "元" };// 每四位数字为一个部分（Part），这里表示每一个部分的单位

    // 此方法用于将数值转化为大写
    public void upperCase() {
        String[] money_str = dividePart();// 将人民币的数值拆分为整数和小数两个部分存放在字符串数组里，money_str[0]存放整数部分，money_str[1]存放小数部分
        String result = "";
        result = result + intergerPartRecon(money_str[0]);// 将整数部分的数字转化为大写并存放入结果(result)中
        result = result + fractionalPart(money_str[1]);// 将小数部分的数字转化为大写并存放入结果(result)中
        System.out.println(result);
    }

    // 此方法用于将人民币的数值拆分为整数和小数两个部分
    private String[] dividePart() {
        long integer_part = (long) money_value;// 分离整数部分
        long fractional_part = Math.round((money_value - integer_part) * 100);// 分离小数部分，round()方法用于取整，因为人民币数值只需要保留两位小数（即分、角）
        return new String[] { integer_part + "", String.valueOf(fractional_part) };// 这里采用了两种将数值型（long）数据转换为字符型（String）数据的方法
    }

    // 此方法用于将整数部分的数字转化为大写
    private String intergerPart(String integer_part) {
        int string_length = integer_part.length();// 整数部分数字的长度
        int part_length = (string_length - 1) / 4;// 每个部分的长度
        int i_length = part_length + 1;// i循环的长度
        int j_length = 4;// j循环的长度
        int bit_count = 0;// 表示当前正在转换某一位的计数器
        int zero_tag = 0;// 处理人民币数值中0的计数器
        String integer_part_result = "";
        if (part_length < 0) {
            return "";
        } else {
            for (int i = 0; i < i_length; i++) {
                if ((string_length % 4 != 0)) {
                    j_length = string_length % 4;
                } else {
                    j_length = 4;
                }
                for (int j = 0; j < j_length; j++) {
                    if ((integer_part.charAt(bit_count) != '0')
                            | (integer_part.charAt(bit_count) == '0' && (string_length == 1))) {
                        integer_part_result += RMB_bit[integer_part.charAt(bit_count) - 48];// 每一个字符型数字的ASCII十进制码-48得到的数值型数字就是这个数字本身
                        integer_part_result += RMB_unit[j_length - j - 1];
                        zero_tag = 0;
                    } else {
                        if ((integer_part.charAt(bit_count) == '0') && (j != j_length - 1) && (zero_tag == 0)) {
                            zero_tag++;
                            integer_part_result += RMB_bit[integer_part.charAt(bit_count) - 48];
                        } else if ((integer_part.charAt(bit_count) == '0') && (j != j_length - 1) && (zero_tag != 0)) {
                            zero_tag++;
                        }

                        if ((integer_part.charAt(bit_count) == '0') && (j == j_length - 1) && (zero_tag != 0)
                                && (integer_part_result.endsWith("零"))) {
                            zero_tag = 0;
                            integer_part_result = integer_part_result.substring(0, integer_part_result.length() - 1);
                        }
                    }
                    bit_count++;
                }
                integer_part_result += RMB_part[(2 - part_length)] + "，";
                part_length--;
                string_length = string_length - j_length;
                zero_tag = 0;
            }
            return integer_part_result;
        }
    }

    private String intergerPartRecon(String integer_part) {
        String result = "";
        int strLen = integer_part.length();
        int partLen = (strLen - 1) / 4 + 1, partCount = 1;
        String[] resultArr = new String[integer_part.length()];
        int resultArrCount = 0;
        // 把数字转化成大写
        for (int i = 0; i < resultArr.length; i++) {
            resultArr[i] = RMB_bit[integer_part.charAt(i) - 48];
        }
        // 每个大写数字加上单位
        do {
            int iLen;
            if (partCount == 1) {
                iLen = (strLen % 4 == 0) ? 4 : (strLen % 4);
            } else {
                iLen = 4;
            }
            for (int i = iLen - 1; i >= 0; i--) {
                resultArr[resultArrCount++] += RMB_unit[i];
            }
            partCount++;
        } while (partCount <= partLen);
        // 加上每个部分的单位，同时去除掉多余的零
        partCount = 1;
        resultArrCount = 0;
        int zeroCount = 0, zeroTag = 0;
        do {
            int iLen;
            if (partCount == 1) {
                iLen = (strLen % 4 == 0) ? 4 : (strLen % 4);
            } else {
                iLen = 4;
            }
            for (int i = iLen - 1; i >= 0; i--) {
                if (integer_part.charAt(resultArrCount++) == '0') {
                    //每第一次遇到0则zeroTag累计一次
                    if (zeroCount == 0) {
                        zeroTag++;
                    }
                    zeroCount++;
                } else {
                    zeroCount = 0;
                }
                switch (zeroCount) {
                    case 1: {

                    }
                    case 2: {

                    }
                    default:{
                        
                    }
                }
            }
            partCount++;
        } while (partCount <= partLen);
        // 把存放的结果字符组合并成一个结果字符串
        for (String str : resultArr) {
            result = result + str;
        }
        return result;
    }

    // 此方法用于将小数部分的数字转化为大写
    private String fractionalPart(String fractional_Part) {
        String fractional_Part_result = "";
        if (fractional_Part.length() > 1) {
            if (fractional_Part.charAt(0) != '0') {
                fractional_Part_result += RMB_bit[fractional_Part.charAt(0) - 48];
                fractional_Part_result += "角";
            } else {
                fractional_Part_result += "零";
            }

            if (fractional_Part.charAt(1) != '0') {
                fractional_Part_result += RMB_bit[fractional_Part.charAt(1) - 48];
                fractional_Part_result += "分";
            }
        } else if ((fractional_Part.length() == 1) && (fractional_Part.charAt(0) != '0')) {
            fractional_Part_result += "零";
            fractional_Part_result += RMB_bit[fractional_Part.charAt(0) - 48];
            fractional_Part_result += "分";
        }
        return fractional_Part_result;
    }

}