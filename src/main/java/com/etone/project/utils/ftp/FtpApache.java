 package com.etone.project.utils.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-9-21
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
public class FtpApache {
    private static FTPClient ftpClient = new FTPClient();
    private static String encoding = System.getProperty("file.encoding");

    public static  String pwd="hlzx_gd123";
    public static  String ip="188.0.52.27";
    public static  String user="hlzx_gd";

    /**
     * Description: 向FTP服务器上传文件
     *
     * @Version1.0
     * @param url
     *            FTP服务器hostname
     * @param port
     *            FTP服务器端口
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param path
     *            FTP服务器保存目录,如果是根目录则为“/”
     * @param filename
     *            上传到FTP服务器上的文件名
     * @param input
     *            本地文件输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String url, int port, String username,
                                     String password, String path, String filename, InputStream input) {
        boolean result = false;

        try {
            int reply;
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpClient.connect(url);
            // ftp.connect(url, port);// 连接FTP服务器
            // 登录
            ftpClient.login(username, password);
            ftpClient.setControlEncoding(encoding);
            // 检验是否连接成功
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                System.out.println("连接失败");
                ftpClient.disconnect();
                return result;
            }

            // 转移工作目录至指定目录下
            boolean change = ftpClient.changeWorkingDirectory(path);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            if (change) {
                result = ftpClient.storeFile(new String(filename.getBytes(encoding),"iso-8859-1"), input);
                if (result) {
                    System.out.println("上传成功!");
                }
            }
            input.close();
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * 将本地文件上传到FTP服务器上
     *
     */
    public void testUpLoadFromDisk() {
        try {
            FileInputStream in = new FileInputStream(new File("E:/号码.txt"));
            boolean flag = uploadFile("127.0.0.1", 21, "zlb","123", "/", "哈哈.txt", in);
            System.out.println(flag);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Description: 从FTP服务器下载文件
     *
     * @Version1.0
     * @param url
     *            FTP服务器hostname
     * @param port
     *            FTP服务器端口
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param remotePath
     *            FTP服务器上的相对路径
     * @param fileName
     *            要下载的文件名
     * @param localPath
     *            下载后保存到本地的路径
     * @return
     */
    public static boolean downFile(String url, int port, String username,
                                   String password, String remotePath, String fileName,
                                   String localPath) {
        boolean result = false;
        try {
            int reply;
            //ftpClient.setControlEncoding(encoding);

            /*
             *  为了上传和下载中文文件，有些地方建议使用以下两句代替
             *  new String(remotePath.getBytes(encoding),"iso-8859-1")转码。
             *  经过测试，通不过。
             */
//            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
//            conf.setServerLanguageCode("zh");
            ftpClient.connect(url, port);
            System.out.println("使用"+url+":"+port+"进行连接成功");
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpClient.login(username, password);// 登录
            System.out.println("使用"+username+"&&"+password+"登录ftp");
            // 设置文件传输类型为二进制
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 获取ftp登录应答代码
            reply = ftpClient.getReplyCode();
            // 验证是否登陆成功
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.out.println("FTP server refused connection.");
                return result;
            }
            // 转移到FTP服务器目录至指定的目录下
            ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(encoding),"iso-8859-1"));
            // 获取文件列表
            FTPFile[] fs = ftpClient.listFiles();
            System.out.println("文件个数为："+fs.length);
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());
                    OutputStream is = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            ftpClient.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * 将FTP服务器上文件下载到本地
     *
     */
    public void testDownFile() {
        try {
            boolean flag = downFile("180.0.52.27", 22, "hlzx_gd","hlzx_gd123", "/home/hlxz/FMS_ALARMDATA/GZ/20160920/",
                    "FMS_ALARMDATA_GZ_CHUANSONG_20160920.CSV.gz", "/home/hlxz/");
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean connect() {

        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ip);
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.setControlEncoding("GBK");
                if (ftpClient.login(user, pwd)) {
                    System.out.println("FTP 连接结果：连上了！");
                    //ftpClient.setControlEncoding("GBK"); // 设置编码
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); // 设置文件类型（二进制）
                    //File localFile = new File("/home/1.rpm");
                    ftpClient.enterLocalPassiveMode();
                    FTPFile[] fs = ftpClient.listFiles();
                    System.out.println("hlxz_gd文件个数为："+fs.length);
                    String remotePath ="/EOMS_GZCL/CZ/20160930/";
                    if(changeDirectory(remotePath)){
                        //ftpClient.makeDirectory(remotePath);
                        //ftpClient.changeWorkingDirectory(remotePath);
                        FTPFile[] fs2 = ftpClient.listFiles();
                        System.out.println("hlxz_gd/EOMS_GZCL文件个数为："+fs2.length);
                        File localFile = new File("/home/"+""+"czGZCL20160930.csv");
                        OutputStream is = new FileOutputStream(localFile);
                        ftpClient.retrieveFile("czGZCL20160930.csv", is);
                        is.close();
                    }else{
                        System.out.println("切换目录失败");
                    }
                    return true;
                }
            }
            disconnect();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 断开与远程服务器的连接
     */
    public static void disconnect() {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 切换多级目录，如果目录不存在则创建
     *
     * @param directory
     * @return
     */
    public static boolean changeDirectory(String directory) {
        try {
            int start = directory.startsWith("/") ? 1 : 0;
            int end = directory.indexOf("/", start);
            while (true) {
                String subDirectory = directory.substring(start, end);
                if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                    if (ftpClient.makeDirectory(subDirectory)) {
                        ftpClient.changeWorkingDirectory(subDirectory);
                    } else {
                        System.out.println("创建目录失败");
                        return false;
                    }
                }
                start = end + 1;
                end = directory.indexOf("/", start);

                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
