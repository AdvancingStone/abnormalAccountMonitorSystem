package com.bluehonour.util;

import java.util.UUID;

/**
 * 工具类：生成UUID
 */
public class UUIDUtils {

    /**
     * 生成指定位数的UUID
     * @param num UUID的长度
     * @return
     */
    public static String generateUUID(int num) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        if(num > uuid.length()){
            num = uuid.length();
        }
        String result = uuid.substring(0,num);
        return result;
    }

}
