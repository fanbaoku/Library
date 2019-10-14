package com.android.mvp.library.okhttp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.mvp.library.base.BaseBean;
import com.android.mvp.library.utils.Base64Util;
import com.android.mvp.library.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * callback工具类
 *
 * @param <B>
 */
public abstract class CallBackUtil<B> {
    public static Handler mMainHandler = new Handler(Looper.getMainLooper());
    Class clazz;

    public void onProgress(float progress, long total) {
    }

    public void onError(final Call call, final Exception e) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(call, e);
            }
        });
    }

    public void onSuccess(Call call, Response response) {
        final B obj = onParseResponse(call, response);
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (obj != null) onResponse(obj);
            }
        });
    }

    /**
     * 解析response，执行在子线程
     */
    @SuppressLint("LongLogTag")
    public B onParseResponse(Call call, Response response) {
        //  得到的是一个 AbstractUiCallBack<T> 的Type
        Type type = getClass().getGenericSuperclass();
        // 得到的是T的实际Type
        Type[] arr = ((ParameterizedType) type).getActualTypeArguments();
        clazz = (Class) arr[0];
        try {
            String result = Base64Util.decode(response.body().string());
            LogUtil.e("result-->" + result);
            Gson gson = new Gson();
            final BaseBean baseBean = gson.fromJson(result, BaseBean.class);
            if (baseBean != null) {
                final String msg = baseBean.getMsg();
                if (TextUtils.isEmpty(baseBean.getStatus() )&&baseBean.getStatus().equals("1")) {
                    final String s = String.valueOf(baseBean.getData());
                    if (clazz == BaseBean.class) {
                        mMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!TextUtils.isEmpty(msg)) {
//                                    BaseToast.showText(context, msg, Toast.LENGTH_SHORT);
                                } else if (TextUtils.isEmpty(s) || "null".equals(s)) {
//                                    BaseToast.showText(context, "操作成功", Toast.LENGTH_SHORT).show();
                                } else if (s.length() > 1 && s.length() < 10) {
//                                    BaseToast.showText(context, s, Toast.LENGTH_SHORT);
                                }
                            }
                        });
                        return (B) baseBean;
                    } else if (TextUtils.isEmpty(s) || "null".equals(s)) {
                        mMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
//                                BaseToast.showText(context, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                        onError(call, new Exception("数据错误"));
                        return null;
                    }
                } else {
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (msg != null) {
                                switch (msg) {
                                    case "400":
//                                        BaseToast.showText(context, "400(错误请求)服务器不理解请求的语法。");
                                        break;
                                    case "401":
//                                        BaseToast.showText(context, "401(未授权)请求要求身份验证。对于登录后请求的网页，服务器可能返回此响应。");
                                        break;
                                    case "403":
//                                        BaseToast.showText(context, "403(禁止)服务器拒绝请求。");
                                        break;
                                    case "404":
//                                        BaseToast.showText(context, "404(未找到)服务器找不到请求的网页。");
                                        break;
                                    case "500":
//                                        BaseToast.showText(context, "500(服务器内部错误)服务器遇到错误，无法完成请求。");
                                        break;
                                    case "503":
//                                        BaseToast.showText(context, "503(服务不可用)服务器目前无法使用(由于超载或停机维护)。");
                                        break;
                                    case "504":
//                                        BaseToast.showText(context, "504(网关超时)服务器作为网关或代理，但是没有及时从上游服务器收到请求。");
                                        break;
                                    default:
                                        if (!TextUtils.isEmpty(msg) && !"系统错误".equals(msg)) {
//                                            BaseToast.showText(context, msg, Toast.LENGTH_LONG);
                                        }

                                        break;
                                }
                            }
                        }
                    });
                    onError(call, new Exception(msg));
                    return null;
                }
            }
            final B b = (B) gson.fromJson(result, clazz);
            return b;
        } catch (IOException | JsonSyntaxException e) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
