package hantoe.codingtest.question1;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CategoryToJsonTest {

    CategoryService categoryService = new CategoryService();
    Gson gson = new Gson();

    /**
     * 콘솔에서 JSON 데이터 확인 가능
     */
    @Test
    @DisplayName("카테고리를 JSON 으로 변환")
    void toJson() {
        Category firstCategory = categoryService.create("최상위 카테고리");
        Category secondCategory = categoryService.create("두번째 카테고리 B", firstCategory.getId());
        categoryService.create("두번째 카테고리 B", firstCategory.getId());

        categoryService.create("최하위 카테고리 B", secondCategory.getId());

        System.out.println(gson.toJson(firstCategory));
    }
}
