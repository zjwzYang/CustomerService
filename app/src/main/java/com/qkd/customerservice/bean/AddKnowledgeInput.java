package com.qkd.customerservice.bean;

/**
 * Created on 1/4/21 13:05
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class AddKnowledgeInput {
    private int mediaType;
    private String mediaContent;
    private String mediaPurpose;
    private int serviceId;

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaContent() {
        return mediaContent;
    }

    public void setMediaContent(String mediaContent) {
        this.mediaContent = mediaContent;
    }

    public String getMediaPurpose() {
        return mediaPurpose;
    }

    public void setMediaPurpose(String mediaPurpose) {
        this.mediaPurpose = mediaPurpose;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}