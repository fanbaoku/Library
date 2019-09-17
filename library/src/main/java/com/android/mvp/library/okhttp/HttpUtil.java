package com.android.mvp.library.okhttp;


import java.io.File;
import java.util.List;
import java.util.Map;

import static com.android.mvp.library.okhttp.OkhttpUtil.okHttpGet;
import static com.android.mvp.library.okhttp.OkhttpUtil.okHttpPost;


public class HttpUtil {

//    private UserInfo userInfo;

    private static String Token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJ1c2VyX2lkIjoiZmMyYTZiMjc4YTNkNDE0MmJhZGRkODg4ZDA1NmQ5MGQiLCJleHAiOjE1NTkzNzAwMTh9.q3eYX1AP6n2BlfwRXsDAMASzkBPMeJShU67bjDawrRM";

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    public static final String FILE_TYPE_FILE = "file/*";
    public static final String FILE_TYPE_IMAGE = "image/*";
    public static final String FILE_TYPE_AUDIO = "audio/*";
    public static final String FILE_TYPE_VIDEO = "video/*";


//    public static HashMap getHeaderMap() {
//        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.getApplication());
//        String token = sharedPreferencesUtil.getToken();
//        String version = sharedPreferencesUtil.getVersion();
//        if(TextUtils.isEmpty(version)){
//            sharedPreferencesUtil.saveVersion(AppUtil.getVersionName(BaseApplication.getApplication()));
//            version = sharedPreferencesUtil.getVersion();
//        }
//        LogUtil.e("token-->" + token);
//        LogUtil.e("version-->" + version);
//        HashMap headerMap = new HashMap();
//        headerMap.put("token", token);
//        headerMap.put("version", version);
//        return headerMap;
//    }

    /**
     * get请求
     *
     * @param url：url
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void Get(Object tag, String url, CallBackUtil callBack) {
        okHttpGet(tag, url, null, null, callBack);
    }

    /**
     * get请求，可以传递参数
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void Get(Object tag, String url, Map<String, String> paramsMap, CallBackUtil callBack) {

        okHttpGet(tag, url, paramsMap, null, callBack);
    }

    /**
     * get请求，可以传递参数
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param headerMap：map集合，封装请求头键值对
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void Get(Object tag, String url, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(tag, METHOD_GET, url, paramsMap, headerMap, callBack).execute();
    }

    /**
     * post请求
     *
     * @param url：url
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void Post(Object tag, String url, CallBackUtil callBack) {
        okHttpPost(tag, url, null, null, callBack);
    }

    /**
     * post请求，可以传递参数
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void Post(Object tag, String url, Map<String, String> paramsMap, CallBackUtil callBack) {
        okHttpPost(tag, url, paramsMap, null, callBack);
    }

    /**
     * post请求，可以传递参数
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param headerMap：map集合，封装请求头键值对
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void Post(Object tag, String url, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(tag, METHOD_POST, url, paramsMap, headerMap, callBack).execute();
    }

    /**
     * post请求
     *
     * @param url：url
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void Put(Object tag, String url, CallBackUtil callBack) {
        Put(tag, url, null, callBack);
    }

    /**
     * post请求，可以传递参数
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void Put(Object tag, String url, Map<String, String> paramsMap, CallBackUtil callBack) {
        Put(tag, url, paramsMap, null, callBack);
    }

    /**
     * post请求，可以传递参数
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param headerMap：map集合，封装请求头键值对
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void Put(Object tag, String url, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(tag, METHOD_PUT, url, paramsMap, headerMap, callBack).execute();
    }

    /**
     * post请求
     *
     * @param url：url
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void Delete(Object tag, String url, CallBackUtil callBack) {
        Delete(tag, url, null, callBack);
    }

    /**
     * post请求，可以传递参数
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void Delete(Object tag, String url, Map<String, String> paramsMap, CallBackUtil callBack) {
        Delete(tag, url, paramsMap, null, callBack);
    }

    /**
     * post请求，可以传递参数
     *
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param headerMap：map集合，封装请求头键值对
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void Delete(Object tag, String url, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(tag, METHOD_DELETE, url, paramsMap, headerMap, callBack).execute();
    }

    /**
     * post请求，可以传递参数
     *
     * @param url：url
     * @param jsonStr：json格式的键值对参数
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void PostJson(Object tag, String url, String jsonStr, CallBackUtil callBack) {
        PostJson(tag, url, jsonStr, null, callBack);
    }

    /**
     * post请求，可以传递参数
     *
     * @param url：url
     * @param jsonStr：json格式的键值对参数
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void PostJsonHeaderMap(Object tag, String url, String jsonStr, String id, CallBackUtil callBack) {
//        Map<String, String> headerMap=new HashMap<>();
//        headerMap.put("loginUid",id);
        PostJson(tag, url, jsonStr, null, callBack);
    }

    /**
     * post请求，可以传递参数
     *
     * @param url：url
     * @param jsonStr：json格式的键值对参数
     * @param headerMap：map集合，封装请求头键值对
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void PostJson(Object tag, String url, String jsonStr, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(tag, METHOD_POST, url, jsonStr, headerMap, callBack).execute();
    }

    /**
     * post请求，上传单个文件
     *
     * @param url：url
     * @param file：File对象
     * @param fileKey：上传参数时file对应的键
     * @param fileType：File类型，是image，video，audio，file
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。还可以重写onProgress方法，得到上传进度
     */
    public static void UploadFile(Object tag, String url, File file, String fileKey, String fileType, CallBackUtil callBack) {
        UploadFile(tag, url, file, fileKey, fileType, null, callBack);
    }

