package com.techease.pacific.Models;

/**
 * Created by Adamnoor on 6/25/2018.
 */

public class CompletedOrdersModel {
    String id;
    String orderNo;
    String arrivDate;
    String shipDate;
    String customerName;
    String img;

    public String getNotesProduction() {
        return notesProduction;
    }

    public void setNotesProduction(String notesProduction) {
        this.notesProduction = notesProduction;
    }

    public String getNotesShippping() {
        return notesShippping;
    }

    public void setNotesShippping(String notesShippping) {
        this.notesShippping = notesShippping;
    }

    public String getMeterialDes() {
        return meterialDes;
    }

    public void setMeterialDes(String meterialDes) {
        this.meterialDes = meterialDes;
    }

    String notesProduction;
    String notesShippping;
    String meterialDes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getArrivDate() {
        return arrivDate;
    }

    public void setArrivDate(String arrivDate) {
        this.arrivDate = arrivDate;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
