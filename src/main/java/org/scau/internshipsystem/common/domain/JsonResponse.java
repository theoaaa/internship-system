package org.scau.internshipsystem.common.domain;



import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonResponse {
    public static void sendJsonResponse(HttpServletResponse resp, Object data){
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(500);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write(JSONObject.toJSONString(data));
    }
}
