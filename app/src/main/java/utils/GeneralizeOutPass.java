/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package utils;

import db.FacultySignedPasses;
import db.FacultyToSignPasses;
import db.ReportLeavingToday;
import db.ReportLeftToday;
import db.ReportReturnedToday;
import db.ReportYetToReturn;
import db.StudentHistory;
import models.OutPassModel;

/**
 * Created by Jayant Singh on 16/2/18.
 */

public class GeneralizeOutPass {

    public OutPassModel general(FacultySignedPasses convert) {
        OutPassModel outPass = new OutPassModel();
        outPass.setId(convert.getId());
        outPass.setUid(convert.getUid());
        outPass.setName(convert.getName());
        outPass.setPhoneNumberVisiting(convert.getPhoneNumberVisiting());
        outPass.setBranch(convert.getBranch());
        outPass.setYear(convert.getYear());
        outPass.setRoomNumber(convert.getRoomNumber());
        outPass.setTimeLeave(convert.getTimeLeave());
        outPass.setTimeReturn(convert.getTimeReturn());
        outPass.setPhoneNumber(convert.getPhoneNumber());
        outPass.setReasonVisit(convert.getReasonVisit());
        outPass.setStudentRemark(convert.getStudentRemark());
        outPass.setVisitingAddress(convert.getVisitingAddress());
        outPass.setWardenSigned(convert.getWardenSigned());
        outPass.setWardenRemark(convert.getWardenRemark());
        outPass.setWardenTalkedToParent(convert.getWardenTalkedToParent());
        outPass.setHodSigned(convert.getHodSigned());
        outPass.setHodRemark(convert.getHodRemark());
        outPass.setHodTalkedToParent(convert.getHodTalkedToParent());
        outPass.setDirectorSigned(convert.getDirectorSigned());
        outPass.setDirectorRemark(convert.getDirectorRemark());
        outPass.setDirectorPrioritySign(convert.getDirectorPrioritySign());
        outPass.setOutPassType(convert.getOutPassType());
        return outPass;
    }

    public OutPassModel general(FacultyToSignPasses convert) {
        OutPassModel outPass = new OutPassModel();
        outPass.setId(convert.getId());
        outPass.setUid(convert.getUid());
        outPass.setName(convert.getName());
        outPass.setPhoneNumberVisiting(convert.getPhoneNumberVisiting());
        outPass.setBranch(convert.getBranch());
        outPass.setYear(convert.getYear());
        outPass.setRoomNumber(convert.getRoomNumber());
        outPass.setTimeLeave(convert.getTimeLeave());
        outPass.setTimeReturn(convert.getTimeReturn());
        outPass.setPhoneNumber(convert.getPhoneNumber());
        outPass.setReasonVisit(convert.getReasonVisit());
        outPass.setStudentRemark(convert.getStudentRemark());
        outPass.setVisitingAddress(convert.getVisitingAddress());
        outPass.setWardenSigned(convert.getWardenSigned());
        outPass.setWardenRemark(convert.getWardenRemark());
        outPass.setWardenTalkedToParent(convert.getWardenTalkedToParent());
        outPass.setHodSigned(convert.getHodSigned());
        outPass.setHodRemark(convert.getHodRemark());
        outPass.setHodTalkedToParent(convert.getHodTalkedToParent());
        outPass.setDirectorSigned(convert.getDirectorSigned());
        outPass.setDirectorRemark(convert.getDirectorRemark());
        outPass.setDirectorPrioritySign(convert.getDirectorPrioritySign());
        outPass.setOutPassType(convert.getOutPassType());
        return outPass;
    }

    public OutPassModel general(ReportLeavingToday convert) {
        OutPassModel outPass = new OutPassModel();
        outPass.setId(convert.getId());
        outPass.setUid(convert.getUid());
        outPass.setName(convert.getName());
        outPass.setPhoneNumberVisiting(convert.getPhoneNumberVisiting());
        outPass.setBranch(convert.getBranch());
        outPass.setYear(convert.getYear());
        outPass.setRoomNumber(convert.getRoomNumber());
        outPass.setTimeLeave(convert.getTimeLeave());
        outPass.setTimeReturn(convert.getTimeReturn());
        outPass.setPhoneNumber(convert.getPhoneNumber());
        outPass.setReasonVisit(convert.getReasonVisit());
        outPass.setStudentRemark(convert.getStudentRemark());
        outPass.setVisitingAddress(convert.getVisitingAddress());
        outPass.setWardenSigned(convert.getWardenSigned());
        outPass.setWardenRemark(convert.getWardenRemark());
        outPass.setWardenTalkedToParent(convert.getWardenTalkedToParent());
        outPass.setHodSigned(convert.getHodSigned());
        outPass.setHodRemark(convert.getHodRemark());
        outPass.setHodTalkedToParent(convert.getHodTalkedToParent());
        outPass.setDirectorSigned(convert.getDirectorSigned());
        outPass.setDirectorRemark(convert.getDirectorRemark());
        outPass.setDirectorPrioritySign(convert.getDirectorPrioritySign());
        outPass.setOutPassType(convert.getOutPassType());
        return outPass;
    }

