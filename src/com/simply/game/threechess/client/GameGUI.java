/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simply.game.threechess.client;

import static com.simply.game.threechess.bean.Constants.*;
import com.simply.game.threechess.bean.GoInfo;
import com.simply.game.threechess.bean.Info;
import com.simply.game.threechess.bean.SelectInfo;
import com.simply.game.threechess.bean.User;
import gameboard2d.GameBoard2D;
import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author who
 */
public class GameGUI extends javax.swing.JFrame {

    /**
     * Creates new form GameGUI
     */
    private GameBoard2D gb2d;
    private String my_turn;//
    private String cur_turn;
    private String turnicon; //棋手图标
    private String[] board;
    private User u;
    private Communicator c;
    private int desk_num;
    private int desk_position;
    
    private int select_id; //选择的id

    private static final String WHITE_PIECE = "white";//棋子图标
    private static final String BLACK_PIECE = "black";//棋子图标
    private static final String EMPTY = "empty";// 空白 
    private static final String S_WHITE_PIECE = "swhite";//棋子图标
    private static final String S_BLACK_PIECE = "sblack";//棋子图标
    
    
    private String status = UNSTARTED;//游戏状态 

    public GameGUI(Communicator c, String turnicon) {
        this.c = c;
        this.turnicon = turnicon;
        initComponents();
        board = new String[38];

        try {
            // First you need to create a GameBoard2D object.
            gb2d = new GameBoard2D();

            // Next, set up the board.
            // The first argument is the image of the board.
            // The second argument is the image with cached areas, look at the
            // file "images/ttt-board-cache.png" to see what I mean. In this image,
            // every cached area are filled with a uniq color. A cached area is
            // nothing more than a box of the board.
            // The two images (board and cache) must be of the same size.
            gb2d.setBoard(new File("images/ttt-board.png"), new File("images/ttt-board-cache.png"));

            // Set up all the boxes of the board.
            // For every box you give :
            //   - an ID, that is an integer
            //   - the color of the cached area for this box
            //   - the reference point of the box from which the library
            //     will draw a piece (this point will be the top left corner
            //     of the drawed piece).
            gb2d.addBox(10, new Color(10, 10, 10), new Point(25, 25));
            gb2d.addBox(11, new Color(20, 20, 20), new Point(275, 25));
            gb2d.addBox(12, new Color(30, 30, 30), new Point(525, 25));

            gb2d.addBox(20, new Color(40, 40, 40), new Point(125, 125));
            gb2d.addBox(21, new Color(50, 50, 50), new Point(275, 125));
            gb2d.addBox(22, new Color(60, 60, 60), new Point(425, 125));

            gb2d.addBox(30, new Color(70, 70, 70), new Point(225, 225));
            gb2d.addBox(31, new Color(80, 80, 80), new Point(275, 225));
            gb2d.addBox(32, new Color(90, 90, 90), new Point(325, 225));

            gb2d.addBox(17, new Color(100, 100, 100), new Point(25, 275));
            gb2d.addBox(27, new Color(110, 110, 110), new Point(125, 275));
            gb2d.addBox(37, new Color(120, 120, 120), new Point(225, 275));

            gb2d.addBox(33, new Color(130, 130, 130), new Point(325, 275));
            gb2d.addBox(23, new Color(140, 140, 140), new Point(425, 275));
            gb2d.addBox(13, new Color(150, 150, 150), new Point(525, 275));

            gb2d.addBox(36, new Color(160, 160, 160), new Point(225, 325));
            gb2d.addBox(35, new Color(170, 170, 170), new Point(275, 325));
            gb2d.addBox(34, new Color(180, 180, 180), new Point(325, 325));

            gb2d.addBox(26, new Color(190, 190, 190), new Point(125, 425));
            gb2d.addBox(25, new Color(200, 200, 200), new Point(275, 425));
            gb2d.addBox(24, new Color(210, 210, 210), new Point(425, 425));

            gb2d.addBox(16, new Color(220, 220, 220), new Point(25, 525));
            gb2d.addBox(15, new Color(230, 230, 230), new Point(275, 525));
            gb2d.addBox(14, new Color(240, 240, 240), new Point(525, 525));

            // Add the pieces of the game.
            // For every piece you give:
            //   - an ID, that is the name of the piece
            //   - the image of the piece
            // Note that a "piece" is simply a thing that will
            // be drawed on the board.
            gb2d.addPiece(WHITE_PIECE, new File("images/white.png"));
            gb2d.addPiece(BLACK_PIECE, new File("images/black.png"));
            gb2d.addPiece(EMPTY, new File("images/empty.png"));
            gb2d.addPiece(S_WHITE_PIECE, new File("images/swhite.png"));
            gb2d.addPiece(S_BLACK_PIECE, new File("images/sblack.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        // To finish the initialization, you add the GameBoard2D object in a panel
        // of your GUI.
        // This panel should be of the same size than the board image.
        jPanel1.add(gb2d);

        newGameActionPerformed(null);

    }

    private void clearBoard() {
        for (int i = 0; i < board.length; i++) {
            board[i] = "";
        }
    }

    private void newGameActionPerformed(java.awt.event.ActionEvent evt) {
        
        clearBoard();

        // That method draw the board on the screen, without anything else, destroying
        // any previously rendering.
        gb2d.drawBoard();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        deskButton1 = new com.simply.game.threechess.client.DeskButton();
        deskButton2 = new com.simply.game.threechess.client.DeskButton();
        deskButton3 = new com.simply.game.threechess.client.DeskButton();
        deskButton4 = new com.simply.game.threechess.client.DeskButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        deskButton5 = new com.simply.game.threechess.client.DeskButton();
        jLabel7 = new javax.swing.JLabel();
        File buffile;
        if(("WHITE_TURN").equals(this.turnicon)){
            buffile  = new File("images/white.png");
        }else{
            buffile  = new File("images/black.png");
        }
        turn1 = new com.simply.game.threechess.client.Turn(buffile);
        turn2 = new com.simply.game.threechess.client.Turn();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                gameGUIClosing(evt);
            }
        });

        jTextPane1.setBackground(new java.awt.Color(51, 51, 0));
        jTextPane1.setEditable(false);
        jTextPane1.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(jTextPane1);

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setText("发送");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(610, 610));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
        );

        deskButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deskButton1.setText("开始");
        deskButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deskButton1ActionPerformed(evt);
            }
        });

        deskButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deskButton2.setText("求和");
        deskButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deskButton2ActionPerformed(evt);
            }
        });

        deskButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deskButton3.setText("悔棋");
        deskButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deskButton3ActionPerformed(evt);
            }
        });

        deskButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deskButton4.setText("认输");

        jLabel1.setText("桌台号：");

        jLabel2.setText("jLabel2");

        jLabel3.setText("我：");

        jLabel4.setText("jLabel4");

        jLabel5.setText("对手：");

        jLabel6.setText("jLabel6");

        deskButton5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deskButton5.setText("退出");
        deskButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deskButton5ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 102));

        turn1.setPreferredSize(new java.awt.Dimension(50, 50));

        javax.swing.GroupLayout turn1Layout = new javax.swing.GroupLayout(turn1);
        turn1.setLayout(turn1Layout);
        turn1Layout.setHorizontalGroup(
            turn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        turn1Layout.setVerticalGroup(
            turn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        turn2.setPreferredSize(new java.awt.Dimension(50, 50));

        javax.swing.GroupLayout turn2Layout = new javax.swing.GroupLayout(turn2);
        turn2.setLayout(turn2Layout);
        turn2Layout.setHorizontalGroup(
            turn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        turn2Layout.setVerticalGroup(
            turn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel8.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N

        jButton2.setText("重新开始");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(deskButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deskButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(deskButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(deskButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(deskButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(turn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 21, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(turn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(23, 23, 23))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(deskButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(deskButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(deskButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(deskButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(deskButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(turn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)))
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(turn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

 

    private void checkWin() {
        // drawPieceOnTop is like drawPiece, but it preserves the background.
        /*if (!board[0].isEmpty() && board[0].equals(board[1]) && board[0].equals(board[2])) {
            gb2d.drawPieceOnTop("stroke h", 0);
        } else if (!board[3].isEmpty() && board[3].equals(board[4]) && board[3].equals(board[5])) {
            gb2d.drawPieceOnTop("stroke h", 3);
        } else if (!board[6].isEmpty() && board[6].equals(board[7]) && board[6].equals(board[8])) {
            gb2d.drawPieceOnTop("stroke h", 6);
        } else if (!board[0].isEmpty() && board[0].equals(board[3]) && board[0].equals(board[6])) {
            gb2d.drawPieceOnTop("stroke v", 0);
        } else if (!board[1].isEmpty() && board[1].equals(board[4]) && board[1].equals(board[7])) {
            gb2d.drawPieceOnTop("stroke v", 1);
        } else if (!board[2].isEmpty() && board[2].equals(board[5]) && board[2].equals(board[8])) {
            gb2d.drawPieceOnTop("stroke v", 2);
        } else if (!board[0].isEmpty() && board[0].equals(board[4]) && board[0].equals(board[8])) {
            gb2d.drawPieceOnTop("stroke d2", 0);
        } else if (!board[2].isEmpty() && board[2].equals(board[4]) && board[2].equals(board[6])) {
            gb2d.drawPieceOnTop("stroke d1", 0);
        }*/
    }

    private void nextTurn() {
       // turn = !turn;
    }
    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
        if (this.jTextField2.hasFocus() && !this.jTextField2.getText().equals("")) {
            String msg = jTextField2.getText();
            Info req = new Info();
            req.setAction(CHAT_MSG);
            req.setChat_msg(u.getName() + "说:" + msg);
            req.setDesk_num(this.desk_num);
            this.c.send(req);
        }
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        String msg = jTextField2.getText();
        Info req = new Info();
        req.setAction(CHAT_MSG);
        req.setChat_msg(u.getName() + "说:" + msg);
        req.setDesk_num(this.desk_num);
        this.c.send(req);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void deskButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deskButton1ActionPerformed
        // TODO add your handling code here:
        Info req = new Info();
        req.setAction(START);
        req.setDesk_num(this.desk_num);
        req.setDesk_position(this.desk_position);
        this.c.send(req);
    }//GEN-LAST:event_deskButton1ActionPerformed

    private void deskButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deskButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deskButton2ActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        // I have added a mouse listener to the panel which contain
        // the GameBoard2D object.
        // When this panel is clicked, we ask the library for the box ID.
        int id = gb2d.getBoxId(evt.getPoint());
//        if (board[id].isEmpty()) {
//            return;
//        }
        System.out.println("cur_status:" + this.status + ",click_id: "+ id + ",from_id:" +  select_id + ",to_id:"+ id );
        if(UNSTARTED.equals(this.status)){
            
        } else if("PUTTING".equals(this.status) && this.my_turn.equals(this.cur_turn)){
            Info req = new Info();
            req.setAction(PUT);
            req.setId(id);
            req.setDesk_num(this.desk_num);
            req.setCur_turn(this.cur_turn);
            this.c.send(req);
        }else if(GETTING.equals(this.status) && this.my_turn.equals(this.cur_turn)){
            Info req = new Info();
            req.setAction(GET);
            req.setId(id);
            req.setDesk_num(this.desk_num);
            req.setCur_turn(this.cur_turn);
            this.c.send(req);
        } else if(SELECTTING.equals(this.status) && this.my_turn.equals(this.cur_turn) && id != 0){
            SelectInfo req_select = new SelectInfo();
            req_select.setAction(SELECT);
            req_select.setS_select_id(id);
            req_select.setS_desk_num(this.desk_num);
            req_select.setS_cur_turn(this.cur_turn);
            this.c.send(req_select);
        }else if(GOING.equals(this.status) && this.my_turn.equals(this.cur_turn) && this.select_id!=0){
//            Info req = new Info();
//            req.setAction(GO);
//            req.setDesk_num(this.desk_num);
//            req.setCur_turn(this.cur_turn);
//            req.setFrom_id(this.select_id);
//            req.setTo_id(id);
//            System.out.println("from_id:"+ select_id + ",to_id:"+ id);
//            this.c.send(req);
              GoInfo g_req = new GoInfo();
              g_req.setG_desk_num(this.desk_num);
              g_req.setG_cur_turn(this.cur_turn);
              g_req.setG_from_id(this.select_id);
              g_req.setG_to_id(id);
              this.c.send(g_req);
        }else if(GOING.equals(this.status) && this.my_turn.equals(this.cur_turn) && this.select_id == 0){
            SelectInfo req_select = new SelectInfo();
            req_select.setAction(SELECT);
            req_select.setS_select_id(id);
            req_select.setS_desk_num(this.desk_num);
            req_select.setS_cur_turn(this.cur_turn);
            this.c.send(req_select);
        }
    }//GEN-LAST:event_jPanel1MouseClicked
    public void drawPiece(String turn, int id){
        // To draw a piece, tell the library the name of the piece and
        // the box id.
        if (WHITE_TURN.equals(turn)) {
            gb2d.drawPiece(WHITE_PIECE, id);
            this.board[id] = WHITE_PIECE;
        } else {
            gb2d.drawPiece(BLACK_PIECE, id);
            this.board[id] = BLACK_PIECE;
        }
    }
    /**
     * 清掉指定id处
     * @param id 
     */
    public void clearPiece(int id){
        // To draw a piece, tell the library the name of the piece and
        // the box id.
        
        gb2d.clearPiece(EMPTY, id);
        this.board[id] = "";
        
    }
    /**
     * 选子操作
     * @param id 
     */
    public void select(int id){
        System.out.println("board[id]:"+board[id]);
        if (board[id].equals(GameGUI.WHITE_PIECE)) {
            gb2d.drawPiece(GameGUI.S_WHITE_PIECE, id);
            board[id] = GameGUI.S_WHITE_PIECE;
            this.select_id = id; 
        } else if (board[id].equals(GameGUI.BLACK_PIECE)) {
            gb2d.drawPiece(GameGUI.S_BLACK_PIECE, id);
            board[id] = GameGUI.S_BLACK_PIECE;
            this.select_id = id;
        }
    }
   /**
    * 取消选择操作
    * @param id 
    */ 
   public void unselect(int id){
        if (board[id].equals(GameGUI.S_WHITE_PIECE)) {
            gb2d.drawPiece(GameGUI.WHITE_PIECE, id);
            board[id] = GameGUI.WHITE_PIECE;
        } else if (board[id].equals(GameGUI.S_BLACK_PIECE)) {
            gb2d.drawPiece(GameGUI.BLACK_PIECE, id);
            board[id] = GameGUI.BLACK_PIECE;
        }
    }
   /**
    * 走棋操作
    * @param from_id
    * @param to_id 
    */
   public void go(int from_id, int to_id){
        this.clearPiece(from_id);
        if (WHITE_TURN.equals(this.cur_turn)) {
            gb2d.drawPiece(WHITE_PIECE, to_id);
            this.board[to_id] = WHITE_PIECE;
        } else {
            gb2d.drawPiece(BLACK_PIECE, to_id);
            this.board[to_id] = BLACK_PIECE;
        }
    }
   
    private void gameGUIClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_gameGUIClosing
        // TODO add your handling code here:
        Info req = new Info();
        req.setAction(QUIT_DESK);
        req.setDesk_num(this.desk_num);
        req.setDesk_position(this.desk_position);
        this.c.send(req);
        this.dispose();
    }//GEN-LAST:event_gameGUIClosing

    private void deskButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deskButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deskButton5ActionPerformed

    private void deskButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deskButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deskButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GameGUI(null,null).setVisible(true);
            }
        });
    }

    public JLabel getjLabel7() {
        return jLabel7;
    }

    public String getCur_turn() {
        return cur_turn;
    }

    public void setCur_turn(String cur_turn) {
        this.cur_turn = cur_turn;
    }

    public String getMy_turn() {
        return my_turn;
    }

    public int getSelect_id() {
        return select_id;
    }

    public void setSelect_id(int select_id) {
        this.select_id = select_id;
    }

    public void setMy_turn(String my_turn) {
        this.my_turn = my_turn;
    }

    public JLabel getjLabel8() {
        return jLabel8;
    }

    public void setjLabel8(JLabel jLabel8) {
        this.jLabel8 = jLabel8;
    }

    public void setjLabel7(JLabel jLabel7) {
        this.jLabel7 = jLabel7;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public void setjPanel1(JPanel jPanel1) {
        this.jPanel1 = jPanel1;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JTextField getjTextField2() {
        return jTextField2;
    }

    public void setjTextField2(JTextField jTextField2) {
        this.jTextField2 = jTextField2;
    }

    public JTextPane getjTextPane1() {
        return jTextPane1;
    }

    public void setjTextPane1(JTextPane jTextPane1) {
        this.jTextPane1 = jTextPane1;
    }

    public int getDesk_position() {
        return desk_position;
    }

    public void setDesk_position(int desk_position) {
        this.desk_position = desk_position;
    }

    public DeskButton getDeskButton1() {
        return deskButton1;
    }

    public void setDeskButton1(DeskButton deskButton1) {
        this.deskButton1 = deskButton1;
    }

    public int getDesk_num() {
        return desk_num;
    }

    public void setDesk_num(int desk_num) {
        this.desk_num = desk_num;
    }

    public String[] getBoard() {
        return board;
    }

    public void setBoard(String[] board) {
        this.board = board;
    }

    public Communicator getC() {
        return c;
    }

    public void setC(Communicator c) {
        this.c = c;
    }

    public DeskButton getDeskButton2() {
        return deskButton2;
    }

    public void setDeskButton2(DeskButton deskButton2) {
        this.deskButton2 = deskButton2;
    }

    public DeskButton getDeskButton3() {
        return deskButton3;
    }

    public void setDeskButton3(DeskButton deskButton3) {
        this.deskButton3 = deskButton3;
    }

    public DeskButton getDeskButton4() {
        return deskButton4;
    }

    public void setDeskButton4(DeskButton deskButton4) {
        this.deskButton4 = deskButton4;
    }

    public GameBoard2D getGb2d() {
        return gb2d;
    }

    public void setGb2d(GameBoard2D gb2d) {
        this.gb2d = gb2d;
    }

    public JButton getjButton1() {
        return jButton1;
    }

    public void setjButton1(JButton jButton1) {
        this.jButton1 = jButton1;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JLabel getjLabel2() {
        return jLabel2;
    }

    public void setjLabel2(JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }

    public JLabel getjLabel3() {
        return jLabel3;
    }

    public void setjLabel3(JLabel jLabel3) {
        this.jLabel3 = jLabel3;
    }

    public JLabel getjLabel4() {
        return jLabel4;
    }

    public void setjLabel4(JLabel jLabel4) {
        this.jLabel4 = jLabel4;
    }

    public JLabel getjLabel5() {
        return jLabel5;
    }

    public void setjLabel5(JLabel jLabel5) {
        this.jLabel5 = jLabel5;
    }

    public JLabel getjLabel6() {
        return jLabel6;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public DeskButton getDeskButton5() {
        return deskButton5;
    }

    public Turn getTurn1() {
        return turn1;
    }

    public void setTurn1(Turn turn1) {
        this.turn1 = turn1;
    }

    public String getTurnicon() {
        return turnicon;
    }

    public void setTurnicon(String turnicon) {
        this.turnicon = turnicon;
    }

    public void setDeskButton5(DeskButton deskButton5) {
        this.deskButton5 = deskButton5;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Turn getTurn2() {
        return turn2;
    }

    public void setTurn2(Turn turn2) {
        this.turn2 = turn2;
    }

    public void setjLabel6(JLabel jLabel6) {
        this.jLabel6 = jLabel6;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.simply.game.threechess.client.DeskButton deskButton1;
    private com.simply.game.threechess.client.DeskButton deskButton2;
    private com.simply.game.threechess.client.DeskButton deskButton3;
    private com.simply.game.threechess.client.DeskButton deskButton4;
    private com.simply.game.threechess.client.DeskButton deskButton5;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextPane jTextPane1;
    private com.simply.game.threechess.client.Turn turn1;
    private com.simply.game.threechess.client.Turn turn2;
    // End of variables declaration//GEN-END:variables
}
