package com.example.hongcheng.data.retrofit;

public class HttpConstants {

    //网络日志大小
    public static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;

    //超时时间
    public static final long CONNECT_TIMEOUT = 5000L;

    //设缓存有效期为两个星期
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 14;

    //查询缓存的Cache-Control设置
    public static final String CACHE_CONTROL_CACHE = "public, only-if-cached, max-stale=" + CACHE_STALE_SEC;

    //查询缓存的Cache-Control设置
    public static final String CACHE_CONTROL_ERROR = "public, max-stale=" + 5;

    //查询网络的Cache-Control设置
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";

    //请求成功code
    public static final int COMMON_SUCCESS_CODE = 0;

    //基础路径
    public static final String BASE_URL = "http://47.106.159.229/api/v1/";
}
