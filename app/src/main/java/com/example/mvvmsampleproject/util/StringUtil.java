package com.example.mvvmsampleproject.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ktshow.cs.ndkaes.BuildConfig;

import java.net.URLEncoder;
import java.text.DecimalFormat;

public class StringUtil {

    private static final String TAG = "StringUtil";

    public static String strComma(String str) {
        if (null == str || str.length() == 0) return "";

        try {
            long value = Long.parseLong(str);
            DecimalFormat format = new DecimalFormat("###,###");
            return format.format(value);
        }
        // [DR-2020-미등록] 마이케이티앱 sparrow 소스진단 취약점 조치
        catch (RuntimeException e) {
            return str;
        } catch (Exception e) {
            return str;
        }
    }

    public static String strDate(String str) {
        if (null == str || str.length() == 0) return "";
        long value = Long.parseLong(str);
        DecimalFormat format = new DecimalFormat("####.##.##");
        return format.format(value);
    }

    public static int strToHex(String str) {
        String color = "FF" + str;
        long icolor = Long.parseLong(color, 16);

        return (int) icolor;
    }


    public static String getUTF8Value(String content) {

        String value = "";

        if (TextUtils.isEmpty(content)) {
            return value;
        }

        try {
            value = URLEncoder.encode(content, "UTF-8");
        }
        // [DR-2020-미등록] 마이케이티앱 sparrow 소스진단 취약점 조치
        catch (RuntimeException e) {
            LogUtil.e(TAG, e.getLocalizedMessage());
        } catch (Exception e) {
            LogUtil.e(TAG, e.getLocalizedMessage());
        }

        return value;
    }

    public static String getAddParam(String url, String param) {
        if (url.contains("?")) {
            // 파라미터 추가
            url += "&" + param;
        } else {
            // 파라미터 추가
            url += "?" + param;
        }
        return url;
    }

    public static String parseKtWebTitle(String title) {
        String strTitle = "";

        if (!TextUtils.isEmpty(title)) {
            String[] parts = title.split("\\|");

            if (parts.length > 0) {
                strTitle = parts[0].trim();
            }
        }

        // [DR-2019-27072] 타이틀 없는경우 '마이케이티' 노출 수정
        if (TextUtils.isEmpty(strTitle) || strTitle.matches("^[http://|https://].*$")) {
            strTitle = "마이 케이티";
        }

        return strTitle;
    }

    public static String parseSplitPipe(String src, int index) {
        if (TextUtils.isEmpty(src))
            return "";

        String[] messages = src.split("\\|");

        if (messages.length < 1)
            return "";


        if (messages.length == 1) {
            return messages[0];
        } else {
            if (index >= messages.length) {
                return messages[0];
            } else {
                return messages[index];
            }
        }
    }

    /**
     * 바코드 4글자 간격으로 - 넣기
     */
    public static String makeBarcodeNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return "";
        }

        int unit = 4;
        String returnValue = "";

        if (number.length() <= unit) {
            return number;
        }

        try {
            returnValue = number.substring(0, unit);
            for (int i = unit; i < number.length(); i += unit) {
                if (i + unit > number.length()) {
                    returnValue += "-" + number.substring(i);
                } else {
                    returnValue += "-" + number.substring(i, i + unit);
                }
            }
            // [DR-2020-미등록] 마이케이티앱 sparrow 소스진단 취약점 조치
        } catch (Exception e) {
            returnValue = number;
        }
        return returnValue;
    }

    // [DR-2019-27367] Android 홈탭에서 뒤로가기 선택시 앱종료 개발
    // #(hash), ?(param) 를 제거한 url 반환
    public static String convertUrlParam(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }

        String convertUrl = url;
        String reg1 = "^[http://|https://].*[#|?].+";
        String reg2 = "^[http://|https://].*[#|?]$";

        if (convertUrl.matches(reg1) || convertUrl.matches(reg2)) {
            if (convertUrl.indexOf("#") > -1) {
                convertUrl = convertUrl.substring(0, convertUrl.indexOf("#"));
            }
            if (convertUrl.indexOf("?") > -1) {
                convertUrl = convertUrl.substring(0, convertUrl.indexOf("?"));
            }
        }

        return convertUrl;
    }

    // [DR-2020-15828] 스플래시 이미지 변경 가능 개발(CMS)
    // url에서 파일 확장자 반환
    public static String getFileExtFromUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }

        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        String[] splitUrl = url.split("\\.");
        String ext = "";
        if (splitUrl.length > 1) {
            ext = splitUrl[1];
        }

        return ext;
    }

    // [DR-2020-15828] 스플래시 이미지 변경 가능 개발(CMS)
    // url에서 파일명 반환
    public static String getFileNameFromUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }

        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }

        String[] splitUrl = url.split("/");

        String filename = "";
        if (splitUrl.length > 0) {
            filename = splitUrl[splitUrl.length - 1];
        }

        return filename;
    }

    // [DR-2020-15828] 스플래시 이미지 변경 가능 개발(CMS)
    // dto -> json string
    public static <T> String dtoToJsonString(T dto) {
        if (dto == null) {
            return "";
        }

        try {
            GsonBuilder builder = new GsonBuilder();
            builder.excludeFieldsWithoutExposeAnnotation();
            builder.serializeNulls();

            Gson gson = builder.create();
            return gson.toJson(dto);
            // [DR-2020-미등록] 마이케이티앱 sparrow 소스진단 취약점 조치
        } catch (RuntimeException e) {
            LogUtil.e( "RuntimeException=" + e.getMessage());
            return "";
        } catch (Exception e) {
            LogUtil.e( "Exception=" + e.getMessage());
            return "";
        }
    }

    // [DR-2020-15828] 스플래시 이미지 변경 가능 개발(CMS)
    // json string -> dto
    public static <T> T parseJson(String json, Class<T> dto) {
        if (TextUtils.isEmpty(json) || "{}".equals(json)) {
            return null;
        }
        if (BuildConfig.DEBUG) {
//            DataUtils.showLog(json);
        }
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.excludeFieldsWithoutExposeAnnotation();
            builder.serializeNulls();

            Gson gson = builder.create();
            return gson.fromJson(json, dto);
            // [DR-2020-미등록] 마이케이티앱 sparrow 소스진단 취약점 조치
        } catch (RuntimeException e) {
            LogUtil.e(TAG, "err=" + e.getMessage());
            return null;
        } catch (Exception e) {
            LogUtil.e(TAG, "err=" + e.getMessage());
            return null;
        }
    }

    public static String parseErrorMsg(String msg, boolean isShort) {
        if (TextUtils.isEmpty(msg))
            return "";
        String[] message = msg.split("\\|");
        if (message.length < 1)
            return "";
        if (message.length == 1) {
            return message[0];
        } else {
            if (isShort) {
                return message[0];
            } else {
                return message[1];
            }
        }
    }

}