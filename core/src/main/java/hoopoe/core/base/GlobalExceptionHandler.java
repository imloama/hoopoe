package hoopoe.core.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    @ResponseBody
    public ResponseEntity exceptionHandle(Exception e){ // 处理方法参数的异常类型
        log.error(e.getMessage(), e);
        ResponseEntity entity = new ResponseEntity("服务器发生错误", HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }




}
