package com.godwealth.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtils {

    /**
     * 发送GET请求
     *
     * @param url         请求URL
     * @param queryParams 请求参数
     * @return 响应结果
     */
    public static String doGet(String url, Map<String, Object> queryParams)
            throws IOException {
        // 拼接参数
        if (queryParams != null && !queryParams.isEmpty()) {
            url += "?" + urlEncode(queryParams);
        }

        // GET请求
        HttpGet get = new HttpGet(url);
        // 请求配置
        get.setConfig(requestConfig());

        // 执行请求
        return getResult(get);
    }

    /**
     * 发送GET请求,专门为获取新浪五日数据改造的get
     *
     * @param url         请求URL
     * @param queryParams 请求参数
     * @return 响应结果
     */
    public static String sendGet(String url, Map<String, Object> queryParams)
            throws IOException {
        // 拼接参数
        if (queryParams != null && !queryParams.isEmpty()) {
            url += "?" + urlEncode(queryParams);
        }

        // GET请求
        HttpGet get = new HttpGet(url);
        get.setHeader(new BasicHeader("content-type", "application/javascript; charset=gbk"));
        get.setHeader(new BasicHeader("vary", "Accept-Encoding"));
        get.setHeader(new BasicHeader("server", "nginx"));
        get.setHeader(new BasicHeader("x-via-ssl", "ssl.27.sinag1.shx.lb.sinanode.com"));
        get.setHeader(new BasicHeader("dpool_header", "stock7-finance-sina-com-cn-canary-bc6985f95-qlr2m"));
        get.setHeader(new BasicHeader("dpool_lb7_header", "draka48"));
        get.setHeader(new BasicHeader("ddpool", "stock7-finance-sina-com-cn"));
        // 请求配置
        get.setConfig(requestConfig());

        // 执行请求
        return getResult(get);
    }

    /**
     * URL参数编码
     *
     * @param queryParams 请求参数
     * @return 参数编码结果
     */
    private static String urlEncode(Map<?, ?> queryParams)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : queryParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
                    URLEncoder.encode(entry.getValue().toString(), "UTF-8")
            ));
        }
        return sb.toString();
    }


    /**
     * 请求配置
     *
     * @return 请求配置
     */
    private static RequestConfig requestConfig() {
        return RequestConfig
                .custom()
                // 连接主机超时时间
                .setConnectTimeout(35000)
                // 请求超时时间
                .setConnectionRequestTimeout(35000)
                // 数据读取超时时间
                .setSocketTimeout(60000)
                .build();
    }


    /**
     * 执行HTTP请求
     *
     * @param requestBase HTTP请求
     * @return 响应结果
     */
    private static String getResult(HttpRequestBase requestBase) throws IOException {
        // HTTP客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 响应结果
        String result = EntityUtils.toString(httpClient.execute(requestBase).getEntity());
        httpClient.close();
        // 返回响应结果
        return result;
    }
}
