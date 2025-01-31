package com.smhu.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smhu.account.Shipper;
import com.smhu.market.Market;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;

public class Order implements Cloneable, Comparable<Order> {

    private String id;
    private String cust;
    private String addressDelivery;
    private String note;
    private Market market;
    private Shipper shipper;
    private String lat;
    private String lng;
    private Date createDate;
    private Time createTime;
    private Time lastUpdate;
    private int status;
    private String author;
    private String reasonCancel;
    private double costShopping;
    private double costDelivery;
    private double totalCost;
    private double refundCost;
    private Date dateDelivery;
    private Time timeDelivery;
    private List<OrderDetail> details;

    @JsonIgnore
    private int commissionShipping;

    @JsonIgnore
    private int commissionShopping;

    @JsonIgnore
    private List<Evidence> evidences;

    public Order() {
    }

    public Order(String id, String cust, String addressDelivery, String note, Market market,
            Shipper shipper, String lat, String lng,
            Date createDate, Time createTime, Time lastUpdate,
            int status, String author, String reasonCancel, double costShopping, double costDelivery, double totalCost, double refundCost,
            Date dateDelivery, Time timeDelivery, int commissionShipping, int commissionShopping, List<OrderDetail> details, List<Evidence> evidences) {
        this.id = id;
        this.cust = cust;
        this.addressDelivery = addressDelivery;
        this.note = note;
        this.market = market;
        this.shipper = shipper;
        this.lat = lat;
        this.lng = lng;
        this.createDate = createDate;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
        this.status = status;
        this.author = author;
        this.reasonCancel = reasonCancel;
        this.costShopping = costShopping;
        this.costDelivery = costDelivery;
        this.totalCost = totalCost;
        this.refundCost = refundCost;
        this.dateDelivery = dateDelivery;
        this.timeDelivery = timeDelivery;
        this.commissionShipping = commissionShipping;
        this.commissionShopping = commissionShopping;
        this.details = details;
        this.evidences = evidences;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCust() {
        return cust;
    }

    public void setCust(String cust) {
        this.cust = cust;
    }

    public String getAddressDelivery() {
        return addressDelivery;
    }

    public void setAddressDelivery(String addressDelivery) {
        this.addressDelivery = addressDelivery;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Shipper getShipper() {
        return shipper;
    }

    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Time getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Time createTime) {
        this.createTime = createTime;
    }

    public Time getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Time lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReasonCancel() {
        return reasonCancel;
    }

    public void setReasonCancel(String reasonCancel) {
        this.reasonCancel = reasonCancel;
    }

    public double getCostShopping() {
        return costShopping;
    }

    public void setCostShopping(double costShopping) {
        this.costShopping = costShopping;
    }

    public double getCostDelivery() {
        return costDelivery;
    }

    public void setCostDelivery(double costDelivery) {
        this.costDelivery = costDelivery;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getRefundCost() {
        return refundCost;
    }

    public void setRefundCost(double refundCost) {
        this.refundCost = refundCost;
    }

    public Date getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public Time getTimeDelivery() {
        return timeDelivery;
    }

    public void setTimeDelivery(Time timeDelivery) {
        this.timeDelivery = timeDelivery;
    }

    public int getCommissionShipping() {
        return commissionShipping;
    }

    public void setCommissionShipping(int commissionShipping) {
        this.commissionShipping = commissionShipping;
    }

    public int getCommissionShopping() {
        return commissionShopping;
    }

    public void setCommissionShopping(int commissionShopping) {
        this.commissionShopping = commissionShopping;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences = evidences;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", cust=" + cust + ", addressDelivery=" + addressDelivery + ", note=" + note + ", market=" + market + ", shipper=" + shipper + ", lat=" + lat + ", lng=" + lng + ", createDate=" + createDate + ", createTime=" + createTime + ", lastUpdate=" + lastUpdate + ", status=" + status + ", author=" + author + ", reasonCancel=" + reasonCancel + ", costShopping=" + costShopping + ", costDelivery=" + costDelivery + ", totalCost=" + totalCost + ", refundCost=" + refundCost + ", dateDelivery=" + dateDelivery + ", timeDelivery=" + timeDelivery + ", details=" + details + ", commissionShipping=" + commissionShipping + ", commissionShopping=" + commissionShopping + ", evidences=" + evidences + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(Order o) {
        return this.timeDelivery.compareTo(o.timeDelivery) != 0
                ? (this.timeDelivery.compareTo(o.timeDelivery)) : (this.createTime.compareTo(o.createTime));
    }
}
