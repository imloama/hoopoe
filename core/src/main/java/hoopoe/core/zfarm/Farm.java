package hoopoe.core.zfarm;

import hoopoe.core.base.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * farm模型定义
 */
@Data
public class Farm<M extends BaseModel<M,? extends Serializable>> {

    private String primaryKey;
    List<Field> fields;

}
