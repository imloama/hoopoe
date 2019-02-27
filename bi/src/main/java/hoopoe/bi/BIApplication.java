package hoopoe.bi;

import com.blade.Blade;

public class BIApplication {

    public static void main(String[] args) {
        Blade.of().get("/", ctx -> ctx.text("Hello Blade")).start();
    }

}