    public OutPassModel general(ReportLeftToday convert) {
        OutPassModel outPass = new OutPassModel();
        outPass.setId(convert.getId());
        outPass.setUid(convert.getUid());
        outPass.setName(convert.getName());
        outPass.setPhoneNumberVisiting(convert.getPhoneNumberVisiting());
        outPass.setBranch(convert.getBranch());
        outPass.setYear(convert.getYear());
        outPass.setRoomNumber(convert.getRoomNumber());
        outPass.setTimeLeave(convert.getTimeLeave());
        outPass.setTimeReturn(convert.getTimeReturn());
        outPass.setPhoneNumber(convert.getPhoneNumber());
        outPass.setReasonVisit(convert.getReasonVisit());
        outPass.setStudentRemark(convert.getStudentRemark());
        outPass.setVisitingAddress(convert.getVisitingAddress());
        outPass.setWardenSigned(convert.getWardenSigned());
        outPass.setWardenRemark(convert.getWardenRemark());
        outPass.setWardenTalkedToParent(convert.getWardenTalkedToParent());
        outPass.setHodSigned(convert.getHodSigned());
        outPass.setHodRemark(convert.getHodRemark());
        outPass.setHodTalkedToParent(convert.getHodTalkedToParent());
        outPass.setDirectorSigned(convert.getDirectorSigned());
        outPass.setDirectorRemark(convert.getDirectorRemark());
        outPass.setDirectorPrioritySign(convert.getDirectorPrioritySign());
        outPass.setOutPassType(convert.getOutPassType());
        return outPass;
    }

    public OutPassModel general(ReportReturnedToday convert) {
        OutPassModel outPass = new OutPassModel();
        outPass.setId(convert.getId());
        outPass.setUid(convert.getUid());
        outPass.setName(convert.getName());
        outPass.setPhoneNumberVisiting(convert.getPhoneNumberVisiting());
        outPass.setBranch(convert.getBranch());
        outPass.setYear(convert.getYear());
        outPass.setRoomNumber(convert.getRoomNumber());
        outPass.setTimeLeave(convert.getTimeLeave());
        outPass.setTimeReturn(convert.getTimeReturn());
        outPass.setPhoneNumber(convert.getPhoneNumber());
        outPass.setReasonVisit(convert.getReasonVisit());
        outPass.setStudentRemark(convert.getStudentRemark());
        outPass.setVisitingAddress(convert.getVisitingAddress());
        outPass.setWardenSigned(convert.getWardenSigned());
        outPass.setWardenRemark(convert.getWardenRemark());
        outPass.setWardenTalkedToParent(convert.getWardenTalkedToParent());
        outPass.setHodSigned(convert.getHodSigned());
        outPass.setHodRemark(convert.getHodRemark());
        outPass.setHodTalkedToParent(convert.getHodTalkedToParent());
        outPass.setDirectorSigned(convert.getDirectorSigned());
        outPass.setDirectorRemark(convert.getDirectorRemark());
        outPass.setDirectorPrioritySign(convert.getDirectorPrioritySign());
        outPass.setOutPassType(convert.getOutPassType());
        return outPass;
    }

    public OutPassModel general(ReportYetToReturn convert) {
        OutPassModel outPass = new OutPassModel();
        outPass.setId(convert.getId());
        outPass.setUid(convert.getUid());
        outPass.setName(convert.getName());
        outPass.setPhoneNumberVisiting(convert.getPhoneNumberVisiting());
        outPass.setBranch(convert.getBranch());
        outPass.setYear(convert.getYear());
        outPass.setRoomNumber(convert.getRoomNumber());
        outPass.setTimeLeave(convert.getTimeLeave());
        outPass.setTimeReturn(convert.getTimeReturn());
        outPass.setPhoneNumber(convert.getPhoneNumber());
        outPass.setReasonVisit(convert.getReasonVisit());
        outPass.setStudentRemark(convert.getStudentRemark());
        outPass.setVisitingAddress(convert.getVisitingAddress());
        outPass.setWardenSigned(convert.getWardenSigned());
        outPass.setWardenRemark(convert.getWardenRemark());
        outPass.setWardenTalkedToParent(convert.getWardenTalkedToParent());
        outPass.setHodSigned(convert.getHodSigned());
        outPass.setHodRemark(convert.getHodRemark());
        outPass.setHodTalkedToParent(convert.getHodTalkedToParent());
        outPass.setDirectorSigned(convert.getDirectorSigned());
        outPass.setDirectorRemark(convert.getDirectorRemark());
        outPass.setDirectorPrioritySign(convert.getDirectorPrioritySign());
        outPass.setOutPassType(convert.getOutPassType());
        return outPass;
    }

    public OutPassModel general(StudentHistory convert) {
        OutPassModel outPass = new OutPassModel();
        outPass.setId(convert.getId());
        outPass.setUid(convert.getUid());
        outPass.setName(convert.getName());
        outPass.setPhoneNumberVisiting(convert.getPhoneNumberVisiting());
        outPass.setBranch(convert.getBranch());
        outPass.setYear(convert.getYear());
        outPass.setRoomNumber(convert.getRoomNumber());
        outPass.setTimeLeave(convert.getTimeLeave());
        outPass.setTimeReturn(convert.getTimeReturn());
        outPass.setPhoneNumber(convert.getPhoneNumber());
        outPass.setReasonVisit(convert.getReasonVisit());
        outPass.setStudentRemark(convert.getStudentRemark());
        outPass.setVisitingAddress(convert.getVisitingAddress());
        outPass.setWardenSigned(convert.getWardenSigned());
        outPass.setWardenRemark(convert.getWardenRemark());
        outPass.setWardenTalkedToParent(convert.getWardenTalkedToParent());
        outPass.setHodSigned(convert.getHodSigned());
        outPass.setHodRemark(convert.getHodRemark());
        outPass.setHodTalkedToParent(convert.getHodTalkedToParent());
        outPass.setDirectorSigned(convert.getDirectorSigned());
        outPass.setDirectorRemark(convert.getDirectorRemark());
        outPass.setDirectorPrioritySign(convert.getDirectorPrioritySign());
        outPass.setOutPassType(convert.getOutPassType());
        return outPass;
    }
}
