package cc.doctor.stars_app.http;

import java.util.List;
import java.util.Map;

import cc.doctor.stars_app.http.user.RsResponse;
import cc.doctor.stars_app.http.user.SearchHisResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface SearchApi {
    @GET("search/his/page")
    Call<PageResponse<SearchHisResponse>> searchHis(@QueryMap Map<String, Object> request, @Header("token") String token);

    @GET("search/rs")
    Call<Response<List<RsResponse>>> searchRs(@Query("keywords") String keywords, @Query("size") Integer size, @Header("token") String token);

    @GET("search/suggest")
    Call<Response<List<String>>> searchSuggest(@Query("keywords") String keywords, @Header("token") String token);
}
