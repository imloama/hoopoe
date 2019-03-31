package hoopoe.core.zfarm;

import lombok.Data;

import java.io.Serializable;

@Data
public class Ref implements Serializable {

    private String apiPrefix;
    private String primaryKey;
    private String labelKey;
    private String parentKey;

}
