package hoopoe.core.zfarm;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Field implements Serializable {

    private FieldType type;
    private boolean show = true;//是否展示
    private String id;
    private String name;
    private String label;
    private boolean showLabel = true;
    private String format;//格式化输出
    private List<Rule> rules = Lists.newArrayList();
    private List<Field> children = Lists.newArrayList();
    // 可能的选项
    private List<Object> options;

    public Field addRule(Rule rule){
        this.rules.add(rule);
        return this;
    }

    public Field addChildren(Field child){
        this.children.add(child);
        return this;
    }


    public Field hide(){
        this.show = false;
        return this;
    }

}
