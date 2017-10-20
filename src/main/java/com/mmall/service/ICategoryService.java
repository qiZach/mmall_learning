package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

public interface ICategoryService {

    /**
     * 添加品类
     *
     * @param categoryName 品类名
     * @param parentId     品类父分类ID
     * @return 添加品类参数错误, 添加品类成功, 添加品类失败
     */
    ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     * 更新品类名
     *
     * @param categoryId   品类ID
     * @param categoryName 品类名
     * @return 更新品类名称参数错误, 更新品类名称成功, 更新品类名称错误
     */
    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    /**
     * 获取平级的品类信息
     *
     * @param categoryId 品类ID
     * @return 未找到当前分类的子分类, 返回品类List
     */
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 获取品类及子品类的ID List
     *
     * @param categoryId 品类ID
     * @return 返回当前品类和子品类的ID List, 默认返回0 和子品类的ID
     */
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
