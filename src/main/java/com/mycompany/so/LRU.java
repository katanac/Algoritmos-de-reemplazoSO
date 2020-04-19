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
public class LRU {

    String nl = System.getProperty("line.separator");
    private ArrayList<Integer> paginas;
    private int porcentajeAciertos, tamaño_marcos;
    private Page[] marcos;

    public LRU(ArrayList<Integer> paginasInsertadas, int marcosInsertados) {
        paginas = paginasInsertadas;
        porcentajeAciertos = 0;
        marcos = new Page[marcosInsertados];
        tamaño_marcos = marcosInsertados;
        for (int i = 0; i < tamaño_marcos; i++) {
            marcos[i] = new Page(-1, 0);
        }
    }
    //1            100 - 1             //99
    //1,2          100 - 1, 100 - 2    //99, 98
    //1,2,3        100 - 1, 100 - 2, 100 - 3   //99, 98, 97 
    //1,2,3,4      100 - 1, 100 - 2, 100 - 3, 100 - 4   //99, 98, 97, 96 
    //1,2,3,4,5    100 - 1, 100 - 2, 100 - 3 , 100 - 4, 100 -5 //99, 98, 97, 96, 95 

    public void run() {
        StringBuffer buffer = new StringBuffer();
        int evictCounter = 0;
        for (int pagina : paginas) {
            buffer.append(String.format("Página de referencia: %d", pagina));
            buffer.append(String.format("%5s", ""));

            String paginaT = String.format("Pagina de referencia: %d", pagina);

            boolean found = searchInFrames(pagina);
            String event = "";
            if (found) {
                porcentajeAciertos++;
                int index = getFrameIndex(pagina);
                marcos[index].useCount = 100 - evictCounter;
            } else {
                int evictIndex = getEvictIndex();
                int pageToEvict = marcos[evictIndex].data;
                Page newPage = new Page(pagina, 100 - evictCounter);
                marcos[evictIndex] = newPage;
                if (pageToEvict == -1) {
                    event = String.format("Página insertada: %d", pagina);
                } else {
                    event = String.format("Página desalojada: %d", pageToEvict);
                }
            }
            evictCounter++;
            String frameImage = printFrame();      
            buffer.setLength(0);

            JOptionPane.showMessageDialog(null, paginaT + nl + event + nl + frameImage + nl);
        }
    }

    public double getPorcentajeAciertos() {
        return ((porcentajeAciertos + 0.0) / paginas.size());
    }

    private boolean searchInFrames(int i) {
        for (Page j : marcos) {
            if (j.data == i) {
                return true;
            }
        }
        return false;
    }

    private int getFrameIndex(int i) {
        for (int j = 0; j < tamaño_marcos; j++) {
            if (marcos[j].data == i) {
                return j;
            }
        }
        return -1;
    }

    /**
     * private void updateUseCount() { for(Page j : frames) { j.useCount++; } }*
     */
    //As a process is added update its counter
    //When the existing process is to be inserted, Increase its counter
    //When new Process comes in and its not existing 
    //then replace it with least counter
    private int getEvictIndex() {
        int index = 0, max = Integer.MIN_VALUE;
        for (int i = 0; i < tamaño_marcos; i++) {
            if (marcos[i].data == -1) {
                return i;
            }

            if (max < marcos[i].useCount) {
                max = marcos[i].useCount;
                index = i;
            }
        }
        return index;
    }

    private String printFrame() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Marcos de página:");
        for (Page p : marcos) {
            buffer.append(String.format("%2d", p.data));
            buffer.append(' ');
        }
        return buffer.toString();
    }

    class Page {

        int data;
        int useCount;

        Page(int data, int useCount) {
            this.data = data;
            this.useCount = useCount;
        }
    }
}
