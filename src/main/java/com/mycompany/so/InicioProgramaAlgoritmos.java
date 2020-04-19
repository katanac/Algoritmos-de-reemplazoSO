package com.mycompany.so;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Karen.Pena
 */
public class InicioProgramaAlgoritmos implements AlgortimosInterface {

    private static ArrayList<Integer> paginas;
    private static int cantidadPaginas, cantidadFrames;
    private static int AlgoritmoElegido;
    private static int[] valorPagina;

    public static void main(String[] args) {
        paginas = new ArrayList<>();

        JOptionPane.showMessageDialog(null, "Bienvenido al programa de algoritmos de reemplazo");

        InicioProgramaAlgoritmos implementacioPagina = new InicioProgramaAlgoritmos();
        implementacioPagina.eleccionDeAlgoritmo();
        implementacioPagina.correrAlgoritmo(implementacioPagina);
    }

    @Override
    public void eleccionDeAlgoritmo() {

        String[] options = {"FIFO", "LFU", "LRU", "MFU"};

        AlgoritmoElegido = JOptionPane.showOptionDialog(null, "Retorno de elecccion",
                "Eleccion de algoritmo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        System.out.println(AlgoritmoElegido);

        //iniciacion de variables para algoritmos
        cantidadPaginas = Integer.parseInt(JOptionPane.showInputDialog(null, " Por favor ingrese Cantidad de Páginas"));
        cantidadFrames = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese cantidad de frames"));

        valorPagina = new int[cantidadPaginas];

        //llenado de paginas
        for (int c = 0; c < cantidadPaginas; c++) {
            valorPagina[c] = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese valor de paginas [" + (c + 1) + "]"));
        }

    }

    @Override
    public void iniciarAlgoritmoFIFO() {

        FIFO fifo = new FIFO(paginas, cantidadFrames);
        fifo.run();
        JOptionPane.showMessageDialog(null, String.format("Aciertos: %d\n", fifo.getAciertos()));
    }

    @Override
    public void iniciarAlgoritmoLFU() {

        LFU lfu = new LFU(paginas, cantidadFrames);
        lfu.run();
        JOptionPane.showMessageDialog(null, String.format("Aciertos:%1$,.2f", lfu.getPorcentajeAciertos()));

    }

    @Override
    public void iniciarAlgoritmoLRU() {
        LRU lru = new LRU(paginas, cantidadFrames);
        lru.run();
        JOptionPane.showMessageDialog(null, String.format("Aciertos:%1$,.2f", lru.getPorcentajeAciertos()));
    }

    @Override
    public void iniciarAlgoritmoMFU() {

        MFU mfu = new MFU(paginas, cantidadFrames);
        mfu.run();
        JOptionPane.showMessageDialog(null, String.format("Aciertos:%1$,.2f", mfu.getPorcentajeAciertos()));

    }

    @Override
    public void inicializacionDatos() {

        for (int id : valorPagina) {
            paginas.add(id);
        }

    }

    @Override
    public void impresionResultadoAlgoritmo() {
        ArrayList<String> listaResultados = new ArrayList<String>();
        paginas.forEach((i) -> {
            listaResultados.add(String.valueOf(i));
        });
        JOptionPane.showMessageDialog(null, String.format("Resultado Algoritmo: " + " " + (listaResultados + " ")));
    }

    public void correrAlgoritmo(InicioProgramaAlgoritmos implementacioPagina) {

        for (int i = 0; i < 2; i++) {
            implementacioPagina.inicializacionDatos();

            JOptionPane.showMessageDialog(null, String.format("Simulacion # " + (i + 1)));

            switch (AlgoritmoElegido) {
                case 0:
                    System.out.println("Algoritmo de reemplazo FIFO:");
                    implementacioPagina.iniciarAlgoritmoFIFO();
                    implementacioPagina.impresionResultadoAlgoritmo();
                    break;
                case 1:
                    System.out.println("Algoritmo de reemplazo LFU");
                    implementacioPagina.iniciarAlgoritmoLFU();
                    implementacioPagina.impresionResultadoAlgoritmo();
                    break;
                case 2:
                    System.out.println("Algoritmo de reemplazo LRU");
                    implementacioPagina.iniciarAlgoritmoLRU();
                    implementacioPagina.impresionResultadoAlgoritmo();
                    break;
                case 3:
                    System.out.println("Algoritmo de reemplazo MFU");
                    implementacioPagina.iniciarAlgoritmoMFU();
                    implementacioPagina.impresionResultadoAlgoritmo();
                    break;
            }

        }

    }

}
