package com.qkd.customerservice.net.service;


import com.qkd.customerservice.bean.AmountInput;
import com.qkd.customerservice.bean.ArticleOutput;
import com.qkd.customerservice.bean.CustomerBookOutput;
import com.qkd.customerservice.bean.CustomizedListBean;
import com.qkd.customerservice.bean.LoginOutput;
import com.qkd.customerservice.bean.NewMessageInput;
import com.qkd.customerservice.bean.NewMessageOutput;
import com.qkd.customerservice.bean.PremiumConfigOutput;
import com.qkd.customerservice.bean.ProductListOutput;
import com.qkd.customerservice.bean.ProductOutput;
import com.qkd.customerservice.bean.QueryCustomizeOutput;
import com.qkd.customerservice.bean.SchemeCustomizeInfo;
import com.qkd.customerservice.net.BaseOutput;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    Observable<ArticleOutput> getArtList(@Query("offset") int offset, @Query("limit") int limit);

    //保险产品列表
    @GET("productList")
    Observable<ProductOutput> getProductList(@Query("offset") int offset, @Query("limit") int limit);

    // 发送文章或产品
    @POST("im/forwardNewsMessage")
    Observable<NewMessageOutput> postNewMessage(@Body NewMessageInput input);

    // 规划师-获取客户通讯录
    @GET("planner/getCustomerBook")
    Observable<CustomerBookOutput> getCustomerBook(@Query("identifier") String identifier, @Query("userStatus") int userStatus);

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
                                                        @Query("orderStatus") int orderStatus);

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
    Observable<BaseOutput> getAmount(@Body AmountInput input);
}
