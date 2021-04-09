package com.example.pokecarguero.Util;

import androidx.annotation.Nullable;

import java.util.List;

public class ListaUtil {

    @Nullable
    public static String[] toStringArray(List<?> itens) {
        if (itens == null) {
            return null;
        }
        String[] strings = new String[itens.size()];
        int index = 0;
        for (Object o : itens) {
            strings[index] = o.toString();
            index++;
        }
        return strings;
    }

}