//                    BaseToast.showText(context, "数据错误");
                }
            });
            e.printStackTrace();
            onError(call, new Exception("数据错误"));
            return null;
        }
    }

    /**
     * 访问网络失败后被调用，执行在UI线程
     */
    public abstract void onFailure(Call call, Exception e);

    /**
     * 访问网络成功后被调用，执行在UI线程
     */
    public abstract void onResponse(B response);

//    /**
//     * 访问网络成功后被调用，执行在UI线程
//     */
//    public abstract void onMsg(String msg);

    public static abstract class CallBackDefault extends CallBackUtil<Response> {
        @Override
        public Response onParseResponse(Call call, Response response) {
            return response;
        }
    }

    public static abstract class CallBackString extends CallBackUtil<String> {
        @Override
        public String onParseResponse(Call call, Response response) {
            try {
                return response.body().string();
            } catch (IOException e) {
                new RuntimeException("failure");
                return "";
            }
        }
    }

    public static abstract class CallBackBitmap extends CallBackUtil<Bitmap> {
        private int mTargetWidth;
        private int mTargetHeight;

        public CallBackBitmap() {
        }

        public CallBackBitmap(int targetWidth, int targetHeight) {
            mTargetWidth = targetWidth;
            mTargetHeight = targetHeight;
        }

        public CallBackBitmap(ImageView imageView) {
            int width = imageView.getWidth();
            int height = imageView.getHeight();
            if (width <= 0 || height <= 0) {
                throw new RuntimeException("无法获取ImageView的width或height");
            }
            mTargetWidth = width;
            mTargetHeight = height;
        }

        @Override
        public Bitmap onParseResponse(Call call, Response response) {
            if (mTargetWidth == 0 || mTargetHeight == 0) {
                return BitmapFactory.decodeStream(response.body().byteStream());
            } else {
                return getZoomBitmap(response);
            }
        }

        /**
         * 压缩图片，避免OOM异常
         */
        private Bitmap getZoomBitmap(Response response) {
            byte[] data = null;
            try {
                data = response.body().bytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeByteArray(data, 0, data.length, options);
            int picWidth = options.outWidth;
            int picHeight = options.outHeight;
            int sampleSize = 1;
            int heightRatio = (int) Math.floor((float) picWidth / (float) mTargetWidth);
            int widthRatio = (int) Math.floor((float) picHeight / (float) mTargetHeight);
            if (heightRatio > 1 || widthRatio > 1) {
                sampleSize = Math.max(heightRatio, widthRatio);
            }
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

            if (bitmap == null) {
                throw new RuntimeException("Failed to decode stream.");
            }
            return bitmap;
        }
    }

    /**
     * 下载文件时的回调类
     */
    public static abstract class CallBackFile extends CallBackUtil<File> {

        private final String mDestFileDir;
        private final String mdestFileName;

        /**
         * @param destFileDir:文件目录
         * @param destFileName：文件名
         */
        public CallBackFile(String destFileDir, String destFileName) {
            mDestFileDir = destFileDir;
            mdestFileName = destFileName;
        }

        @Override
        public File onParseResponse(Call call, Response response) {

            InputStream is = null;
            byte[] buf = new byte[1024 * 8];
            int len = 0;
            FileOutputStream fos = null;
            try {
                is = response.body().byteStream();
                final long total = response.body().contentLength();

                long sum = 0;

                File dir = new File(mDestFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, mdestFileName);
                fos = new FileOutputStream(file);
                while ((len = is.read(buf)) != -1) {
                    sum += len;
                    fos.write(buf, 0, len);
                    final long finalSum = sum;
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onProgress(finalSum * 100.0f / total, total);
                        }
                    });
                }
                fos.flush();

                return file;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    response.body().close();
                    if (is != null) is.close();
                } catch (IOException e) {
                }
                try {
                    if (fos != null) fos.close();
                } catch (IOException e) {
                }
            }
            return null;
        }
    }
}
