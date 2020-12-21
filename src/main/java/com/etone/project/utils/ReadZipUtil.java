package com.etone.project.utils;

import com.etone.project.utils.ziputil.CNZipInputStream;
import com.etone.project.utils.ziputil.ZipEntry;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadZipUtil {

    private List<CNZipInputStream> zipIsList;
    private String charsetName;

    public ReadZipUtil() {
        this.zipIsList = new ArrayList();
    }

    public ReadZipUtil(String charsetName) {
        this();
        this.charsetName = charsetName;
    }

//    public ReadZipUtil(InputStream in) {
//        this();
//        this.zipIsList.add(new CNZipInputStream(in));
//    }

    public ReadZipUtil(InputStream in, String charsetName) {
        this();
        this.charsetName = charsetName;
        if (in != null) {
            CNZipInputStream zin = new CNZipInputStream(in, this.charsetName);
            zipIsList.add(zin);
        }
    }

    public FileData getNextFileData() {

        if (this.zipIsList.isEmpty()) {
            return null;
        }

        for (int i = 0; i < this.zipIsList.size(); i++) {
            CNZipInputStream zin = this.zipIsList.get(i);
            ZipEntry ze;
            
            try {
                while ((ze = zin.getNextEntry()) != null) {
                    if (ze.isDirectory()) {
                        // System.out.print("directory - " + ze.getName() + " : "  
                        // + ze.getSize() + " bytes");  
                        // System.out.println();  
                    } else {

                        String[] split = ze.getName().split("\\u002E"); //u002E是.转义

                        String suffix = split[split.length - 1];

                        if (suffix.toUpperCase().equals("ZIP")) {

                            this.zipIsList.add(0, new CNZipInputStream(new ByteArrayInputStream(getBytes(zin)), this.charsetName));
                            return getNextFileData();
                        } else {
                            byte[] datas = getBytes(zin);
                            if(datas.length == 0) {
                                continue;
                            }
                            return new FileData(ze.getName(), datas);
                        }

                    }

                }
                
                zin.closeEntry();
                
                this.zipIsList.remove(i);
                i--;

            } catch (IOException ex) {

                Logger.getLogger(ReadZipUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub  
        try {
            readZipFile(new FileInputStream("E:/work/20130813/广州mr.zip"));
        } catch (Exception e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
    }

    public static void readZipFile(InputStream in) {

        CNZipInputStream zin = new CNZipInputStream(in, "gb2312");
        ZipEntry ze;
        try {
            while ((ze = zin.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    // System.out.print("directory - " + ze.getName() + " : "  
                    // + ze.getSize() + " bytes");  
                    // System.out.println();  
                } else {
                    System.err.println("file - " + ze.getName() + " : "
                            + ze.getSize() + " bytes"
                            + ",CompressedSize" + ze.getCompressedSize());
                    Long size = ze.getSize(); //返回条目数据的未压缩大小；如果未知，则返回 -1。

                    String[] split = ze.getName().split("\\u002E"); //u002E是.转义

                    String suffix = split[split.length - 1];

                    if (suffix.toUpperCase().equals("ZIP")) {

                        readZipFile(new ByteArrayInputStream(getBytes(zin)));
                    } else {
                        byte[] datas = getBytes(zin);

                        InputStreamReader inputStreamReader;

                        //判断编码是否为UTF-8
                        if (datas[0] == -17 && datas[1] == -69 && datas[2] == -65) {
                            inputStreamReader = new InputStreamReader(new ByteArrayInputStream(datas), "utf-8");
                        } else {
                            inputStreamReader = new InputStreamReader(new ByteArrayInputStream(datas), "gbk");
                        }


                        BufferedReader br = new BufferedReader(inputStreamReader);
                        String line;
                        try {
                            while ((line = br.readLine()) != null) {
                                System.out.println(line);
                            }
                        } catch (IOException iOException) {
                        }
                        br.close();

                        System.out.println();
                    }


                }

            }

            zin.closeEntry();
        } catch (IOException ex) {

            Logger.getLogger(ReadZipUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static byte[] getBytes(CNZipInputStream zin) {
        try {
            byte[] data = new byte[1];
            List<Byte> dataList = new ArrayList();
            while (zin.available() != 0) {
                zin.read(data, 0, 1);
                dataList.add(data[0]);
            }

            byte[] datas = new byte[dataList.size()];
            for (int i = 0; i < dataList.size(); i++) {
                datas[i] = dataList.get(i).byteValue();
            }

            return datas;
        } catch (IOException ex) {
            Logger.getLogger(ReadZipUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new byte[0];
    }
}