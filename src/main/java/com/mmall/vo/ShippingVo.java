package com.mmall.vo;

import lombok.Data;

/**
 * 收货地址Vo
 *
 * @author zhangsiqi
 */
@Data
public class ShippingVo {
    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;
}
