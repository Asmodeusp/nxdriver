package com.saimawzc.freight.dto.taxi;

public class TjxqDato {
    private boolean success;
    private String errCode;
    private String message;
    private TjSWDto data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TjSWDto getData() {
        return data;
    }

    public void setData(TjSWDto data) {
        this.data = data;
    }
}
