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
public class MFU {

    String nl = System.getProperty("line.separator");
    private ArrayList<Integer> paginas;
    private int porcentajeAcierto, tamaño_marcos;
    private Page[] marcos;

    public MFU(ArrayList<Integer> p, int framesInsertados) {
        paginas = p;
        porcentajeAcierto = 0;
        marcos = new Page[framesInsertados];
        tamaño_marcos = framesInsertados;
        for (int i = 0; i < tamaño_marcos; i++) {
            marcos[i] = new Page(-1, 0);
        }
    }

    public void run() {
        StringBuffer buffer = new StringBuffer();
        for (int pagina : paginas) {

            String paginaT = String.format("Pagina de referencia: %d", pagina);

            boolean found = searchInFrames(pagina);
            String event = "";
            if (found) {
                porcentajeAcierto++;
            } else {
                int evictIndex = getEvictIndex();
                int pageToEvict = marcos[evictIndex].data;
                Page newPage = new Page(pagina, 0);
                marcos[evictIndex] = newPage;

                if (pageToEvict == -1) {
                    event = String.format("Página insertada: %d", pagina);
                } else {
                    event = String.format("Página desalojada: %d", pageToEvict);
                }
            }
            String frameImage = printFrame();

            JOptionPane.showMessageDialog(null, paginaT + nl + event + nl + frameImage + nl);

            updateUseCount();
  
            buffer.setLength(0);
        }
    }

    public double getPorcentajeAciertos() {
        return ((porcentajeAcierto + 0.0) / paginas.size());
    }

    private boolean searchInFrames(int i) {
        for (Page j : marcos) {
            if (j.data == i) {
                return true;
            }
        }
        return false;
    }

    private void updateUseCount() {
        for (Page j : marcos) {
            j.useCount++;
        }
    }

    private int getEvictIndex() {
        int index = 0, max = 0;
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
        buffer.append("Marcos de página: ");
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
