package com.bimromatic.component.lib_base.utils;

import java.text.DecimalFormat;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 5/19/21
 * desc   : 内存计算
 * version: 1.0
 */
public class SapHanaUtils {

    /**
     * 将文件大小显示为GB,MB等形式
     */
    public static String size(long size) {

        if (size / (1024 * 1024 * 1024) > 0)
        {
            float tmpSize = (float) (size) / (float) (1024 * 1024 * 1024);
            DecimalFormat df = new DecimalFormat("#.##");
            return "" + df.format(tmpSize) + "GB";
        }
        else if (size / (1024 * 1024) > 0)
        {
            float tmpSize = (float) (size) / (float) (1024 * 1024);
            DecimalFormat df = new DecimalFormat("#.##");
            return "" + df.format(tmpSize) + "MB";
        }
        else if (size / 1024 > 0)
        {
            return "" + (size / (1024)) + "KB";
        }
        else {
            return "" + size + "B";
        }
    }
}
