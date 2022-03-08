package com.saimawzc.freight.dto.my.carleader;

import java.io.Serializable;
import java.util.List;

public class CarLeaderListDto {


    private boolean isLastPage;
    private List<leaderDto>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<leaderDto> getList() {
        return list;
    }

    public void setList(List<leaderDto> list) {
        this.list = list;
    }

    public class leaderDto implements Serializable {
        private String id;
        private int num;
        private String userName;
        private String picture;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }


}
