package hoopoe.core.zfarm;

public interface RulePattern {
    /**
     * 邮箱校验的正则表达式
     */
    static final String EMAIL = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    static final String _NUM = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    static final String IP = "^" + _NUM + "\\." + _NUM + "\\." + _NUM + "\\." + _NUM + "$";
    static final String URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    static final String TELPHONE = "^[1]+[3,5,8]+\\d{9}$";
    static final String STR_NUM_PASSWORD =  "[A-Za-z]+[0-9]";

    /**
     * 整数
     */
    static final String INTEGER =  "^[0-9]*$";
    /**
     * 正整数
     */
    static final String POSITIVE_INTEGER = "^\\+?[1-9][0-9]*$";


}
