package hoopoe.core.zfarm;

import com.google.common.collect.Lists;
import hoopoe.core.base.BaseModel;
import hoopoe.core.zfarm.annotation.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据类注解，生成相应的数据
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ZFarmAnnotationHandler {

    public static <M extends BaseModel<M,? extends Serializable>> Farm<M> handler(Class<M> clasz) throws IllegalAccessException, InstantiationException {
        M model = clasz.newInstance();
        boolean isZFarm = clasz.isAnnotationPresent(ZFarm.class);
        if(!isZFarm)return null;
        ZFarm zfarm = clasz.getAnnotation(ZFarm.class);
        Farm<M> farm = new Farm<>();
        farm.setApiprefix(zfarm.apiPrefix());
        farm.setPrimaryKey(zfarm.primaryKey());
        farm.setTree(zfarm.tree());
        ZAction[] zActions = zfarm.actions();
        if(zActions!=null&&zActions.length>0){
            List<Action> actions = Lists.newArrayList();
            for(ZAction act : zActions){
                Action action = new Action();
                action.setFilter(act.filter().length() == 0 ? null:act.filter());
                action.setLabel(act.label());
                action.setName(act.name());
                actions.add(action);
            }
            farm.setActions(actions);
        }

        java.lang.reflect.Field[] params = clasz.getDeclaredFields();
        List<Field> fieldList = Lists.newArrayList();
        for(int i=0,n=params.length;i<n;i++){
            java.lang.reflect.Field param = params[i];
            if(!param.isAnnotationPresent(ZField.class))continue;
            ZField zField = param.getAnnotation(ZField.class);
            Field field = new Field();
            field.setSearch(zField.search());
            field.setEdit(zField.edit());
            field.setFormat(zField.format().length() == 0 ? null : zField.format());
            field.setLabel(zField.label());
            field.setName(zField.name());
            field.setShow(zField.show());
            field.setType(zField.type());
            field.setShowLabel(zField.showLabel());
            ZOption[] zopts = zField.options();
            if(zopts.length>0){
                List<Option> opts = Lists.newArrayList();
                for (ZOption zop : zopts){
                    Option opt = new Option();
                    opt.setKey(zop.key());
                    opt.setValue(zop.value());
                    opts.add(opt);
                }
                field.setOptions(opts);
            }
            ZRef[] refs = zField.ref();
            if(refs!=null&& refs.length > 0){
                ZRef zRef = refs[0];
                Ref ref = new Ref();
                ref.setApiPrefix(zRef.apiPrefix());
                ref.setLabelKey(zRef.labelKey());
                ref.setPrimaryKey(zRef.primaryKey());
                ref.setParentKey(zRef.parentKey().length() == 0 ? null : zRef.parentKey());
                field.setRef(ref);
            }
            ZRule[] zRules = zField.rules();
            if(zRules!=null&&zRules.length>0){
                List<Rule> rules = Lists.newArrayList();
                for (ZRule zRule : zRules){
                    Rule rule = new Rule();
                    rule.setErrMsg(zRule.errMsg());
                    rule.setMax(zRule.max() == Integer.MAX_VALUE ? null : zRule.max());
                    rule.setMin(zRule.min());
                    rule.setPattern(zRule.pattern());
                    rule.setRequired(zRule.required());
                    rules.add(rule);
                }
                field.setRules(rules);
            }
            fieldList.add(field);
        }
        farm.setFields(fieldList);
        return farm;
    }

}
