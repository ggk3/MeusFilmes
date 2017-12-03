package br.com.pinoti.meusfilmes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.pinoti.meusfilmes.model.Filme;


public class DetalhesFilmeFragment extends Fragment {


    private TextView titulo;
    private TextView nota;
    private TextView data;
    private TextView descricao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhes_filme, container, false);

        titulo = view.findViewById(R.id.titulo);
        nota = view.findViewById(R.id.nota);
        data = view.findViewById(R.id.data);
        descricao = view.findViewById(R.id.descricao);

        Bundle parametros = getArguments();
        if (parametros != null) {
            Filme filme = (Filme) parametros.getSerializable("filme");
            setFilme(filme);
        }



        return view;
    }

    public void setFilme(Filme filme){

        titulo.setText(filme.getTitle());

        descricao.setText(filme.getOverview());

        nota.setText( String.valueOf(filme.getVote_average()));
        nota.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_grade, 0, 0, 0);

        SimpleDateFormat in= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");

        try {
            String dataFormatada = out.format(in.parse(filme.getRelease_date()));
            dataFormatada = "Data de Lançamento: " + dataFormatada;
            data.setText(dataFormatada);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}
