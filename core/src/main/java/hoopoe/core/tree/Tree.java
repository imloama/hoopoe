package hoopoe.core.tree;



import java.util.List;

public class Tree<T extends ITree> implements ITree{
    private List<Tree> children;
    private T source;

    public Tree(T source){
        this.source = source;
    }

    @Override
    public String getKey() {
        return this.source.getKey();
    }

    @Override
    public String getValue() {
        return this.source.getValue();
    }

    @Override
    public String getLabel() {
        return this.source.getLabel();
    }

    @Override
    public String getParentKey() {
        return this.source.getParentKey();
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }
}
