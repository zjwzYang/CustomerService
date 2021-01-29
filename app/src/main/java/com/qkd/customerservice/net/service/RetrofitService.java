package com.qkd.customerservice.net.service;


import com.qkd.customerservice.bean.AddKnowledgeInput;
import com.qkd.customerservice.bean.AddKnowledgeOutput;
import com.qkd.customerservice.bean.AmountInput;
import com.qkd.customerservice.bean.AmountOutput;
import com.qkd.customerservice.bean.ArticleOutput;
import com.qkd.customerservice.bean.CustomerBookOutput;
import com.qkd.customerservice.bean.CustomizedListBean;
import com.qkd.customerservice.bean.DeleteKnowledgeOutput;
import com.qkd.customerservice.bean.KnowledgeOutput;
import com.qkd.customerservice.bean.LoginOutput;
import com.qkd.customerservice.bean.MySchemeDetailOutput;
import com.qkd.customerservice.bean.NewMessageInput;
import com.qkd.customerservice.bean.NewMessageOutput;
import com.qkd.customerservice.bean.PlannerOutput;
import com.qkd.customerservice.bean.PremiumConfigOutput;
import com.qkd.customerservice.bean.ProductListOutput;
import com.qkd.customerservice.bean.ProductOutput;
import com.qkd.customerservice.bean.QrCodeOutput;
import com.qkd.customerservice.bean.QueryCustomizeOutput;
import com.qkd.customerservice.bean.SaveSchemeConfigInput;
import com.qkd.customerservice.bean.SchemeConfigOutput;
import com.qkd.customerservice.bean.SchemeCustomizeInfo;
import com.qkd.customerservice.bean.TransferOutput;
import com.qkd.customerservice.bean.UpdateRemarkInput;
import com.qkd.customerservice.bean.UpdateRemarkOutput;
import com.qkd.customerservice.bean.UpdateUserTagInput;
import com.qkd.customerservice.bean.UpdateWechatInput;
import com.qkd.customerservice.bean.UserTagOutput;
import com.qkd.customerservice.bean.UserTagsBean;
import com.qkd.customerservice.bean.WxchatListOutput;
import com.qkd.customerservice.net.BaseOutput;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created on 2020/10/22 14:32
 * .
 *
 * @author yj
 */
public interface RetrofitService {

//    @GET("security/getAppToken")
//    Observable<TokenBean> getAppToken(@Query("identifier") String identifier);

    // 文章列表
    @GET("artList")
    Observable<ArticleOutput> getArtList(@Query("offset") int offset, @Query("limit") int limit,
                                         @Query("articleTitle") String articleTitle,
                                         @Query("labelName") String labelName);

    //保险产品列表
    @GET("productList")
    Observable<ProductOutput> getProductList(@Query("offset") int offset, @Query("limit") int limit,
                                             @Query("productName") String productName);

    // 发送文章或产品
    @POST("im/forwardNewsMessage")
    Observable<NewMessageOutput> postNewMessage(@Body NewMessageInput input);

    // 规划师-获取客户通讯录
    @GET("planner/getCustomerBook")
    Observable<CustomerBookOutput> getCustomerBook(@Query("identifier") String identifier);

    // app登录
    @Multipart
    @POST("planner/login")
    Observable<LoginOutput> login(@PartMap Map<String, RequestBody> requestBodyMap);

    // 更改规划师状态
    @FormUrlEncoded
    @PUT("planner/updateStatus")
    Observable<BaseOutput> updateStatus(@Field("identifier") String identifier, @Field("status") int status);

    // 查询方案客户列表
    @GET("customize/queryCustomizeList")
    Observable<QueryCustomizeOutput> queryCustomizeList(@Query("serviceId") int serviceId,
                                                        @Query("pageNum") int pageNum,
                                                        @Query("orderStatus") int orderStatus,
                                                        @Query("params") String params);

    // 待定制方案
    @GET("scheme/to-be-customized/list")
    Observable<CustomizedListBean> getList(@Query("start") String start, @Query("length") String length);

