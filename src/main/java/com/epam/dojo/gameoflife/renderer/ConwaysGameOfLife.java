package com.epam.dojo.gameoflife.renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ConwaysGameOfLife extends JFrame implements ActionListener {

    private JMenuItem mi_file_options, mi_file_exit;
    private JMenuItem mi_game_autofill, mi_game_play, mi_game_stop, mi_game_reset;

    private GameBoard gameBoard;
    private Thread game;

    public ConwaysGameOfLife() {

        // Setup game board
        gameBoard = new GameBoard();


        JScrollPane scroller = new JScrollPane(gameBoard);
        setPreferredSize(new Dimension(450, 110));
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scroller, BorderLayout.CENTER);

        add(scroller);
        // Setup menu
        JMenuBar mb_menu = new JMenuBar();
        setJMenuBar(mb_menu);
        JMenu m_file = new JMenu("File");
        mb_menu.add(m_file);
        JMenu m_game = new JMenu("Game");
        mb_menu.add(m_game);
        JMenuItem mi_file_open = new JMenuItem("Open");
        mi_file_open.addActionListener(new OpenLifeFile(this));
        m_file.add(mi_file_open);
        mi_file_options = new JMenuItem("Options");
        mi_file_options.addActionListener(this);
        mi_file_exit = new JMenuItem("Exit");
        mi_file_exit.addActionListener(this);
        m_file.add(mi_file_options);
        m_file.add(new JSeparator());
        m_file.add(mi_file_exit);
        mi_game_autofill = new JMenuItem("Autofill");
        mi_game_autofill.addActionListener(this);
        mi_game_play = new JMenuItem("Play");
        mi_game_play.addActionListener(this);
        mi_game_stop = new JMenuItem("Stop");
        mi_game_stop.setEnabled(false);
        mi_game_stop.addActionListener(this);
        mi_game_reset = new JMenuItem("Reset");
        mi_game_reset.addActionListener(this);
        m_game.add(mi_game_autofill);
        m_game.add(new JSeparator());
        m_game.add(mi_game_play);
        m_game.add(mi_game_stop);
        m_game.add(mi_game_reset);

    }

    public void setGameBeingPlayed(boolean isBeingPlayed) {
        if (isBeingPlayed) {
            mi_game_play.setEnabled(false);
            mi_game_stop.setEnabled(true);
            game = new Thread(gameBoard);
            game.start();
        } else {
            mi_game_play.setEnabled(true);
            mi_game_stop.setEnabled(false);
            game.interrupt();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(mi_file_exit)) {
            // Exit the game
            System.exit(0);
        } else if (ae.getSource().equals(mi_file_options)) {
            // Put up an options panel to change the number of moves per second
            final JFrame f_options = new JFrame();
            f_options.setTitle("Options");
            f_options.setSize(300,60);
            f_options.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - f_options.getWidth())/2,
                    (Toolkit.getDefaultToolkit().getScreenSize().height - f_options.getHeight())/2);
            f_options.setResizable(false);
            JPanel p_options = new JPanel();
            p_options.setOpaque(false);
            f_options.add(p_options);
            p_options.add(new JLabel("Number of moves per second:"));
            Integer[] secondOptions = {1,2,3,4,5,10,15,20};
            final JComboBox cb_seconds = new JComboBox(secondOptions);
            p_options.add(cb_seconds);
            cb_seconds.setSelectedItem(gameBoard.getMovesPerSecond());
            cb_seconds.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae) {
                    gameBoard.setMovesPerSecond( (Integer)cb_seconds.getSelectedItem());
                    f_options.dispose();
                }
            });
            f_options.setVisible(true);
        } else if (ae.getSource().equals(mi_game_autofill)) {
            final JFrame f_autoFill = new JFrame();
            f_autoFill.setTitle("Autofill");
            f_autoFill.setSize(360, 60);
            f_autoFill.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - f_autoFill.getWidth())/2,
                    (Toolkit.getDefaultToolkit().getScreenSize().height - f_autoFill.getHeight())/2);
            f_autoFill.setResizable(false);
            JPanel p_autoFill = new JPanel();
            p_autoFill.setOpaque(false);
            f_autoFill.add(p_autoFill);
            p_autoFill.add(new JLabel("What percentage should be filled? "));
            Object[] percentageOptions = {"Select",5,10,15,20,25,30,40,50,60,70,80,90,95};
            final JComboBox cb_percent = new JComboBox(percentageOptions);
            p_autoFill.add(cb_percent);
            cb_percent.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cb_percent.getSelectedIndex() > 0) {
                        gameBoard.resetBoard();
                        gameBoard.randomlyFillBoard((Integer)cb_percent.getSelectedItem());
                        f_autoFill.dispose();
                    }
                }
            });
            f_autoFill.setVisible(true);
        } else if (ae.getSource().equals(mi_game_reset)) {
            gameBoard.resetBoard();
            gameBoard.repaint();
        } else if (ae.getSource().equals(mi_game_play)) {
            setGameBeingPlayed(true);
        } else if (ae.getSource().equals(mi_game_stop)) {
            setGameBeingPlayed(false);
        }
    }

    public void updateGameSize(int width, int height) {
        this.gameBoard.setBoardSize(new Dimension(width, height));
    }

    public void setPoints(List<Point> pointList) {
        this.gameBoard.setPoints(pointList);
    }
}