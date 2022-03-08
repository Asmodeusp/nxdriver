package com.saimawzc.freight.dto.login;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2020/8/5.
 * 地区
 */

public class AreaDto implements IPickerViewData {

    private String id;
    private String name;
    private String mergerName;
    private int pid;
    private List<city>children;

    @Override
    public String getPickerViewText() {
        return name;
    }

    public class city implements IPickerViewData {
        private String id;
        private String name;
        private String mergerName;
        private int pid;
        private List<country>children;
        public List<country> getChildren() {
            return children;
        }
        public void setChildren(List<country> children) {
            this.children = children;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getMergerName() {
            return mergerName;
        }
        public void setMergerName(String mergerName) {
            this.mergerName = mergerName;
        }
        public int getPid() {
            return pid;
        }
        public void setPid(int pid) {
            this.pid = pid;
        }

        @Override
        public String getPickerViewText() {
            return name;
        }
    }
    public class  country implements IPickerViewData {
        private String id;
        private String name;
        private String mergerName;
        private int pid;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getMergerName() {
            return mergerName;
        }
        public void setMergerName(String mergerName) {
            this.mergerName = mergerName;
        }
        public int getPid() {
            return pid;
        }
        public void setPid(int pid) {
            this.pid = pid;
        }

        @Override
        public String getPickerViewText() {
            return name;
        }
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<city> getChildren() {
        return children;
    }

    public void setChildren(List<city> children) {
        this.children = children;
    }
}
