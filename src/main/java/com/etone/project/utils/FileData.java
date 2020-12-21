package com.etone.project.utils;

import java.util.Arrays;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class FileData {
    public String fileName;
    public byte[] data;
    public FileData(String fileName, byte[] data) {
        
        //检查最后两个数据是不是都==62（对应ascii码表中的>）
        
        if(data[data.length - 2] == 62 && data[data.length - 1] == 62) {
            data = Arrays.copyOf(data, data.length -1);
        }
        
        this.data = data;
        this.fileName = fileName;
    }
}
