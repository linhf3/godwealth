package com.godwealth.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 节假日
 * @author sie_linhongfei
 * @createDate 2022/05/28 10:27
 */
public class HolidaysUtils {

    public static boolean whetherToWork(){
        String httpUrl = "http://tool.bitefu.net/jiari/";
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        // 处理节假日
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        String httpArg = f.format(new Date());
        httpUrl = httpUrl + "?d=" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);

            }
            reader.close();
            result = sbf.toString();
            // 0 上班 1周末 2节假日
            if ("0".equals(result)){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }
}
