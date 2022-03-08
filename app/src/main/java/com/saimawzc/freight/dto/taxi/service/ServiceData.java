package com.saimawzc.freight.dto.taxi.service;

import java.util.List;

public class ServiceData {
    private int errCode;
    private boolean success;
    private String message;
    private List<MyServiceListDto>data;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MyServiceListDto> getData() {
        return data;
    }

    public void setData(List<MyServiceListDto> data) {
        this.data = data;
    }
}
