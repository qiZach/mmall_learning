电商平台：
  1. 用户
  2. 地址
  3. 购物车
  4. 订单
  5. 支付

架构：
  1. 采用分布式Tomcat，使用Nginx方向代理完成请求分发
  2. 使用vftp服务器存储图片，提高图片加载速度
  3. 使用Redis作为缓存中间件实现单点登录功能，并对查询数据做缓存，避免多次查询数据库