    // 查询申请信息和投保信息
    @GET("scheme/to-be-customized/getSchemeCustomizeInfo")
    Observable<SchemeCustomizeInfo> getSchemeCustomizeInfo(@Query("orderNumber") String orderNumber);

    //查询保险库
    @GET("scheme/to-be-customized/listProduct")
    Observable<ProductListOutput> getProductList(@Query("productName") String productName, @Query("productType") String productType);

    // 查询保费试算配置
    @GET("scheme/to-be-customized/getPremiumConfig")
    Observable<PremiumConfigOutput> getPremiumConfig(@Query("productId") int productId);

    // 保费试算
    @POST("scheme/to-be-customized/getAmount")
    Observable<AmountOutput> getAmount(@Body AmountInput input);

    // 保存预览
    @POST("scheme/to-be-customized/saveSchemeConfig")
    Observable<SchemeConfigOutput> saveSchemeConfig(@Body SaveSchemeConfigInput input);

    // 查询知识库列表（1-文本 2-图片 3-语音 4-视频）
    @GET("knowledge/queryKnowledge")
    Observable<KnowledgeOutput> queryKnowledge(@Query("mediaType") int mediaType,
                                               @Query("serviceId") int serviceId,
                                               @Query("pageNum") int pageNum,
                                               @Query("params") String params);

    //查询规划师列表
    @GET("planner/queryPlannerList")
    Observable<PlannerOutput> queryPlannerList(@Query("identifier") String identifier, @Query("pageNum") int pageNum);

    // 转移客户至别的规划师
    @FormUrlEncoded
    @PUT("planner/transferCustomer")
    Observable<TransferOutput> transferCustomer(@Field("openId") String openId,
                                                @Field("identifier") String identifier,
                                                @Field("newServiceId") int newServiceId,
                                                @Field("newIdentifier") String newIdentifier);

    // 发送给客户
    @FormUrlEncoded
    @PUT("scheme/to-be-customized/saveAsHasSend")
    Observable<BaseOutput> saveAsHasSend(@Field("orderNumber") long orderNumber,
                                         @Field("userId") String userId);

    // 保存为待发送
    @FormUrlEncoded
    @PUT("scheme/to-be-customized/saveAsToBeSend")
    Observable<BaseOutput> saveAsToBeSend(@Field("orderNumber") long orderNumber,
                                          @Field("userId") String userId);

    // 删除素材
    @HTTP(method = "DELETE", path = "knowledge/deleteKnowledge", hasBody = true)
    Observable<DeleteKnowledgeOutput> deleteKnowledge(@Query("mediaId") int mediaId);

    // 知识库-新增素材
    @POST("knowledge/addKnowledge")
    Observable<AddKnowledgeOutput> addKnowledge(@Body AddKnowledgeInput input);

    @GET("appUser/queryUserTag")
    Observable<UserTagsBean> queryUserTag(@Query("openId") String openId);

    // 用户-查询用户标签列表
    @GET("appUser/getUserTag")
    Observable<UserTagOutput> getUserTags(@Query("pageNum") int pageNum);

    // 更新是否已添加微信状态
    @POST("planner/updateIsAddWechat")
    Observable<BaseOutput> updateIsAddWechat(@Body UpdateWechatInput input);

    @GET("planner/getAddWechat")
    Observable<WxchatListOutput> getAddWechat(@Query("identifier") String identifier);

    @POST("appUser/updateUserTag")
    Observable<BaseOutput> updateUserTag(@Body UpdateUserTagInput input);

    @GET("knowledge/getQrCode")
    Observable<QrCodeOutput> getQrCode(@Query("identifier") String identifier);

    @POST("planner/updateRemark")
    Observable<UpdateRemarkOutput> updateRemark(@Body UpdateRemarkInput input);

    @GET("planner/getRemark")
    Observable<UpdateRemarkOutput> getRemark(@Query("openId") String openId);

    @GET("scheme/getMySchemeDetail")
    Observable<MySchemeDetailOutput> getMySchemeDetail(@Query("orderNumber") String orderNumber, @Query("userId") String userId);
}
