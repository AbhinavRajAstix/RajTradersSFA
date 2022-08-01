package com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface;


import com.google.gson.annotations.SerializedName;


public class PotentialDistributorImageTable {


    @SerializedName("NodeID")
    private String NodeID;

    @SerializedName("NodeType")
    private int NodeType;

    @SerializedName("imagePath")
    private String ImagePath;

    @SerializedName("ImageClicktime")
    private String ImageClickedTime;

    @SerializedName("ImageType")
    private String ImageType;
    @SerializedName("ImageName")
    private String ImageName;

    public String getNodeID() {
        return NodeID;
    }

    public void setNodeID(String nodeID) {
        NodeID = nodeID;
    }

    public int getNodeType() {
        return NodeType;
    }

    public void setNodeType(int nodeType) {
        NodeType = nodeType;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getImageClickedTime() {
        return ImageClickedTime;
    }

    public void setImageClickedTime(String imageClickedTime) {
        ImageClickedTime = imageClickedTime;
    }

    public String getImageType() {
        return ImageType;
    }

    public void setImageType(String imageType) {
        ImageType = imageType;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    @Override
    public String toString() {
        return "PotentialDistributorImageTable{" +
                "NodeID='" + NodeID + '\'' +
                ", NodeType=" + NodeType +
                ", ImagePath='" + ImagePath + '\'' +
                ", ImageClickedTime='" + ImageClickedTime + '\'' +
                ", ImageType='" + ImageType + '\'' +
                ", ImageName='" + ImageName + '\'' +
                '}';
    }
}
