package org.scau.internshipsystem.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonResult {
    //响应结果
    private boolean result;
    //响应数据
    private Object data;

    public JsonResult(boolean result){
        this.result = result;
    }

    public static JsonResult success(Object data){
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult failure(Object data){
        JsonResult jsonResult = new JsonResult(false);
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult failure(){
       return new JsonResult(false);
    }

    public static JsonResult success(){
        return new JsonResult(true);
    }
}
