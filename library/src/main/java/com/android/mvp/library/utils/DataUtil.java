package com.android.mvp.library.utils;

import android.content.Context;
import android.graphics.Paint;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据
 */
public class DataUtil {
    private static Context context = UIUtils.getContext();

    public static String doubleTrans(double d) {
        if (Math.round(d) - d == 0) {
            return String.valueOf((long) d);
        }
        return String.valueOf(d);
    }

    //是否包含中文
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 集合非空
     */
    public static boolean isNull(List list) {
        return list == null || list.isEmpty();
    }

    /**
     * 集合非空
     */
    public static boolean isNull(Map map) {
        return map == null || map.isEmpty();
    }

    public static int getIntByString(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
//            BaseToast.showText(context, "数据中有字符串无法转换数字", Toast.LENGTH_SHORT).show();
        }
        return -1;
    }

    public static Float getFloatByString(String s) {
        try {
            return Float.parseFloat(s);
        } catch (Exception e) {
            e.printStackTrace();
//            BaseToast.showText(context, "数据中有字符串无法转换数字", Toast.LENGTH_SHORT).show();
        }
        return -1f;
    }

    public static String getStringByList(List<String> strings) {
        if (DataUtil.isNull(strings)) strings = new ArrayList<>();
        String str = "";
        for (int i = 0; i < strings.size(); i++) {
            str += strings.get(i) + ",";
        }
        return str.length() < 1 ? "" : str.substring(0, str.length() - 1);
    }

    public static List<String> getListByString(String str) {
        String[] strings = str.split(",");
        return new ArrayList(Arrays.asList(strings));
    }

    public static <K, V> List<K> getListByMapKey(Map<K, V> map) {
        if (DataUtil.isNull(map)) {
            map = new HashMap<>();
        }
        return new ArrayList<>(map.keySet());
    }

    public static <K, V> List<V> getListByMapValues(Map<K, V> map) {
        return new ArrayList<>(map.values());
    }


    public static int[] getTimeByLong(Long l) {
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue() / 3000;
        if (second >= 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }
        if (minute >= 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        if (hour >= 24) {
            day = hour / 24;
            hour = hour % 24;
        }
        return new int[]{day, hour, minute, second};
    }

    /**
     * text自动排版换行
     *
     * @param str
     * @return
     */
    public static String textLineWrap(String str) {
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
//toabc->半角转全角后
        String toAbc = new String(c);
        toAbc = toAbc.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(toAbc);
//m.replaceAll("").trim()->去除特殊字符，中文符转英文符
        return m.replaceAll("").trim();
    }

    public static void autoSplitText(final TextView tv) {
        ViewTreeObserver vto2 = tv.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final String rawText = tv.getText().toString(); //原始文本
                final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息

                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                final float[] tvWidth = new float[]{tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight()};//控件可用宽度

                //将原始文本按行拆分
                String[] rawTextLines = rawText.replaceAll("\r", "").split("\n");
                StringBuilder sbNewText = new StringBuilder();
                for (String rawTextLine : rawTextLines) {
                    if (tvPaint.measureText(rawTextLine) <= tvWidth[0]) {
                        //如果整行宽度在控件可用宽度之内，就不处理了
                        sbNewText.append(rawTextLine);
                    } else {
                        //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                        float lineWidth = 0;
                        for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                            char ch = rawTextLine.charAt(cnt);
                            lineWidth += tvPaint.measureText(String.valueOf(ch));
                            if (lineWidth <= tvWidth[0]) {
                                sbNewText.append(ch);
                            } else {
                                sbNewText.append("\n");
                                lineWidth = 0;
                                --cnt;
                            }
                        }
                    }
                    sbNewText.append("\n");
                }
                //把结尾多余的\n去掉
                if (!rawText.endsWith("\n")) {
                    sbNewText.deleteCharAt(sbNewText.length() - 1);
                }
                tv.setText(sbNewText.toString());
            }
        });
    }

    //判断文件是否存在
    public static boolean fileIsExists(File f) {
        try {
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 距离今天多久
     *
     * @param timestr
     * @return
     */
    private static final long ONE_MINUTE = 60;

    private static final long ONE_HOUR = 3600;

    private static final long ONE_DAY = 86400;

    private static final long ONE_MONTH = 2592000;

    private static final long ONE_YEAR = 31104000;


    public static String fromToday(String timestr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        if (timestr == null || timestr.equals("") || timestr.equals("null")) {
            return "未知时间";
        }
        try {
            date = df.parse(timestr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "未知时间";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long time = date.getTime() / 1000;
        long now = new Date().getTime() / 1000;
        long ago = now - time;
        if (ago <= ONE_MINUTE) {
            return ago + "秒前";
        } else if (ago <= ONE_HOUR) {
            return ago / ONE_MINUTE + "分钟前";
        } else if (ago <= ONE_DAY) {
            return ago / ONE_HOUR + "小时" + (ago % ONE_HOUR / ONE_MINUTE) + "分钟前";
        } else if (ago <= ONE_DAY * 2) {
            return "昨天" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE) + "分";
        } else if (ago <= ONE_DAY * 3) {
            return "前天" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE) + "分";
        } else if (ago <= ONE_MONTH) {
            long day = ago / ONE_DAY;
            if (day < 7)
                return day + "天前";
            else if (day >= 7 && day < 14)
                return "1周前";
            else if (day >= 14 && day < 21)
                return "2周前";
            else if (day >= 21 && day < 28)
                return "3周前";
            else
                return "4周前";
        } else if (ago <= ONE_YEAR) {
            long month = ago / ONE_MONTH;
            long day = ago % ONE_MONTH / ONE_DAY;
            return month + "个月" + day + "天前";
        } else {
            long year = ago / ONE_YEAR;
            int month = calendar.get(Calendar.MONTH) + 1;// JANUARY which is 0 so month+1
            return year + "年前" + month + "月" + calendar.get(Calendar.DATE)
                    + "日";
        }
    }

    public static long getBetweenDate(String s1, String s2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            if (s1.contains(" ")) {
                d1 = format.parse(s1);
                d2 = format.parse(s2);
            }else {
                d1 = format1.parse(s1);
                d2 = format1.parse(s2);
            }
            return d2.getTime() - d1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getDate(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = format.parse(s);
            return format.format(new Date(d.getTime() + 1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDateFormat(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date d = format.parse(s);
            return format1.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