    /**
     * post请求，上传单个文件
     *
     * @param url：url
     * @param file：File对象
     * @param fileKey：上传参数时file对应的键
     * @param fileType：File类型，是image，video，audio，file
     * @param paramsMap：map集合，封装键值对参数
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。还可以重写onProgress方法，得到上传进度
     */
    public static void UploadFile(Object tag, String url, File file, String fileKey, String fileType, Map<String, String> paramsMap, CallBackUtil callBack) {
        UploadFile(tag, url, file, fileKey, fileType, paramsMap, null, callBack);
    }

    /**
     * post请求，上传单个文件
     *
     * @param url：url
     * @param file：File对象
     * @param fileKey：上传参数时file对应的键
     * @param fileType：File类型，是image，video，audio，file
     * @param paramsMap：map集合，封装键值对参数
     * @param headerMap：map集合，封装请求头键值对
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。还可以重写onProgress方法，得到上传进度
     */
    public static void UploadFile(Object tag, String url, File file, String fileKey, String fileType, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(tag, METHOD_POST, url, paramsMap, file, fileKey, fileType, headerMap, callBack).execute();
    }

    /**
     * post请求，上传多个文件，以list集合的形式
     *
     * @param url：url
     * @param fileList：集合元素是File对象
     * @param fileKey：上传参数时fileList对应的键
     * @param fileType：File类型，是image，video，audio，file
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void UploadListFile(Object tag, String url, List<File> fileList, String fileKey, String fileType, CallBackUtil callBack) {
        UploadListFile(tag, url, null, fileList, fileKey, fileType, callBack);
    }

    /**
     * post请求，上传多个文件，以list集合的形式
     *
     * @param url：url
     * @param fileList：集合元素是File对象
     * @param fileKey：上传参数时fileList对应的键
     * @param fileType：File类型，是image，video，audio，file
     * @param paramsMap：map集合，封装键值对参数
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void UploadListFile(Object tag, String url, Map<String, String> paramsMap, List<File> fileList, String fileKey, String fileType, CallBackUtil callBack) {
        UploadListFile(tag, url, paramsMap, fileList, fileKey, fileType, null, callBack);
    }

    /**
     * post请求，上传多个文件，以list集合的形式
     *
     * @param url：url
     * @param fileList：集合元素是File对象
     * @param fileKey：上传参数时fileList对应的键
     * @param fileType：File类型，是image，video，audio，file
     * @param paramsMap：map集合，封装键值对参数
     * @param headerMap：map集合，封装请求头键值对
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void UploadListFile(Object tag, String url, Map<String, String> paramsMap, List<File> fileList, String fileKey, String fileType, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(tag, METHOD_POST, url, paramsMap, fileList, fileKey, fileType, headerMap, callBack).execute();
    }

    /**
     * post请求，上传多个文件，以map集合的形式
     *
     * @param url：url
     * @param fileMap：集合key是File对象对应的键，集合value是File对象
     * @param fileType：File类型，是image，video，audio，file
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void UploadMapFile(Object tag, String url, Map<String, File> fileMap, String fileType, CallBackUtil callBack) {
        UploadMapFile(tag, url, fileMap, fileType, null, callBack);
    }

    /**
     * post请求，上传多个文件，以map集合的形式
     *
     * @param url：url
     * @param fileMap：集合key是File对象对应的键，集合value是File对象
     * @param fileType：File类型，是image，video，audio，file
     * @param paramsMap：map集合，封装键值对参数
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void UploadMapFile(Object tag, String url, Map<String, File> fileMap, String fileType, Map<String, String> paramsMap, CallBackUtil callBack) {
        UploadMapFile(tag, url, fileMap, fileType, paramsMap, null, callBack);
    }

    /**
     * post请求，上传多个文件，以map集合的形式
     *
     * @param url：url
     * @param fileMap：集合key是File对象对应的键，集合value是File对象
     * @param fileType：File类型，是image，video，audio，file
     * @param paramsMap：map集合，封装键值对参数
     * @param headerMap：map集合，封装请求头键值对
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void UploadMapFile(Object tag, String url, Map<String, File> fileMap, String fileType, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(tag, METHOD_POST, url, paramsMap, fileMap, fileType, headerMap, callBack).execute();
    }

    /**
     * 下载文件,不带参数
     */
    public static void DownloadFile(Object tag, String url, CallBackUtil.CallBackFile callBack) {
        DownloadFile(tag, url, null, callBack);
    }

    /**
     * 下载文件,带参数
     */
    public static void DownloadFile(Object tag, String url, Map<String, String> paramsMap, CallBackUtil.CallBackFile callBack) {
        okHttpGet(tag, url, paramsMap, null, callBack);
    }

    /**
     * 加载图片
     */
    public static void GetBitmap(Object tag, String url, CallBackUtil.CallBackBitmap callBack) {
        GetBitmap(tag, url, null, callBack);
    }

    /**
     * 加载图片，带参数
     */
    public static void GetBitmap(Object tag, String url, Map<String, String> paramsMap, CallBackUtil.CallBackBitmap callBack) {
        okHttpGet(tag, url, paramsMap, null, callBack);
    }
}
