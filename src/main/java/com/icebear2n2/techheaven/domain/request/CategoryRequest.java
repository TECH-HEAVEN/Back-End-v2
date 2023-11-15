package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    private Long categoryId;
    private String categoryName;
    public Category toEntity() {
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }

    /**
     * 값이 존재하는 경우 카테고리를 업데이트합니다.
     *
     * @param category 업데이트할 카테고리 엔터티
     */
    public void updateCategoryIfNotNull(Category category) {
        if (this.categoryName != null) {
            category.setCategoryName(this.categoryName);
        }
    }
}