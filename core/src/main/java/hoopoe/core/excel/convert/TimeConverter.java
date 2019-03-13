package hoopoe.core.excel.convert;

import cn.hutool.core.date.DateUtil;
import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitWriteConverterException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class TimeConverter implements WriteConverter {
    @Override
    public String convert(Object value) throws ExcelKitWriteConverterException {
        try {
            if (value == null)
                return "";
            else {
                return DateUtil.format((Date)value, "yyyy-MM-dd HH:mm:ss");
            }
        } catch (Exception e) {
            log.error("时间转换异常", e);
            return "";
        }
    }
}