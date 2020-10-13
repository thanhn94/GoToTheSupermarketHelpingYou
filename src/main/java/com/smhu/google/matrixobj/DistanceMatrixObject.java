package com.smhu.google.matrixobj;

import com.google.gson.annotations.SerializedName;

public class DistanceMatrixObject {

    @SerializedName("destination_addresses")
    private String[] destinationAddr;

    @SerializedName("origin_addresses")
    private String[] originAddr;

    @SerializedName("rows")
    private RowObject[] rows;

    @SerializedName("status")
    private String status;

    public String[] getDestinationAddr() {
        return destinationAddr;
    }

    public String[] getOriginAddr() {
        return originAddr;
    }

    public RowObject[] getRows() {
        return rows;
    }

    public String getStatus() {
        return status;
    }

}