package com.saimawzc.freight.dto.face;

public class EditTaxaDto2 {

    private int errCode;
    private String message;
    boolean success;
    public Facedata data;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Facedata getData() {
        return data;
    }

    public void setData(Facedata data) {
        this.data = data;
    }

    public class Facedata  {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
