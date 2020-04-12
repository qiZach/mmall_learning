package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单上平VO
 *
 * @author zhangsiqi
 */
@Data
public class OrderItemVo {
    private Long orderNo;

    private Integer productId;

    private String productName;

    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private String createTime;
}
