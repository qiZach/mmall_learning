package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

/**
 * @author zhangsiqi
 */
public interface IProductService {

    /**
     * 新增或更新商品
     *
     * @param product 商品
     * @return 更新产品成功, 更新产品失败, 新增商品成功, 新增商品失败,新增或更新产品参数不正确
     */
    ServerResponse saveOrUpdateProduct(Product product);

    /**
     * 设置商品状态
     *
     * @param productId 商品主键
     * @param status    商品状态
     * @return 修改产品销售状态成功
     */
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    /**
     * 后台商品展示详情
     *
     * @param productId 商品ID
     * @return 返回商品的VO对象
     */
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    /**
     * 根据页码和页面容量查询其中你的商品List
     *
     * @param pageNum  页码
     * @param pageSize 页面容量
     * @return 查询List
     */
    ServerResponse getProductList(Integer pageNum, Integer pageSize);

    /**
     * 根据商品名称(模糊查询)或商品ID 进行查询
     *
     * @param productName 商品名称(模糊查询)
     * @param productId   商品ID
     * @param pageNum     页码
     * @param pageSize    页面容量
     * @return 查询List
     */
    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    /**
     * 用户商品详情展示
     *
     * @param productId 商品ID
     * @return 返回商品的VO对象
     */
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    /**
     * 根据查询字段或品类ID 进行模糊查询
     *
     * @param keyword    查询字段
     * @param categoryId 品类ID
     * @param pageNum    页码
     * @param pageSize   页面容量
     * @param orderBy    排序方式
     * @return 分页对象
     */
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
