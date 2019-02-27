package hoopoe.core.security;

import com.blade.mvc.RouteContext;
import com.blade.mvc.hook.WebHook;

/**
 * 权限,校验
 */
public class SecurityMiddleware implements  WebHook {
    @Override
    public boolean before(RouteContext context) {
        return true;
    }
}
