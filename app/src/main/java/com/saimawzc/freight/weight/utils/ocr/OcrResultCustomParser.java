package com.saimawzc.freight.weight.utils.ocr;

import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.OcrResponseResult;
import com.baidu.ocr.sdk.utils.Parser;

import org.json.JSONException;
import org.json.JSONObject;

public class OcrResultCustomParser implements Parser<OcrResponseResult> {

    public OcrResultCustomParser() {
    }

    public OcrResponseResult parse(String json) throws OCRError {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has("error_code")&&jsonObject.optInt("error_code")!=0) {
                OCRError error = new OCRError(jsonObject.optInt("error_code"), jsonObject.optString("error_msg"));
                error.setLogId(jsonObject.optLong("log_id"));
                throw error;
            } else {
                OcrResponseResult result = new OcrResponseResult();
                result.setLogId(jsonObject.optLong("log_id"));
                result.setJsonRes(json);
                return result;
            }
        } catch (JSONException var4) {
            throw new OCRError(283505, "Server illegal response " + json, var4);
        }
    }
}
