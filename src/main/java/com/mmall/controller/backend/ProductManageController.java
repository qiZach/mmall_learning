package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zhangsiqi
 */
@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @ResponseBody
    @RequestMapping("save.do")
    public ServerResponse productSave(HttpServletRequest request, Product product) {
        // 填充我们增加产品的业务逻辑
        return iProductService.saveOrUpdateProduct(product);
    }

    @ResponseBody
    @RequestMapping("set_sale_status.do")
    public ServerResponse setSaleStatus(HttpServletRequest request, Integer productId, Integer status) {
        return iProductService.setSaleStatus(productId, status);
    }

    @ResponseBody
    @RequestMapping("detail.do")
    public ServerResponse getDetail(HttpServletRequest request, Integer productId) {
        return iProductService.manageProductDetail(productId);
    }

    @ResponseBody
    @RequestMapping("list.do")
    public ServerResponse getList(HttpServletRequest request, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return iProductService.getProductList(pageNum, pageSize);
    }

    @ResponseBody
    @RequestMapping("search.do")
    public ServerResponse productSearch(HttpServletRequest request, String productName, Integer productId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return iProductService.searchProduct(productName, productId, pageNum, pageSize);
    }

    @ResponseBody
    @RequestMapping("upload.do")
    public ServerResponse upload(HttpServletRequest request, @RequestParam(value = "upload_file", required = false) MultipartFile file) {
        // 获取文件上传到Tomcat中的路径
        String path = request.getSession().getServletContext().getRealPath("upload");
        // 上传文件到服务器, 返回文件名
        String targetFilename = iFileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFilename;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFilename);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }

    @ResponseBody
    @RequestMapping("richtext_img_upload.do")
    public Map richTextImg_upload(HttpServletRequest request, @RequestParam(value = "upload_file", required = false) MultipartFile file,
                                  HttpServletResponse response) {
        // 富文本中对于返回值有自己的要求, 我们使用的是simditor所以按照simditor的要求进行返回
        /*{
            "success": true/false,
            "msg": "error message", # optional
            "file_path": "[real file path]"
        }*/
        Map resultMap = Maps.newHashMap();
        // 获取文件上传到Tomcat中的路径
        String path = request.getSession().getServletContext().getRealPath("upload");
        // 上传文件到服务器, 返回文件名
        String targetFilename = iFileService.upload(file, path);
        if (StringUtils.isBlank(targetFilename)) {
            resultMap.put("success", false);
            resultMap.put("msg", "上传失败");
            return resultMap;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFilename;
        resultMap.put("success", true);
        resultMap.put("msg", "上传成功");
        resultMap.put("file_path", url);
        response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
        return resultMap;

    }

}