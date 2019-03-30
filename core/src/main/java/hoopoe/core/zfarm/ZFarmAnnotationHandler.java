package hoopoe.core.zfarm;

import com.google.common.collect.Lists;
import hoopoe.core.base.BaseModel;
import hoopoe.core.zfarm.annotation.ZFarm;
import hoopoe.core.zfarm.annotation.ZField;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据类注解，生成相应的数据
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ZFarmAnnotationHandler {

    public <M extends BaseModel<M,? extends Serializable>> Farm<M> handler(Class<M> clasz) throws IllegalAccessException, InstantiationException {
        M m = clasz.newInstance();
        boolean isZFarm = clasz.isAnnotationPresent(ZFarm.class);
        if(!isZFarm)return null;
        ZFarm zfarm = clasz.getAnnotation(ZFarm.class);
        java.lang.reflect.Field[] params = clasz.getDeclaredFields();
        List<Field> fieldList = Lists.newArrayList();
        for(int i=0,n=params.length;i<n;i++){
            java.lang.reflect.Field param = params[i];
            if(!param.isAnnotationPresent(ZField.class))continue;
            ZField zField = param.getAnnotation(ZField.class);
            Field field = new Field();
            field.setSearch(zField.search());

        }

        //TODO

        return null;
    }

}
