/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net;

import net.models.LogLeaveModel;
import net.models.LogReturnModel;
import net.models.LoginModel;
import net.models.OutPassModel;
import net.models.UserModel;

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
    Call<List<OutPassModel>> getOutPasses(
            @Header("token") String token,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("outpass")
    Call<List<OutPassModel>> getOutPassesSigned(
            @Header("token") String token,
            @Query("onlySigned") boolean signed,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("outpass")
    Call<List<OutPassModel>> getOutPassesUnsigned(
            @Header("token") String token,
            @Query("onlyUnsigned") boolean unsigned,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("report/leaving-today")
    Call<List<OutPassModel>> getStudentsLeavingToday(
            @Header("token") String token,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("report/returned-today")
    Call<List<OutPassModel>> getStudentsReturnedToday(
            @Header("token") String token,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("report/left-today")
    Call<List<OutPassModel>> getStudentsLeftToday(
            @Header("token") String token,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("report/yet-to-return")
    Call<List<OutPassModel>> getStudentsYetToReturn(
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
            @Field("reasonVisit") String reasonVisit
    );

    @FormUrlEncoded
    @PUT("auth/logout")
    Call<ResponseBody> putLogout(
            @Header("token") String token,
            @Field("token") String firebaseToken
    );

    @FormUrlEncoded
    @PUT("sign")
    Call<ResponseBody> putSignOutPass(
            @Header("token") String token,
            @Field("id") String id,
            @Field("allowed") Boolean allowed
    );

    @FormUrlEncoded
    @PUT("sign")
    Call<ResponseBody> putSignOutPassDirectorPriority(
            @Header("token") String token,
            @Field("id") String id,
            @Field("allowed") Boolean allowed,
            @Field("prioritySign") String prioritySign
    );

    @FormUrlEncoded
    @PUT("firebase/add-token")
    Call<ResponseBody> putAddFirebaseToken(
            @Header("token") String token,
            @Field("token") String firebaseToken
    );

    @FormUrlEncoded
    @PUT("log-leave")
    Call<LogLeaveModel> putLogLeave(
            @Header("token") String token,
            @Field("id") String id
    );

    @FormUrlEncoded
    @PUT("log-return")
    Call<LogReturnModel> putLogReturn(
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
