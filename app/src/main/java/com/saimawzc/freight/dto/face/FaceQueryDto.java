package com.saimawzc.freight.dto.face;

import java.io.Serializable;

public class FaceQueryDto implements Serializable {

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

    public class Facedata implements Serializable{
        private String carId;

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        private String clsyr;
        private String czsmgkfjBos;

        public String getCzsmgkfjBos() {
            return czsmgkfjBos;
        }

        public void setCzsmgkfjBos(String czsmgkfjBos) {
            this.czsmgkfjBos = czsmgkfjBos;
        }

        private String cycc;
        private String cycph;
        private String cycsfgk;
        private String cycsjsyr;
        private String cycsyrlxfs;
        private String cycx;
        private String cycxszfj;
        private String cyczpfj;
        private String cyczpfjBos;//承运车辆照片
        private String cypp;
        private String cyzz;
        private String czsmgkfj;
        private String fwfsfzjhm;
        private String fwfsfzjlx;
        private String fwfuuid;
        private String fwfxm;
        private String id;
        private String jszfj;
        private String jszjhm;
        private String khrsfzjhm;
        private String khrxm;
        private String khyh;
        private String nsrmc;
        private String ptzcsj;
        private String qysmgkfj;
        private String skzh;
        private String smrzfj;
        private String ssdq;
        private String sys_car_id;
        private String sys_user_card_sj_id;
        private String xszjhm;
        private String yddh;
        private String smrzfjBos;
        private String qysmgkfjBos;
        private String jszfjBos;
        private String cycxszfjBos;
        private String ssdqStr;

        public String getSsdqStr() {
            return ssdqStr;
        }

        public void setSsdqStr(String ssdqStr) {
            this.ssdqStr = ssdqStr;
        }

        public String getCycxszfjBos() {
            return cycxszfjBos;
        }

        public void setCycxszfjBos(String cycxszfjBos) {
            this.cycxszfjBos = cycxszfjBos;
        }

        public String getJszfjBos() {
            return jszfjBos;
        }

        public void setJszfjBos(String jszfjBos) {
            this.jszfjBos = jszfjBos;
        }

        public String getQysmgkfjBos() {
            return qysmgkfjBos;
        }

        public void setQysmgkfjBos(String qysmgkfjBos) {
            this.qysmgkfjBos = qysmgkfjBos;
        }

        public String getSmrzfjBos() {
            return smrzfjBos;
        }

        public void setSmrzfjBos(String smrzfjBos) {
            this.smrzfjBos = smrzfjBos;
        }

        public String getCyczpfjBos() {
            return cyczpfjBos;
        }

        public void setCyczpfjBos(String cyczpfjBos) {
            this.cyczpfjBos = cyczpfjBos;
        }

        public String getClsyr() {
            return clsyr;
        }

        public void setClsyr(String clsyr) {
            this.clsyr = clsyr;
        }

        public String getCycc() {
            return cycc;
        }

        public void setCycc(String cycc) {
            this.cycc = cycc;
        }

        public String getCycph() {
            return cycph;
        }

        public void setCycph(String cycph) {
            this.cycph = cycph;
        }

        public String getCycsfgk() {
            return cycsfgk;
        }

        public void setCycsfgk(String cycsfgk) {
            this.cycsfgk = cycsfgk;
        }

        public String getCycsjsyr() {
            return cycsjsyr;
        }

        public void setCycsjsyr(String cycsjsyr) {
            this.cycsjsyr = cycsjsyr;
        }

        public String getCycsyrlxfs() {
            return cycsyrlxfs;
        }

        public void setCycsyrlxfs(String cycsyrlxfs) {
            this.cycsyrlxfs = cycsyrlxfs;
        }

        public String getCycx() {
            return cycx;
        }

        public void setCycx(String cycx) {
            this.cycx = cycx;
        }

        public String getCycxszfj() {
            return cycxszfj;
        }

