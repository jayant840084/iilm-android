/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net;

import db.FacultySignedPasses;
import db.FacultyToSignPasses;
import db.ReportLeavingToday;
import db.ReportLeftToday;
import db.ReportReturnedToday;
import db.ReportYetToReturn;
import db.StudentHistory;
import io.realm.RealmResults;
import models.GuardLogModel;
import models.LoginModel;
import models.OutPassModel;
import models.UserModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by jayan on 16-11-2016.
 */

public interface ApiInterface {

    @GET("auth/login")
    Call<LoginModel> getLogin(
            @Header("uid") String uid,
            @Header("password") String password
    );

    @GET("user")
    Call<UserModel> getUser(
            @Header("token") String token
    );

    @GET("outpass")
    Call<List<StudentHistory>> getOutPasses(
            @Header("token") String token,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("outpass")
    Call<List<FacultySignedPasses>> getOutPassesSigned(
            @Header("token") String token,
            @Query("onlySigned") boolean signed,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("outpass")
    Call<List<FacultyToSignPasses>> getOutPassesUnsigned(
            @Header("token") String token,
            @Query("onlyUnsigned") boolean unsigned,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("report/leaving-today")
    Call<List<ReportLeavingToday>> getStudentsLeavingToday(
            @Header("token") String token,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("report/returned-today")
    Call<List<ReportReturnedToday>> getStudentsReturnedToday(
            @Header("token") String token,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("report/left-today")
    Call<List<ReportLeftToday>> getStudentsLeftToday(
            @Header("token") String token,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("report/yet-to-return")
    Call<List<ReportYetToReturn>> getStudentsYetToReturn(
            @Header("token") String token,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("check-update")
    Call<ResponseBody> checkUpdate();

    @FormUrlEncoded
    @POST("outpass")
    Call<ResponseBody> postRequestOutPass(
            @Header("token") String token,
            @Field("outpassType") String outPassType,
            @Field("leaveTime") Long leaveTime,
            @Field("returnTime") Long returnTime,
            @Field("phoneNumber") String phoneNumber,
            @Field("visitingAddress") String visitingAddress,
            @Field("reasonVisit") String reasonVisit,
            @Field("remark") String studentRemark
    );

    @FormUrlEncoded
    @PUT("auth/logout")
    Call<ResponseBody> putLogout(
            @Header("token") String token,
            @Field("token") String firebaseToken
    );

    @FormUrlEncoded
    @PUT("auth/reset-password")
    Call<UserModel> putResetPassword(
            @Field("uid") String uid
    );

    @FormUrlEncoded
    @PUT("sign")
    Call<ResponseBody> putSignDirector(
            @Header("token") String token,
            @Field("id") String id,
            @Field("allowed") boolean allowed,
            @Field("prioritySign") boolean priority,
            @Field("remark") String remark
    );

    @FormUrlEncoded
    @PUT("sign")
    Call<ResponseBody> putSignWarden(
            @Header("token") String token,
            @Field("id") String id,
            @Field("allowed") boolean allowed,
            @Field("talkedToParent") boolean talkedToParent,
            @Field("remark") String remark
    );

    @FormUrlEncoded
    @PUT("sign")
    Call<ResponseBody> putSignHod(
            @Header("token") String token,
            @Field("id") String id,
            @Field("allowed") boolean allowed,
            @Field("talkedToParent") boolean talkedToParent,
            @Field("remark") String remark
    );

    @FormUrlEncoded
    @PUT("firebase/add-token")
    Call<ResponseBody> putAddFirebaseToken(
            @Header("token") String token,
            @Field("token") String firebaseToken
    );

    @FormUrlEncoded
    @PUT("log-leave")
    Call<GuardLogModel> putLogLeave(
            @Header("token") String token,
            @Field("id") String id
    );

    @FormUrlEncoded
    @PUT("log-return")
    Call<GuardLogModel> putLogReturn(
            @Header("token") String token,
            @Field("id") String id
    );

    @FormUrlEncoded
    @PUT("auth/change-password")
    Call<ResponseBody> putChangePassword(
            @Header("token") String token,
            @Field("oldPassword") String oldPassword,
            @Field("newPassword") String newPassword
    );
}
