package org.endless.ddd.simplified.starter.common.utils.model.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * JsonTools
 * <p>
 * create 2024/11/19 01:39
 * <p>
 * update 2024/11/19 01:39
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class JsonTools {

    public static String maskSensitive(String json) {
        Object jsonObject = JSON.parse(json);
        maskJsonObjectSensitive(jsonObject);
        return JSON.toJSONString(jsonObject);
    }

    private static void maskJsonObjectSensitive(Object node) {
        if (node instanceof JSONObject jsonObject) {
            for (String key : jsonObject.keySet()) {
                if (StringTools.containsSensitive(key)) {
                    jsonObject.put(key, "******");
                } else {
                    maskJsonObjectSensitive(jsonObject.get(key));
                }
            }
        } else if (node instanceof JSONArray jsonArray) {
            for (Object object : jsonArray) {
                maskJsonObjectSensitive(object);
            }
        }
    }
}
