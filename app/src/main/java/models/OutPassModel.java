/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package models;

import com.google.gson.annotations.SerializedName;

import constants.OutPassAttributes;

/**
 * Created by jayan on 16-11-2016.
 */

public class OutPassModel {

    @SerializedName(OutPassAttributes.ID)
    private String id;

    @SerializedName(OutPassAttributes.UID)
    private String uid;

    @SerializedName(OutPassAttributes.NAME)
    private String name;

    @SerializedName(OutPassAttributes.PHONE_NUMBER_VISITING)
    private String phoneNumberVisiting;

    @SerializedName(OutPassAttributes.BRANCH)
    private String branch;

    @SerializedName(OutPassAttributes.YEAR)
    private String year;

    @SerializedName(OutPassAttributes.ROOM_NUMBER)
    private String roomNumber;

    @SerializedName(OutPassAttributes.TIME_STAMP_LEAVE)
    private String timeLeave;

    @SerializedName(OutPassAttributes.TIME_STAMP_RETURN)
    private String timeReturn;

    @SerializedName(OutPassAttributes.PHONE_NUMBER)
    private String phoneNumber;

    @SerializedName(OutPassAttributes.REASON)
    private String reasonVisit;

    @SerializedName(OutPassAttributes.STUDENT_REMARK)
    private String studentRemark;

    @SerializedName(OutPassAttributes.ADDRESS)
    private String visitingAddress;

    @SerializedName(OutPassAttributes.WARDEN_SIGNED)
    private Boolean wardenSigned;

    @SerializedName(OutPassAttributes.WARDEN_REMARK)
    private String wardenRemark;

    @SerializedName(OutPassAttributes.WARDEN_TALKED_TO_PARENT)
    private Boolean wardenTalkedToParent;

    @SerializedName(OutPassAttributes.HOD_SIGNED)
    private Boolean hodSigned;

    @SerializedName(OutPassAttributes.HOD_REMARK)
    private String hodRemark;

    @SerializedName(OutPassAttributes.HOD_TALKED_TO_PARENT)
    private Boolean hodTalkedToParent;

    @SerializedName(OutPassAttributes.DIRECTOR_SIGNED)
    private Boolean directorSigned;

    @SerializedName(OutPassAttributes.DIRECTOR_REMARK)
    private String directorRemark;

    @SerializedName(OutPassAttributes.DIRECTOR_PRIORITY_SIGN)
    private Boolean directorPrioritySign;

    @SerializedName(OutPassAttributes.OUT_PASS_TYPE)
    private String outPassType;

    public OutPassModel() {
    }

    public String getVisitingAddress() {
        return visitingAddress;
    }

    public void setVisitingAddress(String visitingAddress) {
        this.visitingAddress = visitingAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReasonVisit() {
        return reasonVisit;
    }

    public void setReasonVisit(String reasonVisit) {
        this.reasonVisit = reasonVisit;
    }

    public Boolean getWardenSigned() {
        return wardenSigned;
    }

    public void setWardenSigned(Boolean wardenSigned) {
        this.wardenSigned = wardenSigned;
    }

    public Boolean getHodSigned() {
        return hodSigned;
    }

    public void setHodSigned(Boolean hodSigned) {
        this.hodSigned = hodSigned;
    }

    public Boolean getDirectorSigned() {
        return directorSigned;
    }

    public void setDirectorSigned(Boolean directorSigned) {
        this.directorSigned = directorSigned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumberVisiting() {
        return phoneNumberVisiting;
    }

    public void setPhoneNumberVisiting(String phoneNumberVisiting) {
        this.phoneNumberVisiting = phoneNumberVisiting;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getTimeLeave() {
        return timeLeave;
    }

    public void setTimeLeave(String timeLeave) {
        this.timeLeave = timeLeave;
    }

    public String getTimeReturn() {
        return timeReturn;
    }

    public void setTimeReturn(String timeReturn) {
        this.timeReturn = timeReturn;
    }

    public String getOutPassType() {
        return outPassType;
    }

    public void setOutPassType(String outPassType) {
        this.outPassType = outPassType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWardenRemark() {
        return wardenRemark;
    }

    public void setWardenRemark(String wardenRemark) {
        this.wardenRemark = wardenRemark;
    }

    public Boolean getWardenTalkedToParent() {
        return wardenTalkedToParent;
    }

    public void setWardenTalkedToParent(Boolean wardenTalkedToParent) {
        this.wardenTalkedToParent = wardenTalkedToParent;
    }

    public String getHodRemark() {
        return hodRemark;
    }

    public void setHodRemark(String hodRemark) {
        this.hodRemark = hodRemark;
    }

    public Boolean getHodTalkedToParent() {
        return hodTalkedToParent;
    }

    public void setHodTalkedToParent(Boolean hodTalkedToParent) {
        this.hodTalkedToParent = hodTalkedToParent;
    }

    public String getDirectorRemark() {
        return directorRemark;
    }

    public void setDirectorRemark(String directorRemark) {
        this.directorRemark = directorRemark;
    }

    public Boolean getDirectorPrioritySign() {
        return directorPrioritySign;
    }

    public void setDirectorPrioritySign(Boolean directorPrioritySign) {
        this.directorPrioritySign = directorPrioritySign;
    }

    public String getStudentRemark() {
        return studentRemark;
    }

    public void setStudentRemark(String studentRemark) {
        this.studentRemark = studentRemark;
    }
}
