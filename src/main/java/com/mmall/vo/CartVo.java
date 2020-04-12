package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车Vo
 * @author zhangsiqi
 */
@Data
public class CartVo {

    List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice;
    /**
     * 是否全选
     */
    private Boolean allChecked;
    private String imageHost;
}
