package hoopoe.core.tree;



import lombok.Data;

import java.util.List;

@Data
public class Tree<T extends ITree> implements ITree{
    private List<Tree<T>> children;
    private T source;
    private boolean leaf = true;// 是否叶子节点

    public Tree(T source){
        this.source = source;
    }

    @Override
    public String getKey() {
        return this.source == null ? "0" : this.source.getKey();
    }

    @Override
    public String getValue() {
        return this.source == null ? "" : this.source.getValue();
    }

    @Override
    public String getLabel() {
        return this.source == null ? "" :this.source.getLabel();
    }

    @Override
    public String getParentKey() {
        return this.source == null ? "" :this.source.getParentKey();
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }
}
