package br.com.pinoti.meusfilmes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import br.com.pinoti.meusfilmes.dao.FilmeDAO;
import br.com.pinoti.meusfilmes.model.Filme;

public class ListaFilmesActivity extends AppCompatActivity {

    private ListView listaFilmes;
    private List<Filme> filmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filmes);

        Button botaoNovo = findViewById(R.id.buttonAddFilme);

        botaoNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaFilmesActivity.this, NovoFilmeActivity.class);
                startActivity(intent);
            }
        });

        listaFilmes = findViewById(R.id.listaFilmes);

        listaFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ListaFilmesActivity.this, DetalhesFilmeActivity.class);
                intent.putExtra("filme", filmes.get(i));
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        atualizaLista(false);
    }

    public void atualizaLista(boolean ordenaData){

        FilmeDAO dao = new FilmeDAO(this);
        filmes = dao.listaFilmes(ordenaData);

        ArrayAdapter<Filme> adapter = new ArrayAdapter<Filme>(this, android.R.layout.simple_list_item_1, filmes);
        listaFilmes.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_filmes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_ordena_alfabetico:
                atualizaLista(false);
                Toast.makeText(this, "Ordem Alfabetica", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_ordena_data:
                atualizaLista(true);
                Toast.makeText(this, "Ordem de Lançamento", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
