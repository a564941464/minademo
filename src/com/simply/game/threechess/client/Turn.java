/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simply.game.threechess.client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author who
 */
public class Turn extends javax.swing.JPanel {
    private BufferedImage bufImg;

    public Turn() {

    }
    
    /**
     * Creates new form Turn
     */
    public Turn(File bufImg)  {
        try{
        this.bufImg =  ImageIO.read(bufImg);
        }catch(Exception e){
            e.printStackTrace();
        }
        initComponents();
    }
    public Dimension getDimension() {
        return new Dimension(bufImg.getWidth(), bufImg.getHeight());
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(bufImg != null){
            g.drawImage(bufImg, 0, 0, null);
        }        
    }

    public BufferedImage getBufImg() {
        return bufImg;
    }

    public void setBufImg(BufferedImage bufImg) {
        this.bufImg = bufImg;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(50, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
