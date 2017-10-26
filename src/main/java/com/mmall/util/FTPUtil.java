package com.mmall.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class FTPUtil {

    private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private static String ftpId = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");

    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpId, 21, ftpUser, ftpPass);
        logger.info("开始连接FTP服务器");
        // 放到img 文件夹下
        boolean result = ftpUtil.uploadFile("img", fileList);
        logger.info("结束上传, 上传结果:{}", result);

        return result;
    }

    /**
     * 将fileList上传到服务器的remotePath下
     *
     * @param remotePath 远程路径
     * @param fileList   文件列表
     * @return 上传成功or失败
     * @throws IOException 异常
     */
    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;
        // 连接FTP服务器
        if (connectServer(this.ip, this.user, this.pwd)) {
            try {
                // 改变路径
                if (!ftpClient.changeWorkingDirectory(remotePath)) {
                    logger.error("切换目录失败,当前目录为: " + ftpClient.printWorkingDirectory());
                }

                // 设置缓冲区
                ftpClient.setBufferSize(1024);
                // 设置编码
                ftpClient.setControlEncoding("UTF-8");
                // 将文件类型设置为二进制类型
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                logger.info("当前文件路径: " + ftpClient.printWorkingDirectory());
                for (File file : fileList) {
                    // 获取文件流
                    fis = new FileInputStream(file);
                    // 存储文件流
                    ftpClient.storeFile(file.getName(), fis);
                }
            } catch (IOException e) {
                logger.error("上传文件异常", e);
                uploaded = false;
                e.printStackTrace();
            } finally {
                fis.close();
                ftpClient.disconnect();
            }
            return uploaded;
        }
        return false;
    }

    private boolean connectServer(String ip, String user, String pwd) {

        boolean isSuccess = false;

        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user, pwd);

        } catch (IOException e) {
            logger.error("连接FTP服务器异常", e);
        }
        return isSuccess;
    }


    public static String getFtpId() {
        return ftpId;
    }

    public static void setFtpId(String ftpId) {
        FTPUtil.ftpId = ftpId;
    }

    public static String getFtpUser() {
        return ftpUser;
    }

    public static void setFtpUser(String ftpUser) {
        FTPUtil.ftpUser = ftpUser;
    }

    public static String getFtpPass() {
        return ftpPass;
    }

    public static void setFtpPass(String ftpPass) {
        FTPUtil.ftpPass = ftpPass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

}
