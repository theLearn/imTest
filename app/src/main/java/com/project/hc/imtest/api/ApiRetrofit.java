package com.project.hc.imtest.api;

import com.example.hongcheng.data.retrofit.response.BaseResponse;
import com.project.hc.imtest.model.*;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by hongcheng on 16/9/11.
 */
public interface ApiRetrofit {
    
    @GET(ApiConstants.LOGIN_URL)
    Observable<BaseResponse<LoginInfo>> login(@Query("mobile") String mobile, @Query("password") String password);

    @GET(ApiConstants.CHANGE_INFO_URL)
    Observable<BaseResponse<Object>> changeInfo(@Query("userName") String userName, @Query("photo") String photo);

    @GET(ApiConstants.CHANGE_PW_URL)
    Observable<BaseResponse<Object>> changePw(@Query("old") String old, @Query("password") String password);

    @GET(ApiConstants.GET_ALL_PHOTO_URL)
    Observable<BaseResponse<List<String>>> getAllPhotos();

    @GET(ApiConstants.GET_POINT_URL)
    Observable<BaseResponse<PointInfo>> getPoint();

    @GET(ApiConstants.COMPLAIN_URL)
    Observable<BaseResponse<Object>> complain(@Query("mobile") String mobile, @Query("userName") String userName, @Query("content") String content);

    @GET(ApiConstants.GET_CARD_INFO_URL)
    Observable<BaseResponse<Object>> getCardInfo();

    @GET(ApiConstants.GET_ALIPAY_INFO_URL)
    Observable<BaseResponse<Object>> getAlipayInfo();

    @GET(ApiConstants.BIND_CARD_URL)
    Observable<BaseResponse<Object>> bindCard(@Query("bankname") String bankname, @Query("bankcode") String bankcode, @Query("rebankcode") String rebankcode, @Query("username") String username, @Query("carId") String carId);

    @GET(ApiConstants.BIND_ALIPAY_URL)
    Observable<BaseResponse<Object>> bindAlipay(@Query("code") String code, @Query("username") String username, @Query("recode") String recode);

    @GET(ApiConstants.GET_RED_RECORD_URL)
    Observable<BaseResponse<RecordList>> getRedRecord(@Query("page") int page, @Query("pageSize") int pageSize);

    @GET(ApiConstants.GET_PROFIT_URL)
    Observable<BaseResponse<RecordList>> getProfit(@Query("page") int page, @Query("pageSize") int pageSize);

    @GET(ApiConstants.GET_SYSTEM_NOTIFY_URL)
    Observable<BaseResponse<List<MsgInfo>>> getSystemNotifyList(@Query("page") int page, @Query("pageSize") int pageSize);

    @GET(ApiConstants.GET_GROUP_LIST_URL)
    Observable<BaseResponse<BaseListResponse<GroupInfo>>> getAllGroup(@Query("type") String type, @Query("page") int page, @Query("pageSize") int pageSize);

    @GET(ApiConstants.FORGET_PW_URL)
    Observable<BaseResponse<Object>> forgetPw(@Query("mobile") String mobile, @Query("password") String password, @Query("code") String code);

    @GET(ApiConstants.REGISTER_URL)
    Observable<BaseResponse<Object>> register(@Query("mobile") String mobile, @Query("password") String password, @Query("code") String code, @Query("nickname") String nickname);

    @GET(ApiConstants.GET_GROUP_INFO_URL)
    Observable<BaseResponse<GroupInfo>> getGroupInfoById( @Query("gid") String gid);

    @GET(ApiConstants.SEND_CL_RED_URL)
    Observable<BaseResponse<Object>> sendClRed(@Query("money") String money, @Query("gid") String gid, @Query("thunder  ") String thunder);

    @GET(ApiConstants.SEND_JL_RED_URL)
    Observable<BaseResponse<Object>> sendJlRed(@Query("money") String money, @Query("gid") String gid);

    @GET(ApiConstants.RECEIVE_CL_RED_URL)
    Observable<BaseResponse<Object>> receiveClRed(@Query("gid") String gid, @Query("hb_id") String hb_id);

    @GET(ApiConstants.RECEIVE_JL_RED_URL)
    Observable<BaseResponse<Object>> receiveJlRed(@Query("gid") String gid, @Query("hb_id") String hb_id);
}
