package hoopoe.core.tree;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class TreeUtil {

    private TreeUtil(){}
    private final static String TOP_NODE_ID = "0";

    public static <T extends ITree> Tree<T> build(List<T> list){
        if(list == null)return null;
        Map<String,Tree<T>> nodemap = list.stream()
            .map(item -> new Tree<T>(item))
            .collect(Collectors.toMap(Tree::getKey, tree -> tree));

        List<String> topids = Lists.newArrayList();
        nodemap.forEach((id, tree) -> {
            if(tree.getParentKey() == null || TOP_NODE_ID.equals(tree.getParentKey())){
                topids.add(id);
                return;
            }
            Tree<T> parent = nodemap.get(tree.getParentKey());
            List<Tree<T>> children = parent.getChildren();
            if(children == null){
               children = Lists.newArrayList();

            }
            children.add(tree);
            parent.setChildren(children);
        });
        List<Tree<T>> tops = nodemap.entrySet().stream()
                .filter( e -> topids.contains(e.getKey()))
                .map(e -> e.getValue())
                .collect(Collectors.toList());

        Tree<T> root = new Tree<>(null);
        root.setLeaf(false);
        root.setChildren(tops);
        return root;
    }


}
