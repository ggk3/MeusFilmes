package br.com.pinoti.meusfilmes.service;

import br.com.pinoti.meusfilmes.dto.FilmeSync;
import br.com.pinoti.meusfilmes.model.Filme;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmesService {

    @GET("movie")
    Call<FilmeSync> busca(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query, @Query("include_adult") String adult);
}
