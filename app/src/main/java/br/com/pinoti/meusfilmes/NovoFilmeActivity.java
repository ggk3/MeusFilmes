package br.com.pinoti.meusfilmes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.com.pinoti.meusfilmes.dto.FilmeSync;
import br.com.pinoti.meusfilmes.model.Filme;
import br.com.pinoti.meusfilmes.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovoFilmeActivity extends AppCompatActivity {

    private List<Filme> filmes;
    private EditText editNomeFilme;
    private ListView listaFilmes;
    private ProgressBar progressRequisicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_filme);

        progressRequisicao = findViewById(R.id.requisicaoProgress);

        progressRequisicao.setVisibility(View.INVISIBLE);

        Button botaoBuscar = findViewById(R.id.botaoBusca);
        listaFilmes = findViewById(R.id.listaFilmesEncontrados);
        editNomeFilme = findViewById(R.id.editNomeFilme);


        botaoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String textoBusca = editNomeFilme.getText().toString();

                if( textoBusca.length() > 0){

                    limpaLista();

                    progressRequisicao.setVisibility(View.VISIBLE);

                    Call<FilmeSync> call = new RetrofitInicializador().getFilmesService().busca( "38038d78ea9aaebf483329afc9d53c13", "pt-BR", textoBusca  ,"false" );
                    call.enqueue(new Callback<FilmeSync>() {
                        @Override
                        public void onResponse(@NonNull Call<FilmeSync> call, @NonNull Response<FilmeSync> response) {

                            filmes = response.body().getResults();

                            carregaLista();

                            if(filmes.size() == 0){
                                Toast.makeText(NovoFilmeActivity.this, "Não foram encontrados resultados para sua busca!", Toast.LENGTH_SHORT).show();
                            }

                            progressRequisicao.setVisibility(View.INVISIBLE);

                        }

                        @Override
                        public void onFailure(@NonNull Call<FilmeSync> call, @NonNull Throwable t) {
                            Toast.makeText(NovoFilmeActivity.this, "Verifique sua conexão e tente novamente em instantes!", Toast.LENGTH_LONG).show();

                            progressRequisicao.setVisibility(View.INVISIBLE);

                        }

                    });
                }else{
                    Toast.makeText(NovoFilmeActivity.this, "Informe o nome do filme!", Toast.LENGTH_SHORT).show();
                    limpaLista();
                }



            }
        });


        listaFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(NovoFilmeActivity.this, ConfirmarFilmeActivity.class);
                intent.putExtra("filme", filmes.get(i));

                startActivity(intent);

            }
        });



    }

    public void carregaLista(){
        ArrayAdapter<Filme> adapter = new ArrayAdapter<Filme>(this, android.R.layout.simple_list_item_1, filmes);
        listaFilmes.setAdapter(adapter);
    }

    public void limpaLista(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{});
        listaFilmes.setAdapter(adapter);
    }
}
