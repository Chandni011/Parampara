package com.ambitious.parampara.Connection;

import com.ambitious.parampara.Model.nativePlace;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LoadInterface {


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% SignUp %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("signup")
    Call<ResponseBody> signup(@Query("username") String username,
                              @Query("email") String email,
                              @Query("mobile") String mobile,
                              @Query("password") String password,
                              @Query("register_id") String register_id,
                              @Query("type") String type,
                              @Query("lattitude") String lattitude,
                              @Query("logitude") String logitude);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Login %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("login")
    Call<ResponseBody> Login(@Query("email") String email,
                             @Query("password") String password,
                             @Query("register_id") String register_id,
                             @Query("type") String type);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% GET PROFILE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @GET("get_profile")
    Call<ResponseBody> GetProfile(@Query("user_id") String user_id);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% GET PROFILE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @GET("get_slider")
    Call<ResponseBody> GetSlider();


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% OTP VERIFY %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @GET("verify_otp")
    Call<ResponseBody> OTPverify(@Query("user_id") String user_id,
                                 @Query("otp") String code);


    @GET("resend_otp")
    Call<ResponseBody> resendOTP(@Query("mobile") String mobile);


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% update PROFILE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


    @GET("get_driver_list_nearbuy")
    Call<ResponseBody> drivershow(@Query("lat") String lat,
                                  @Query("lon") String lon,
                                  @Query("driver_type") String driver_type);


    @POST("add_PoojaRating")
    Call<ResponseBody> addPoojaRating(@Query("id") String id,
                                      @Query("rating") String rating,
                                      @Query("panditId") String panditId,
                                      @Query("feedBack") String feedBack,
                                      @Query("type") String type
    );


    @GET("change_pooja_booking_status")
    Call<ResponseBody> change_pooja_booking_status(@Query("pooja_booking_id") String pooja_booking_id,
                                                   @Query("status") String user_id);

    @POST("add_OrderRating")
    Call<ResponseBody> add_OrderRating(@Query("id") String id,
                                       @Query("rating") String rating,
                                       @Query("produtId") String produtId,
                                       @Query("feedBack") String feedBack,
                                       @Query("type") String type
    );


    @GET("get_pooja")
    Call<ResponseBody> get_pooja();


    @POST("forgot_password")
    Call<ResponseBody> forgetpassword(@Query("mobile") String mobile);

    @GET("pooja_booking")
    Call<ResponseBody> sendrequest(@Query("pooja_id") String pooja_id,
                                   @Query("pooja_name") String pooja_name,
                                   @Query("user_id") String user_id,
                                   @Query("username") String username,
                                   @Query("date") String date,
                                   @Query("time") String time,
                                   @Query("flat") String flat,
                                   @Query("landmark") String landmark,
                                   @Query("colony") String colony,
                                   @Query("pin") String pin,
                                   @Query("price") String price,
                                   @Query("with_item") String with_item,
                                   @Query("status") String status,
                                   @Query("lattitude") String latitude,
                                   @Query("longitude") String longitude,
                                   @Query("payment_type") String payment_type,
                                   @Query("transection_id") String transection_id);
//status=pending
//http://ambitious.in.net/new/parampara/index.php/api/pooja_booking?pooja_id=1&user_id=44&date=2-4-2020&time=11%3A02&flat=add&landmark=land&colony=colony&pin=42184&price=1200&with_item=0&payment_type=cash

    //http://ambitious.in.net/new/parampara/index.php/api/get_pooja_booking?user_id=44&status=pending


    //http://ambitious.in.net/new/parampara/index.php/api/get_pooja_booking?status=pending

  /*  @GET("get_pooja_booking")
    Call<ResponseBody> get_pooja_booking(@Query("status")String status);
//http://ambitious.in.net/new/parampara/index.php/api/get_pooja_booking?user_id=2&status=accepted*/

    @POST("get_pooja_booking")
    Call<ResponseBody> acceptbooking(@Query("user_id") String user_id,
                                     @Query("type") int type,
                                     @Query("status") String status);


    @GET("get_category")
    Call<ResponseBody> get_category();


    @GET("get_order")
    Call<ResponseBody> get_order(@Query("userId") String user_id);


    @GET("change_order_status")
    Call<ResponseBody> change_status(@Query("status") String status,
                                     @Query("orderId") String orderId,
                                     @Query("productId") String product_id
    );


    @POST("get_Product")
    Call<ResponseBody> get_product(@Query("category_id") String category_id);


    @POST("add_order")
    Call<ResponseBody> add_order(@Query("user_id") String user_id,
                                 @Query("user_name") String user_name,
                                 @Query("category_id") String category_id,
                                 @Query("category_name") String category_name,
                                 @Query("product_id") String product_id,
                                 @Query("product_name") String product_name,
                                 @Query("address") String address,
                                 @Query("state") String state,
                                 @Query("city") String city,
                                 @Query("pincode") String pincode,
                                 @Query("latitude") String latitude,
                                 @Query("longitude") String longitude,
                                 @Query("status") String status,
                                 @Query("updateTime") String updateTime,
                                 @Query("amount") String amount,
                                 @Query("admin_amount") String admin_amount,
                                 @Query("quantity") String quantity,
                                 @Query("payment_mode") String payment_mode,
                                 @Query("entry_date") String entry_date,
                                 @Query("vendorId") String vender_id,
                                 @Query("transection_id") String transection_id);
    //http://ambitious.in.net/new/parampara/index.php/api/get_product?category_id=2

    @GET("https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=1500&sensor=true&key=AIzaSyDILYQC5PYXYdvTCjttiKZj4bBF7LT7vtQ")
    Call<nativePlace> getNearbyPlace(@Query("location") String loc,
                                     @Query("keyword") String type);

    @Multipart
    @POST("signup")
    Call<ResponseBody> UpdateProfile(@Query("user_id") String user_id,
                                     @Query("username") String username,
                                     @Query("email") String email,
                                     @Query("mobile") String mobile,
                                     @Query("alternate_no") String alternate_no,
                                     @Query("address") String address,
                                     @Query("city") String city,
                                     @Query("state") String state,
                                     @Query("landmark") String landmark,
                                     @Query("country") String country,
                                     @Query("pincode") String pincode,
                                     @Query("lattitude") String latitude,
                                     @Query("logitude") String longitude,
                                     @Part MultipartBody.Part body);


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% SOCIAL: LOGIN %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    @GET("social_login")
    Call<ResponseBody> social_login(@Query("social_id") String social_id,
                                    @Query("username") String username,
                                    @Query("email") String email,
                                    @Query("mobile") String mobileString,
                                    @Query("type") String type,
                                    @Query("register_id") String register_id,
                                    @Query("image") String imageUrlString);

    @POST("change_password")
    Call<ResponseBody> changepasswordapi(@Query("user_id") String user_id,
                                         @Query("old_password") String old_password,
                                         @Query("password") String password);


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Paramars : API %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @GET("get_virtual_service")
    Call<ResponseBody> getVertualService();


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Paramars : API %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    @POST("kundali")
    Call<ResponseBody> makekundali(@Query("user_id") String userID,
                                   @Query("name") String name,
                                   @Query("date_time") String currentDate,
                                   @Query("dob") String dateOfBrith,
                                   @Query("place_birth") String placeOfBirth,
                                   @Query("price") String vPrice,
                                   @Query("description") String desc,
                                   @Query("contact") String cont,
                                   @Query("email") String em,
                                   @Query("gender") String gender,
                                   @Query("time_of_birth") String tob,
                                   @Query("address") String add,
                                   @Query("language") String prefferedlanguage,
                                   @Query("transection_id") String TraID);


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Paramars : API %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    @POST("paramarsh")
    Call<ResponseBody> makeParamars(@Query("user_id") String userID,
                                    @Query("name") String name,
                                    @Query("date_time") String currentDate,
                                    @Query("dob") String dateOfBrith,
                                    @Query("place_birth") String placeOfBirth,
                                    @Query("question") String question,
                                    @Query("preffered_timing") String prefferdTime,
                                    @Query("call_option") String callOption,
                                    @Query("price") String vPrice,
                                    @Query("description") String desc,
                                    @Query("dob_time") String tob,
                                    @Query("email") String em,
                                    @Query("gender") String gender,
                                    @Query("contact") String cont,
                                    @Query("prefered_language") String prefferedlanguage,
                                    @Query("transection_id") String TraID);

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Paramars : API %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


    @POST("get_paramars_booking")
    Call<ResponseBody> get_paramars_booking(@Query("user_id") String user_id);


    @POST("get_kundali_booking")
    Call<ResponseBody> get_kundali_booking(@Query("user_id") String user_id);


    @GET("get_ayojan")
    Call<ResponseBody> get_ayojan();


    @POST("bhavya_ayojan")
    Call<ResponseBody> submit_bhavya_ayojan(@Query("ayojan_id") String ayojan_id,
                                            @Query("ayojan_name") String ayojan_name,
                                            @Query("user_id") String user_id,
                                            @Query("user_name") String user_name,
                                            @Query("date") String date,
                                            @Query("time") String time,
                                            @Query("amount") String amount,
                                            @Query("address") String address,
                                            @Query("pincode") String pincode,
                                            @Query("city") String city,
                                            @Query("entry_date") String entry_date,
                                            @Query("pay_type") String pay_type,
                                            @Query("transection_id") String transection_id,
                                            @Query("lattitude") String latitude,
                                            @Query("longitude") String longitude,
                                            @Query("status") String status);


    @GET("get_mandal")
    Call<ResponseBody> get_mandal();


    @POST("mandal_booked")
    Call<ResponseBody> mandal_booked(@Query("mandal_id") String mandal_id,
                                     @Query("mandali_name") String mandali_name,
                                     @Query("user_id") String user_id,
                                     @Query("user_name") String user_name,
                                     @Query("date") String date,
                                     @Query("time") String time,
                                     @Query("amount") String amount,
                                     @Query("address") String address,
                                     @Query("pincode") String pincode,
                                     @Query("city") String city,
                                     @Query("entry_date") String entry_date,
                                     @Query("transection_id") String transection_id,
                                     @Query("pay_type") String pay_type,
                                     @Query("lattitude") String latitude,
                                     @Query("longitude") String longitude,
                                     @Query("status") String status);


    @GET("get_home_dailypandit")
    Call<ResponseBody> get_home_dailypandit();


    @GET("get_office_dailypandit")
    Call<ResponseBody> get_office_dailypandit();

    @GET("get_terms")
    Call<ResponseBody> get_terms();

    @GET("get_donation")
    Call<ResponseBody> get_donation();

    @GET("get_commission")
    Call<ResponseBody> get_commission();


    @POST("donation_pay")
    Call<ResponseBody> donation_pay(@Query("userId") String id,
                                    @Query("userName") String name,
                                    @Query("address") String add,
                                    @Query("longitude") String longitude,
                                    @Query("lattitude") String lattitude,
                                    @Query("pincode") String pincode,
                                    @Query("city") String city,
                                    @Query("cause") String cause,
                                    @Query("discription") String description,
                                    @Query("amt") String ammoun,
                                    @Query("note") String note,
                                    @Query("mode") String paymentmode,
                                    @Query("statuse") String status,
                                    @Query("entrydate") String date,
                                    @Query("transection_id") String transationId,
                                    @Query("mobile") String contact,
                                    @Query("email") String email);


    @POST("dailypandit_booking")
    Call<ResponseBody> pandit_booking(@Query("subscription_id") String subscription_id,
                                      @Query("user_id") String user_id,
                                      @Query("user_name") String name,
                                      @Query("email") String email,
                                      @Query("mobile") String mobile,
                                      @Query("pay_type") String pay_type,
                                      @Query("price") String price,
                                      @Query("entry_date") String entry_date,
                                      @Query("status") String status,
                                      @Query("transection_id") String transection_id,
                                      @Query("address") String address,
                                      @Query("longitude") String longitude,
                                      @Query("lattitude") String lattitude,
                                      @Query("pincode") String pincode,
                                      @Query("date") String date,
                                      @Query("time") String time);



    @POST("check_available_time")
    Call<ResponseBody> check_available_time(@Query("date") String date,
                                            @Query("time") String time);


    @POST("confrm_range_pandit")
    Call<ResponseBody> confrm_range_pandit(@Query("longi") double longi,
                                           @Query("lati") double lati);

    @POST("get_ayojan_booking")
    Call<ResponseBody> get_ayojan_booking(@Query("user_id") String userID);

    @POST("get_mandal_booking")
    Call<ResponseBody> get_mandal_booking(@Query("user_id") String userID);

    @POST("dailypandit_booking_submittion")
    Call<ResponseBody> dailypandit_booking_submittion(@Query("user_id") String userID);

    @POST("donation_submittion")
    Call<ResponseBody> donation_submittion(@Query("user_id") String userID);

    @POST("get_privacy_policy")
    Call<ResponseBody> get_privacy_policy();

    @POST("get_about")
    Call<ResponseBody> get_about();

    @POST("get_admin_profile")
    Call<ResponseBody> get_admin_profile();
}
