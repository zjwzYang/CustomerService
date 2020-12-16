package com.qkd.customerservice.net.service;


import com.qkd.customerservice.bean.ArticleOutput;
import com.qkd.customerservice.bean.CustomerBookOutput;
import com.qkd.customerservice.bean.NewMessageInput;
import com.qkd.customerservice.bean.NewMessageOutput;
import com.qkd.customerservice.bean.ProductOutput;
import com.qkd.customerservice.bean.TokenBean;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created on 2020/10/22 14:32
 * .
 *
 * @author yj
 */
public interface RetrofitService {

    @GET("security/getToken")
    Observable<TokenBean> getToken(@Query("userId") int userId);

    // 文章列表
    @GET("artList")
    Observable<ArticleOutput> getArtList(@Query("offset") int offset, @Query("limit") int limit);

    //保险产品列表
    @GET("productList")
    Observable<ProductOutput> getProductList(@Query("offset") int offset, @Query("limit") int limit);

    // 发送文章或产品
    @POST("im/forwardNewsMessage")
    Observable<NewMessageOutput> postNewMessage(@Body NewMessageInput input);

    @GET("planner/getCustomerBook")
    Observable<CustomerBookOutput> getCustomerBook(@Query("identifier") String identifier, @Query("userStatus") int userStatus);
}
