package hantoe.codingtest.question1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryServiceTest {

    CategoryService categoryService;
    Map<Long, Category> categoryMap;

    @BeforeEach
    void beforeEach() {
        categoryService = new CategoryService();
        categoryMap = categoryService.getCategoryMap();
    }

    @Test
    @DisplayName("카테고리를 생성할 수 있다.")
    void createCategory() {
        Category category = categoryService.create("카테고리 이름");

        assertThat(categoryMap.values()).contains(category);
    }

    @Test
    @DisplayName("카테고리를 삭제할 수 있다")
    void deleteCategory() {
        Category category = categoryService.create("카테고리 이름");

        categoryService.delete(category.getId());

        assertThat(categoryMap).isEmpty();
    }

    @Test
    @DisplayName("카테고리를 id로 조회할 수 있다.")
    void searchById() {
        Category category = categoryService.create("카테고리 이름");

        Category findCategory = categoryService.searchById(category.getId());

        assertThat(findCategory).isEqualTo(category);
    }

    @Test
    @DisplayName("카테고리를 이름으로 조회할 수 있다.")
    void searchByName() {
        Category category = categoryService.create("카테고리 이름");

        List<Category> findCategoryList = categoryService.searchByName("카테고리 이름");

        assertThat(findCategoryList).contains(category);
    }

    @Test
    @DisplayName("상위 카테고리의 하위 카테고리를 생성할 수 있다.")
    void createChild() {
        Category parentCategory = categoryService.create("상위 카테고리");
        Category childCategory = categoryService.create("하위 카테고리", parentCategory.getId());

        assertThat(parentCategory.getChildren()).contains(childCategory);
        assertThat(childCategory.getParentIds()).contains(parentCategory.getId());
    }

    @Test
    @DisplayName("상위 카테고리의 하위 카테고리를 제거할 수 있다.")
    void deleteChild() {
        Category parentCategory = categoryService.create("상위 카테고리");
        Category childCategory = categoryService.create("하위 카테고리", parentCategory.getId());

        categoryService.deleteChild(parentCategory.getId(), childCategory.getId());

        assertThat(parentCategory.getChildren()).isEmpty();
        assertThat(childCategory.getParentIds()).isEmpty();
    }

    @Test
    @DisplayName("하위 카테고리의 상위 카테고리를 변경할 수 있다.")
    void changeParent() {
        Category parentCategoryA = categoryService.create("상위 카테고리 A");
        Category parentCategoryB = categoryService.create("상위 카테고리 B");
        Category childCategory = categoryService.create("하위 카테고리", parentCategoryA.getId());

        categoryService.changeParent(parentCategoryA.getId(), parentCategoryB.getId(),
                                     childCategory.getId());

        assertThat(parentCategoryA.getChildren()).isEmpty();
        assertThat(parentCategoryB.getChildren()).contains(childCategory);
        assertThat(childCategory.getParentIds()).doesNotContain(parentCategoryA.getId());
        assertThat(childCategory.getParentIds().contains(parentCategoryB.getId()));

    }

    @Test
    @DisplayName("여러 개의 상위 카테고리에 포함되는 하위 카테고리를 생성할 수 있다.")
    void createCanDuplicateCategory() {
        Category parentCategoryA = categoryService.create("상위 카테고리 A");
        Category parentCategoryB = categoryService.create("상위 카테고리 B");
        Category canDuplicateCategory =
                categoryService.createCanDuplicate("익명 카테고리", parentCategoryA.getId());

        categoryService.addChild(parentCategoryB.getId(), canDuplicateCategory.getId());

        assertThat(parentCategoryA.getChildren()).contains(canDuplicateCategory);
        assertThat(parentCategoryB.getChildren()).contains(canDuplicateCategory);
        assertThat(canDuplicateCategory.getParentIds())
                .contains(parentCategoryA.getId(), parentCategoryB.getId());
    }

}