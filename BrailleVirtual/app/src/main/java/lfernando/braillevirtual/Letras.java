package lfernando.braillevirtual;

/**
 * Created by Luis Fernando Gomes Sales on 17/11/2016.
 */


public class Letras {

    char[] letras = new char[]{'a', 'e', 'i', 'o', 'u'};
    int[][] pontos = new int[][]{
            new int[]{1, 0, 0, 0, 0, 0},
            new int[]{1, 0, 0, 0, 1, 0},
            new int[]{0, 1, 0, 1, 0, 0},
            new int[]{1, 0, 1, 0, 1, 0},
            new int[]{1, 0, 1, 0, 0, 1}

    };

    public char getLetras(int i) {
        return letras[i];
    }

    public int[] getPontos(int i) {
        return pontos[i];
    }
}
