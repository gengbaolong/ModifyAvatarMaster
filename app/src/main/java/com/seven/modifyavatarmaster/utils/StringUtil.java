package com.seven.modifyavatarmaster.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by snh007 on 2017/7/28.
 */

public class StringUtil {
    /**
     * 判断字符串是否为空
     *
     * @param str 字符串值
     * @return boolean true空，false 非空
     */
    public static boolean isBlank(String str) {
        if (str != null) {
            return "".equals(str.trim());
        }
        return true;
    }
    public static boolean checkCodeLength(String string,int length){
        if(!isBlank(string)){
            return string.length()==length;
        }
        return false;
    }
    /**
     * 判断字符串是否为空
     * isEmpty(null) ==  true
     * isEmpty("") ==  true
     * isEmpty(" ") ==  false
     *
     * @param str 字符串值
     * @return boolean true空，false 非空
     */
    public static boolean isBlank(CharSequence str) {
        if (str != null) {
            return str.length() > 0 ? false : true;
        }
        return true;
    }
    /**
     * 获取字符串的长度
     *
     * @param str 字符串值
     */
    public static int getStrLength(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 字符串转double
     *
     * @param obj 字符串值
     */
    public static double strToDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
        }
        return 0D;
    }

    /**
     * 字符串转int类型
     *
     * @param str 字符串值
     * @return 转换异常默认返回0
     */
    public static int strToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * Double转int类型
     *
     * @param numb double值
     * @return 转换异常默认返回0
     */
    public static int doubleToInt(double numb) {
        try {
            int number = (int) numb;
            return number;
        } catch (Exception e) {
        }
        return 0;
    }
    /**
     * 验证邮箱地址是否正确
     *
     * @param email 输入的邮箱地址
     * @return boolean true正确，false 不正确
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[_-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 验证手机号码是否正确
     * @param mobile 手机号码
     * @return boolean true正确，false 不正确
     * 13+任意数
     * 15+除4的任意数
     * 18+任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isPhone(String mobile){
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobile);
        return m.matches();

    }

    /**
     * 检查银行卡 （15-19位）
     * @param bankNum
     * @return
     */
    public static boolean checkBank(String bankNum){
        if(!isBlank(bankNum)){
            return bankNum.length()>=15&&bankNum.length()<=19;
        }
        return false;
    }

    /**
     * 检查合利宝绑卡时的银行卡位数
     * @param bankNum
     * @return
     */
    public static boolean checkHelibaoBankNum(String bankNum){
        if(!StringUtil.isBlank(bankNum)){
            return bankNum.length()>=16&&bankNum.length()<=20;
        }
        return false;
    }

    /**
     * 比较字符串大小
     * @param appCode
     * @param serverCode
     * @return true:服务器大于客户端  false:服务器小于客户端
     */
    public static boolean compareString(String appCode,String serverCode){
        boolean compareFlag = false;
        try {
            if (!StringUtil.isBlank(appCode) && !StringUtil.isBlank(serverCode)) {
                int appInt = Integer.parseInt(appCode);
                int serverInt = Integer.parseInt(serverCode);
                if (serverInt > appInt) {
                    compareFlag = true;
                }
            }
        }catch (Exception e){

        }
        return compareFlag;
    }
}
