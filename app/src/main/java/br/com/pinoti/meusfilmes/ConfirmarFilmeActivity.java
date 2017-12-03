package br.com.pinoti.meusfilmes;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.pinoti.meusfilmes.dao.FilmeDAO;
import br.com.pinoti.meusfilmes.model.Filme;

public class ConfirmarFilmeActivity extends AppCompatActivity {

    private Filme filme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_filme);

        filme = (Filme) getIntent().getSerializableExtra("filme");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();

        DetalhesFilmeFragment detalhesFragment = new DetalhesFilmeFragment();
        Bundle parametros = new Bundle();
        parametros.putSerializable("filme", filme);
        detalhesFragment.setArguments(parametros);

        tx.replace(R.id.confirmar_detalhes, detalhesFragment);

        tx.commit();

        Button botaoSalvar = findViewById(R.id.botaoConfirmar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilmeDAO dao = new FilmeDAO(ConfirmarFilmeActivity.this);
                Boolean response = dao.insereFilme(filme);

                if(response){
                    Toast.makeText(ConfirmarFilmeActivity.this, "Filme salvo com sucesso!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ConfirmarFilmeActivity.this, "Filme já encontra-se no dispositivo!", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }
}
