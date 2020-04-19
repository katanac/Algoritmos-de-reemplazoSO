/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.so;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Karen.Pena
 */
public class FIFO {

    String nl = System.getProperty("line.separator");
    private int porcionAciertos = 0;
    private final ArrayList<Integer> paginas;
    private final ArrayList<Integer> Marcos;

    public FIFO(ArrayList<Integer> paginasInsertadas, int framesInsertados) {
        paginas = paginasInsertadas;
        
        Marcos = new ArrayList<>();
        Marcos.add(framesInsertados);

    }

    public void run() {
    
        int evictIndex = 0;
        for (int pagina : paginas) {
            String paginaT = String.format("Pagina de referencia: %d", pagina);
            String evento = "";
            if (Marcos.contains(pagina)) {
                
                porcionAciertos++;
                
            } else {

                if (Marcos.get(evictIndex) == -1) {
                    evento = String.format("pagina insertada: %d", pagina);
                } else {
                    evento = String.format("Página desalojada: %d", Marcos.get(evictIndex));
                }

                Marcos.set(evictIndex, pagina);
                evictIndex++;

                if (evictIndex >= 3) {
                    evictIndex = 0;
                }

                evictIndex = 0;
            }

            String frameImage = impresionDeMarcos();
            JOptionPane.showMessageDialog(null, paginaT + nl + evento + nl + frameImage + nl);

        }
    }

    public int getAciertos() {
        return porcionAciertos;
    }

    private String impresionDeMarcos() {
        String page = "";

        for (Integer p : Marcos) {

            page = String.format("Marcos de página:  %d", p);
        }
        return page;
    }

}
