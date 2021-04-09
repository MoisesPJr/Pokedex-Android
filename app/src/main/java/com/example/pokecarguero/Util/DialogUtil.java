package com.example.pokecarguero.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.List;

public class DialogUtil {

    public static void escolherDeUmaLista(final Context context, final String titulo, List<? extends Object> itens, final OnRetornoEscolha onRetornoEscolha) {
        String[] stringArray = ListaUtil.toStringArray(itens);
        if (stringArray == null) return;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(titulo);
        dialogBuilder.setItems(stringArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onRetornoEscolha.onSucesso(which);
            }
        });

        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onRetornoEscolha.onCancelado();
            }
        });
        dialogBuilder.show();

    }

    public interface OnRetornoEscolha {
        void onSucesso(int posicaoDoItem);

        void onCancelado();
    }

}
