package com.project.hc.imtest.api;

public class ApiConstants {
    //登录路径
    public static final String LOGIN_URL = "user/login";
    //修改密码
    public static final String CHANGE_PW_URL = "user/change_password";
    //修改个人资料 图片可以为空 昵称不能为空
    public static final String CHANGE_INFO_URL = "user/changeInfo";
    //得到后台的全部头像
    public static final String GET_ALL_PHOTO_URL = "user/allPhotos";
    //得到自己的当前积分
    public static final String GET_POINT_URL = "user/getPoint";
    //新增投诉
    public static final String COMPLAIN_URL = "complain/addComplain";
    //得到用户的银行卡信息
    public static final String GET_CARD_INFO_URL = "Pay/bankcard";
    //请求得到支付宝的账号
    public static final String GET_ALIPAY_INFO_URL = "Pay/aliCode";
    //绑定银行卡
    public static final String BIND_CARD_URL = "Pay/bindbankCard";
    //提交支付宝资料
    public static final String BIND_ALIPAY_URL = "Pay/bindali";
    //红包记录
    public static final String GET_RED_RECORD_URL = "user/hb_record";
    //我的收益
    public static final String GET_PROFIT_URL = "user/my_profit";
    //获取系统通知
    public static final String GET_SYSTEM_NOTIFY_URL = "AppNotice/noticeList";
    //获取所有群
    public static final String GET_GROUP_LIST_URL = " user/all_group";
    //忘记密码
    public static final String FORGET_PW_URL = "user/forget_password";
    //注册
    public static final String REGISTER_URL = "user/register";
    //根据id获取群信息
    public static final String GET_GROUP_INFO_URL = "AppGroup/groupInfo";
    //发踩雷红包
    public static final String SEND_CL_RED_URL = "RedPacket/sendCailRed";
    //发接龙红包
    public static final String SEND_JL_RED_URL = "RedPacket/sendJielRed";
    //抢踩雷红包
    public static final String RECEIVE_CL_RED_URL = "RedPacket/robCailRed";
    //抢接龙红包
    public static final String RECEIVE_JL_RED_URL = "RedPacket/robJielRed";
    //获取红包详情
    public static final String GET_RED_DETAIL_URL = "RedPacket/redRecord";
    //获取客服信息
    public static final String GET_KF_ID_URL = "user/get_kf";
    //获取时间戳
    public static final String GET_TIME_URL = "code/get_time";
    //获取短信验证码
    public static final String GET_SMS_CODE_URL = "code/phoneMd5";
    //获取二维码
    public static final String GET_QR_CODE_URL = "user/getQrCodeUrl";

    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "password";
    public static final String AUTOLOGIN = "autoLogin";
}