        public void setCycxszfj(String cycxszfj) {
            this.cycxszfj = cycxszfj;
        }

        public String getCyczpfj() {
            return cyczpfj;
        }

        public void setCyczpfj(String cyczpfj) {
            this.cyczpfj = cyczpfj;
        }

        public String getCypp() {
            return cypp;
        }

        public void setCypp(String cypp) {
            this.cypp = cypp;
        }

        public String getCyzz() {
            return cyzz;
        }

        public void setCyzz(String cyzz) {
            this.cyzz = cyzz;
        }

        public String getCzsmgkfj() {
            return czsmgkfj;
        }

        public void setCzsmgkfj(String czsmgkfj) {
            this.czsmgkfj = czsmgkfj;
        }

        public String getFwfsfzjhm() {
            return fwfsfzjhm;
        }

        public void setFwfsfzjhm(String fwfsfzjhm) {
            this.fwfsfzjhm = fwfsfzjhm;
        }

        public String getFwfsfzjlx() {
            return fwfsfzjlx;
        }

        public void setFwfsfzjlx(String fwfsfzjlx) {
            this.fwfsfzjlx = fwfsfzjlx;
        }

        public String getFwfuuid() {
            return fwfuuid;
        }

        public void setFwfuuid(String fwfuuid) {
            this.fwfuuid = fwfuuid;
        }

        public String getFwfxm() {
            return fwfxm;
        }

        public void setFwfxm(String fwfxm) {
            this.fwfxm = fwfxm;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJszfj() {
            return jszfj;
        }

        public void setJszfj(String jszfj) {
            this.jszfj = jszfj;
        }

        public String getJszjhm() {
            return jszjhm;
        }

        public void setJszjhm(String jszjhm) {
            this.jszjhm = jszjhm;
        }

        public String getKhrsfzjhm() {
            return khrsfzjhm;
        }

        public void setKhrsfzjhm(String khrsfzjhm) {
            this.khrsfzjhm = khrsfzjhm;
        }

        public String getKhrxm() {
            return khrxm;
        }

        public void setKhrxm(String khrxm) {
            this.khrxm = khrxm;
        }

        public String getKhyh() {
            return khyh;
        }

        public void setKhyh(String khyh) {
            this.khyh = khyh;
        }

        public String getNsrmc() {
            return nsrmc;
        }

        public void setNsrmc(String nsrmc) {
            this.nsrmc = nsrmc;
        }

        public String getPtzcsj() {
            return ptzcsj;
        }

        public void setPtzcsj(String ptzcsj) {
            this.ptzcsj = ptzcsj;
        }

        public String getQysmgkfj() {
            return qysmgkfj;
        }

        public void setQysmgkfj(String qysmgkfj) {
            this.qysmgkfj = qysmgkfj;
        }

        public String getSkzh() {
            return skzh;
        }

        public void setSkzh(String skzh) {
            this.skzh = skzh;
        }

        public String getSmrzfj() {
            return smrzfj;
        }

        public void setSmrzfj(String smrzfj) {
            this.smrzfj = smrzfj;
        }

        public String getSsdq() {
            return ssdq;
        }

        public void setSsdq(String ssdq) {
            this.ssdq = ssdq;
        }

        public String getSys_car_id() {
            return sys_car_id;
        }

        public void setSys_car_id(String sys_car_id) {
            this.sys_car_id = sys_car_id;
        }

        public String getSys_user_card_sj_id() {
            return sys_user_card_sj_id;
        }

        public void setSys_user_card_sj_id(String sys_user_card_sj_id) {
            this.sys_user_card_sj_id = sys_user_card_sj_id;
        }

        public String getXszjhm() {
            return xszjhm;
        }

        public void setXszjhm(String xszjhm) {
            this.xszjhm = xszjhm;
        }

        public String getYddh() {
            return yddh;
        }

        public void setYddh(String yddh) {
            this.yddh = yddh;
        }
    }

}
