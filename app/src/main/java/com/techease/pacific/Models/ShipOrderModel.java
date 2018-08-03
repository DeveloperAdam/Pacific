package com.techease.pacific.Models;

/**
 * Created by Adamnoor on 7/9/2018.
 */

public class ShipOrderModel {

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

    public String getArrived_date() {
        return arrived_date;
    }

    public void setArrived_date(String arrived_date) {
        this.arrived_date = arrived_date;
    }

    public String getShipment_date() {
        return shipment_date;
    }

    public void setShipment_date(String shipment_date) {
        this.shipment_date = shipment_date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String id;
    String order_no;
    String arrived_date;
    String shipment_date;
    String customer;
    String image;

    public String getDayLeft() {
        return dayLeft;
    }

    public void setDayLeft(String dayLeft) {
        this.dayLeft = dayLeft;
    }

    String dayLeft;


    public String getNotesProduction() {
        return notesProduction;
    }

    public void setNotesProduction(String notesProduction) {
        this.notesProduction = notesProduction;
    }

    String notesProduction;

    public String getMetrialDescription() {
        return metrialDescription;
    }

    public void setMetrialDescription(String metrialDescription) {
        this.metrialDescription = metrialDescription;
    }

    public String getNotesShipping() {
        return notesShipping;
    }

    public void setNotesShipping(String notesShipping) {
        this.notesShipping = notesShipping;
    }

    String metrialDescription;
    String notesShipping;
}
