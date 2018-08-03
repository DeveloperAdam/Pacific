package com.techease.pacific.Models;

/**
 * Created by Adamnoor on 6/25/2018.
 */

public class ActiveModel {

    String id;
    String order_no;
    String shipDate;
    String arriveDate;
    String workDays;
    String customerName;
    String materialDes;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;

    public String getMaterialDes() {
        return materialDes;
    }

    public void setMaterialDes(String materialDes) {
        this.materialDes = materialDes;
    }

    public String getNotesShip() {
        return NotesShip;
    }

    public void setNotesShip(String notesShip) {
        NotesShip = notesShip;
    }

    String NotesShip;

    public String getNotesProduction() {
        return notesProduction;
    }

    public void setNotesProduction(String notesProduction) {
        this.notesProduction = notesProduction;
    }

    String notesProduction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getWorkDays() {
        return workDays;
    }

    public void setWorkDays(String workDays) {
        this.workDays = workDays;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


}
