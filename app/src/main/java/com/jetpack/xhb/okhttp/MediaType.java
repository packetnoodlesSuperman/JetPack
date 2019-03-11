package com.jetpack.xhb.okhttp;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Matcher正则了解
 * 了解 MediaType
 * MediaType对象包含了三种信息：type 、subtype以及charset
 *      比如 "text/x-markdown; charset=utf-8" ，type值是text，表示是文本这一大类；
 *           /后面的x-markdown是subtype charset=utf-8 则表示采用UTF-8编码
 */
public class MediaType {

    private static final String TOKEN = "([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)";
    private static final String QUOTED = "\"([^\"]*)\"";

    private static final Pattern TYPE_SUBTYPE = Pattern.compile(TOKEN + "/" + TOKEN);
    private static final Pattern PARAMETER = Pattern.compile(
            ";\\s*(?:" + TOKEN + "=(?:" + TOKEN + "|" + QUOTED + "))?");

    private final String mediaType;
    private final String type;
    private final String subtype;
    private final String charset;

    private MediaType(String mediaType, String type, String subtype, String charset) {
        this.mediaType = mediaType;
        this.type = type;
        this.subtype = subtype;
        this.charset = charset;
    }

    /**
     * 解析出type 、subtype以及charset
     */
    public static MediaType parse(String string) {
        //string = "text/x-markdown; charset=utf-8"
        Matcher typeSubtype = TYPE_SUBTYPE.matcher(string);
        if (!typeSubtype.lookingAt()) {
            return null;
        }

        String type = typeSubtype.group(1).toLowerCase(Locale.US);
        String subtype = typeSubtype.group(2).toLowerCase(Locale.US);

        String charset = null;
        Matcher parameter = PARAMETER.matcher(string);

        for (int s = typeSubtype.end(); s < string.length(); s = parameter.end()) {
            parameter.region(s, string.length());
            if (!parameter.lookingAt()) {
                return null;
            }


        }

        return new MediaType(string, type, subtype, charset);
    }

    public Charset charset() {
        return charset != null ? Charset.forName(charset) : null;
    }

}
