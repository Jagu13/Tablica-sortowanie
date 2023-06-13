package com.zad4;

import java.util.Random;
import java.util.Scanner;

public class Uruchamianie {

    public static void main(String[] args) {

        //ROBIMY TABLICĘ
        int[] tablica = new int[320000000];
        Random losowaLiczba = new Random();

        for (int i = 0; i < tablica.length; i++) {
            tablica[i] = losowaLiczba.nextInt();
        }

        //INPUT
        System.out.println("Podaj ilosc watkow, które chcesz utworzyc: ");
        Scanner scan = new Scanner(System.in);
        int iloscWatkow = scan.nextInt();
        scan.nextLine();

        //WYLICZANIE PRZEDZIAŁÓW
        int dlugoscPrzedzialu = (int) Math.floor(tablica.length / iloscWatkow);

        //TABLICE POCZĄTKÓW I KOŃCÓW
        int[] tablicaPoczatkow = new int[iloscWatkow];
        int[] tablicaKoncow = new int[iloscWatkow];

        //WARTOŚCI POCZĄTKÓW PRZEDZIAŁÓW
        for (int i = 0; i <= tablicaPoczatkow.length - 1; i++) {
            if (i == 0) {
                tablicaPoczatkow[i] = 0;
            } else if (i == (tablicaPoczatkow.length - 1)) {
                tablicaPoczatkow[i] = dlugoscPrzedzialu * (iloscWatkow - 1) - 1;
            } else {
                tablicaPoczatkow[i] = i * dlugoscPrzedzialu - 1;
            }
        }

        //WARTOŚCI KOŃCÓW PRZEDZIAŁÓW
        for (int i = 0; i <= tablicaKoncow.length - 1; i++) {
            if (i == 0) {
                tablicaKoncow[i] = dlugoscPrzedzialu;
            } else if (i == tablicaKoncow.length - 1) {
                tablicaKoncow[i] = tablica.length - 1;
            } else {
                tablicaKoncow[i] = (i+1) * dlugoscPrzedzialu;
            }
        }

//        //PRINTOWANIE TABLIC POCZĄTKÓW I KOŃCÓW
//        for (int i = 0; i <= tablicaPoczatkow.length - 1; i++) {
//            System.out.print(tablicaPoczatkow[i] + ", ");
//        }
//        System.out.println();
//        for (int i = 0; i <= tablicaKoncow.length - 1; i++) {
//            System.out.print(tablicaKoncow[i] + ", ");
//        }

        // ______________________________________________________________________________________

        //TABLICA MIN I MAX
        int[] tablicaMinMaxZWatkow = new int[iloscWatkow * 2];

        //TWORZENIE WĄTKÓW
        Thread[] tablicaWatkow = new Thread[iloscWatkow];
        for (int i = 0; i < iloscWatkow; i++) {
            tablicaWatkow[i] = new WatekThread(tablica, tablicaPoczatkow[i], tablicaKoncow[i]);
        }

        long t1 = System.nanoTime(); //ZCZYTUJEMY CZAS ROZPOCZĘCIA SIĘ WĄTKU

        //URUCHAMIAMY WĄTEK
        for (int i = 0; i < iloscWatkow; i++) {
            tablicaWatkow[i].start();
        }

        //CZEKAMY NA ZAKOŃCZENIE WĄTKU
        for (int i = 0; i < iloscWatkow; i++) {
            try {
                tablicaWatkow[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //PRZYPISUJEMY WARTOŚCI DO TABLICY MIN I MAX
        for (int i = 0; i < tablicaWatkow.length; i++) {
            WatekThread tmp = (WatekThread)tablicaWatkow[i];
            tablicaMinMaxZWatkow[2 * i] = tmp.getWartoscMin();
            tablicaMinMaxZWatkow[2 * i + 1] = tmp.getWartoscMax();
        }

//        System.out.print("Tablica min i max: ");
//        for (int i = 0; i < tablicaMinMaxZWatkow.length; i++) {
//            System.out.print(tablicaMinMaxZWatkow[i] + ", ");
//        }

//        //PRINTUJEMY CAŁĄ TABLICĘ
//        System.out.println();
//        for (int i = 0; i < tablica.length; i++) {
//            System.out.print(tablica[i]+" ");
//        }
//        System.out.println();


        //WYSZUKIWANIE KOŃCOWEGO WYNIKU
        WatekThread watekOstatni = new WatekThread(tablicaMinMaxZWatkow, 0, tablicaMinMaxZWatkow.length);
        watekOstatni.start();
        try {
            watekOstatni.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //PRINTUJEMY WARTOŚCI MIN I MAX
        System.out.println(watekOstatni.getWartoscMax());
        System.out.println(watekOstatni.getWartoscMin());

        long t2 = System.nanoTime(); //ZCZYTUJEMY CZAS ZAKOŃCZENIA SIĘ WĄTKU

        long t3 = (t2-t1)/1000000; //OBLICZAMY CZAS
        System.out.println(t3);
    }
}

//Szybkość działania programu zależy od ilości rdzeni procesora.
//Na 6-rdzeniowym, 12-wątkowym procesorze (dane niżej) wyniki wyszukiwania
//wartości min i max w 320mln-elementowej tablicy wynosiły w zależności od ilości wąrków
//od 100 do 350 ms.
//Długośc działania programu stopniowo spadała przy ilości 1-8 wątków,
//natomiast po zwiększeniu ich do 20-500 zaobserwowano wydłużenie działania programu.

//Procesor AMD Ryzen 5 2600 Six-Core Processor, 3400 MHz, Rdzenie: 6, Procesory logiczne: 12
//Nazwa systemu operacyjnego: Microsoft Windows 10 Home
//Pamięć RAM: 16GB
