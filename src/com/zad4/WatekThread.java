package com.zad4;

public class WatekThread extends Thread {


    private int[] tablica;
    private int wartoscMax;
    private int wartoscMin;
    private int poczatek;
    private int koniec;

    public int getWartoscMax() {
        return wartoscMax;
    }

    public int getWartoscMin() {
        return wartoscMin;
    }

    public WatekThread(int[] tablica, int poczatek, int koniec) {
        this.tablica = tablica;
        this.poczatek = poczatek;
        this.koniec = koniec;
    }

    @Override
    public void run() {

        //SZUKAMY NAJWIĘKSZEJ WARTOŚCI
        //PRZYPISUJEMY WARTOŚCI DLA MIN I MAX
        wartoscMax = tablica[poczatek];
        wartoscMin = tablica[poczatek];

        for (int i = poczatek; i < koniec; i++) {
            if (wartoscMax < tablica[i]) {
                wartoscMax = tablica[i];
            }
        }

        //SZUKAMY NAJMNIEJSZYCH WARTOŚCI
        for (int i = poczatek; i < koniec; i++) {
            if (wartoscMin > tablica[i]) {
                wartoscMin = tablica[i];
            }
        }
    }
}
