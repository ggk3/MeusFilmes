package br.com.pinoti.meusfilmes;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.pinoti.meusfilmes.model.Filme;

public class DetalhesFilmeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_filme);

        Filme filme = (Filme) getIntent().getSerializableExtra("filme");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();

        DetalhesFilmeFragment detalhesFragment = new DetalhesFilmeFragment();
        Bundle parametros = new Bundle();
        parametros.putSerializable("filme", filme);
        detalhesFragment.setArguments(parametros);

        tx.replace(R.id.frame_detalhes, detalhesFragment);

        tx.commit();

    }
}
