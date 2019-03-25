package hoopoe.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public final class ValueCheck {

    private ValueCheck(){}

    public static void strIsNotBlank(String source, String message) throws Exception {
        if(StringUtils.isBlank(source)){
            throw new Exception(message);
        }
    }

    public static void isNull(Object target, String message)throws Exception {
        if(target == null)throw new Exception(message);
        if(target instanceof String && StringUtils.isBlank((String)target))throw new Exception(message);
        if(target instanceof Collection && ((Collection)target).isEmpty())throw new Exception(message);
    }



}
