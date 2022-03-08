package com.saimawzc.freight.dto.my.queue;

import java.util.List;

public class CarQueueDto {
    private boolean isLastPage;

    private List<data>list;


    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<data> getList() {
        return list;
    }

    public void setList(List<data> list) {
        this.list = list;
    }

   public class  data{
       private String carNo;
       private String clientName;
       private String id;
       private String phone;
       private String picture;
       private String userName;

       public String getUserName() {
           return userName;
       }

       public void setUserName(String userName) {
           this.userName = userName;
       }

       public String getCarNo() {
           return carNo;
       }

       public void setCarNo(String carNo) {
           this.carNo = carNo;
       }

       public String getClientName() {
           return clientName;
       }

       public void setClientName(String clientName) {
           this.clientName = clientName;
       }

       public String getId() {
           return id;
       }

       public void setId(String id) {
           this.id = id;
       }

       public String getPhone() {
           return phone;
       }

       public void setPhone(String phone) {
           this.phone = phone;
       }

       public String getPicture() {
           return picture;
       }

       public void setPicture(String picture) {
           this.picture = picture;
       }
   }


}
