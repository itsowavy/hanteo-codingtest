package hanteo.codingtest.question1;

import java.util.HashSet;
import java.util.Set;

/**
 * id: 아이디 <br>
 * parentIds: 상위 카테고리의 Id를 담고 있는 Set  <br>
 * name: 카테고리 이름 <br>
 * children: 해당 카테고리의 하위 카테고리를 담고 있는 Set <br>
 * canDuplicate: true일 경우 상위 카테고리를 2개 이상 설정 가능 (default = false)
 */
public class Category {

    private final Long id;
    private Set<Long> parentIds;
    private String name;
    private Set<Category> children;
    private boolean canDuplicate;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
        this.children = new HashSet<>();
        this.parentIds = new HashSet<>();
    }

    public Category(Long id, String name, Boolean canDuplicate) {
        this.id = id;
        this.name = name;
        this.children = new HashSet<>();
        this.parentIds = new HashSet<>();
        this.canDuplicate = canDuplicate;
    }

    /**
     * 하위 카테고리를 추가합니다.
     * @param child 추가할 하위 카테고리
     */
    public void addChild(Category child) {
        children.add(child);
        child.addParent(this.id);
    }

    /**
     * 하위 카테고리를 제거합니다.
     * @param child 제거할 하위 카테고리
     */
    public void deleteChild(Category child) {
        children.remove(child);
        child.deleteParent(this.id);
    }

    /**
     * Id를 이용해 상위 카테고리를 지정합니다. (private Method)
     * @param parentId 지정할 상위 카테고리의 Id
     */
    private void addParent(Long parentId) {
        parentIds.add(parentId);
    }

    /**
     * Id를 이용해 상위 카테고리를 제거합니다. (private Method)
     * @param parentId 제거할 상위 카테고리의 Id
     */
    private void deleteParent(Long parentId) {
        parentIds.remove(parentId);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Long> getParentIds() {
        return parentIds;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public Boolean canDuplicate() {
        return canDuplicate;
    }

}
