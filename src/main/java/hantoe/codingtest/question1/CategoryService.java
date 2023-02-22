package hantoe.codingtest.question1;

import hantoe.codingtest.question1.exception.AlreadyHasParentException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryService {

    private static Map<Long, Category> categoryMap = new HashMap<>();

    private static Long sequence = 0L;

    /**
     * @param id 조회할 카테고리의 id
     */
    public Category searchById(Long id) {
        return categoryMap.get(id);
    }

    /**
     * 이름으로 카테고리를 조회합니다. <br>
     * 카테고리의 이름은 중복을 허용하기 때문에 List로 값을 리턴합니다.
     * @param name 조회할 카테고리의 이름
     */
    public List<Category> searchByName(String name) {
        return categoryMap.values().stream().filter(category -> category.getName().equals(name))
                          .collect(Collectors.toList());
    }

    /**
     * 기본 카테고리를 생성합니다.
     * @param name 생성할 카테고리 이름
     */
    public Category create(String name) {
        Category category = new Category(++sequence, name);
        categoryMap.put(category.getId(), category);

        return category;
    }

    /**
     * parentId를 인자값으로 전달할 경우 해당 카테고리의 하위 카테고리가 생성됩니다.
     *
     * @param name     생성할 카테고리 이름
     * @param parentId 상위 카테고리의 id
     */
    public Category create(String name, Long parentId) {
        Category category = new Category(++sequence, name);
        searchById(parentId).addChild(category);
        categoryMap.put(category.getId(), category);

        return category;
    }

    /**
     * 여러 개의 상위 카테고리에 포함될 수 있는 카테고리를 생성합니다.
     * @param name 생성할 카테고리 이름
     * @param parentId 지정할 상위 카테고리의 id
     */
    public Category createCanDuplicate(String name, Long parentId) {
        Category category = new Category(++sequence, name, true);
        searchById(parentId).addChild(category);
        categoryMap.put(category.getId(), category);

        return category;
    }


    public void delete(Long id) {
        categoryMap.remove(id);
    }

    /**
     * 상위 카테고리에 하위 카테고리를 추가 합니다.
     * @param parentId 상위 카테고리 id
     * @param childId 추가할 하위 카테고리 id
     * @exception AlreadyHasParentException canDuplicate가 false인 Category가 2개 이상의 상위 카테고리에 속할 수 없습니다.
     *                                      여러 개의 상위 카테고리에 포함되려면 canDuplicate를 true로 설정해야 합니다.
     */
    public void addChild(Long parentId, Long childId) {
        Category parentCategory = searchById(parentId);
        Category childCategory = searchById(childId);
        if (childCategory.canDuplicate() == false && childCategory.getParentIds().size() >= 1) {
            throw new AlreadyHasParentException();
        }
        parentCategory.addChild(childCategory);
    }

    /**
     * 상위 카테고리에서 하위 카테고리를 제거 합니다.
     * @param parentId 상위 카테고리 id
     * @param childId 제거할 하위 카테고리 id
     */
    public void deleteChild(Long parentId, Long childId) {
        Category parentCategory = searchById(parentId);
        Category childCategory = searchById(childId);

        parentCategory.deleteChild(childCategory);
    }

    /**
     * 하위 카테고리의 상위 카테고리를 변경합니다.
     * @param currentParentId 현재 상위 카테고리 id
     * @param nextParentId 바꿀 상위 카테고리 id
     * @param childId 상위 카테고리를 변경할 하위 카테고리 id
     */
    public void changeParent(Long currentParentId, Long nextParentId, Long childId) {
        deleteChild(currentParentId, childId);
        addChild(nextParentId, childId);
    }

    public Map<Long, Category> getCategoryMap() {
        return categoryMap;
    }
}
