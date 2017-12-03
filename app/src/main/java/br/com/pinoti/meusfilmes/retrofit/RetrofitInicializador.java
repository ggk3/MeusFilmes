package br.com.pinoti.meusfilmes.retrofit;

import br.com.pinoti.meusfilmes.service.FilmesService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class RetrofitInicializador {

    private final Retrofit retrofit;

    public RetrofitInicializador(){

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/search/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public FilmesService getFilmesService() {
        return retrofit.create(FilmesService.class);
    }
}
