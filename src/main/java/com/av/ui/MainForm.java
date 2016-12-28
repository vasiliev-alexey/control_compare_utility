package com.av.ui;

import com.av.Runner;
import com.av.dao.oracle.OracleControlDAO;
import com.av.dao.sqllite.SQLiteDBUtils;
import com.av.domain.Control;
import com.av.service.ControlCompareServiceImpl;
import com.av.service.LoadServiceImpl;
import com.av.service.UnloadFacade;
import com.av.service.exception.FileFormatException;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

//import javax.swing.border.LineBorder;
//import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * Created with IntelliJ IDEA.
 * User: vasiljev.alexey
 * Date: 13.05.14
 * Time: 11:34
 */
public class MainForm extends JFrame {

    JMenu menuUnload, menuLoad, menuMain, menuBar, menuAbout;
    JMenuItem menuItemExit, menuItemLoadFile, menuItemCompare, menuItemAbout;
    JToolBar toolBar = new JToolBar();
    JMenuItem menuItemUnload;
    private JFrame mainFrame;
    private boolean isLoaded = false;
    private
    JProgressBar progressBar = new JProgressBar();
    private JMenuBar jMenuBar;
    // final JPanel progPanel = new JPanel();
    private ErrorControlsPanel p;
    private Logger log = LogManager.getLogger(MainForm.class);

    public MainForm() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1200, 700));
        setVisible(true);
        setTitle(UIConstant.APPTITLE + " " + Runner.APPVER);
        setLayout(new MigLayout());
        //  setLayout(null);
        init();
        mainFrame = this;

        toolBar.setBorderPainted(true);
        add(toolBar/*, "dock south"*/);


        if (ClientMode.getInstance().isDebug()) {
            add(debugPanel(), "cell 0 0 , wrap");
        }


    }

    private void init() {
        initMenu();
    }

    private void initMenu() {
        jMenuBar = new JMenuBar();
        menuMain = new JMenu("Сервис");
        menuBar = new JMenu("");
        menuUnload = new JMenu("Выгрузка");
        menuLoad = new JMenu("Загрузка и сравнение");

        jMenuBar.add(menuMain/*, "growx 110"*/);

        menuItemLoadFile = new JMenuItem("Загрузить базу с контролями");
        menuLoad.add(menuItemLoadFile);
        menuItemLoadFile.addActionListener(new ActionLoadFile());

        menuItemCompare = new JMenuItem("Сравнить контроли");
        menuLoad.add(menuItemCompare);
        menuItemCompare.setEnabled(isLoaded);

        menuItemCompare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar.setIndeterminate(true);
                progressBar.setVisible(true);
                progressBar.setString("Идет обработка запроса, пожалуйста подождите");
                progressBar.setStringPainted(true);
                //   progressBar.setPreferredSize(new Dimension(1000 , 25));
                jMenuBar.add(progressBar);
                toolBar.add(progressBar);
                // int w = progressBar.getParent().getWidth();

                progressBar.setPreferredSize(new Dimension(1200, 30));
                mainFrame.validate();
                mainFrame.repaint();
                RunCompare worker = new RunCompare();
                worker.execute();


            }
        });


        jMenuBar.add(menuBar/*, "growx 30"*/);

        menuItemExit = new JMenuItem("Выход");
        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuItemUnload = new JMenuItem("Выгрузка c EHQE");
        menuItemUnload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                JFileChooser fc = new JFileChooser();
                //   fc.setVisible(true);
                //  fc.showSaveDialog(null) ;
                // fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //   fc.setFileFilter();

                File file;
                int retrival = fc.showSaveDialog(null);


                if (retrival == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    progressBar.setPreferredSize(new Dimension(1200, 25));
                    progressBar.setIndeterminate(true);
                    progressBar.setVisible(true);
                    progressBar.setString("Идет обработка запроса, пожалуйста подождите");
                    progressBar.setStringPainted(true);
                    jMenuBar.add(progressBar);
                    toolBar.add(progressBar);
                    mainFrame.validate();
                    mainFrame.repaint();
                    RunWorker worker = new RunWorker();
                    worker.setFacade(new UnloadFacade());
                    worker.setFile(file);
                    worker.execute();
                }
                ;


            }
        });

        menuUnload.add(menuItemUnload);
        menuMain.add(menuItemExit);
        setJMenuBar(jMenuBar);
        menuUnload.setEnabled(false);
        menuLoad.setEnabled(false);


        if (ClientMode.getInstance().getMode().equals(Mode.ADMIN)) {
            jMenuBar.add(menuUnload, "growx 30");
            menuUnload.setEnabled(true);

        } else {
            jMenuBar.add(menuLoad, "growx 30");
            menuLoad.setEnabled(true);
        }


        JMenu menuAbout = new JMenu("Справка");
        JMenuItem menuItemAbout = new JMenuItem("О программе");
        menuItemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String msg = "Разработано по заказу ГС МОУ  \n"
                        + "Версия " + Runner.APPVER + "\n"
                        + "www.av.ru";

                JOptionPane.showMessageDialog(mainFrame, msg, "О программе ....", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenuItem menuItemHelp = new JMenuItem("Help");

        menuItemHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                java.net.URI uri = null;
                try {
                    uri = new java.net.URI("https://www.asfk-support.ru/jira/browse/KB-660");

                    desktop.browse(uri);


                } catch (URISyntaxException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }
        });


        menuAbout.add(menuItemAbout);
        menuAbout.add(menuItemHelp);


        jMenuBar.add(menuAbout);

    }

    private JPanel debugPanel() {
        JPanel panelbut = new JPanel();

        JButton btn = new JButton("SQLLite");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SQLiteDBUtils.createDB();
            }
        });
        panelbut.add(btn);

        JButton btn2 = new JButton("test");
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Control> list = new OracleControlDAO().findAll();

                SQLiteDBUtils.getInstance().insertConrols(list);

            }
        });


        panelbut.add(btn2);

        JButton btn3 = new JButton("testLoad");
        btn3.addActionListener(new ActionLoadFile());


        panelbut.add(btn3);


        JButton btn4 = new JButton("testCompare");
        btn4.addActionListener(new ActionCompare());


        panelbut.add(btn4);


        return panelbut;
    }


    class RunWorker extends SwingWorker<Object, Object> {

        private UnloadFacade facade;
        private File file;

        void setFacade(UnloadFacade facade) {
            this.facade = facade;
        }

        void setFile(File file) {
            this.file = file;
        }

        protected Object doInBackground() throws Exception {
            facade.unload(file);
            return null;
        }

        @Override
        protected void done() {
            progressBar.setIndeterminate(false);
            progressBar.repaint();
            progressBar.setVisible(false);
            progressBar.setString("Done!");
            mainFrame.validate();
            mainFrame.repaint();
            JOptionPane.showMessageDialog(null, "Выгрузка осуществлена", "Выгрузка....", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    class RunCompare extends SwingWorker<Object, Object> {


        protected Object doInBackground() throws Exception {
            ControlCompareServiceImpl serv = new ControlCompareServiceImpl();

            //   serv.compare();

            Map<Control, Integer> rezult = new HashMap();

            rezult = new ControlCompareServiceImpl().compare();
            log.debug("-------------------- finish compare ------------------");

            log.debug("rezult = " + rezult.size());
            if (p != null) {
                mainFrame.remove(p);
            }
            p = new ErrorControlsPanel(rezult);

            mainFrame.add(p, "cell 0 1");
            mainFrame.validate();
            mainFrame.repaint();
            log.debug("-------------------- comapared ------------------");

            // mainFrame.validate();
            // mainFrame.repaint();
            return null;
        }

        @Override
        protected void done() {
            // mainFrame.add(p, "cell 0 1");
            progressBar.setIndeterminate(false);
            progressBar.repaint();
            progressBar.setVisible(false);


            ;
            JOptionPane.showMessageDialog(null, "Сравнение осуществлено", "Сравнение....", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    class ActionLoadFile implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            JFileChooser openDialog = new JFileChooser();
            openDialog.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {

                    return f.getName().endsWith("zip");
                }

                @Override
                public String getDescription() {
                    return "Архив с БД контролей";
                }
            });

            int result = openDialog.showOpenDialog(mainFrame);


            if (result == JFileChooser.APPROVE_OPTION) {

                File f = openDialog.getSelectedFile();

                LoadServiceImpl service = new LoadServiceImpl();

                try {
                    service.loadDB(f);

                    LoadServiceImpl serv = new LoadServiceImpl();

                    if (!serv.isActualDate()) {

                        JOptionPane.showMessageDialog(mainFrame, "База контролей устарела",
                                "Старье детектед", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    menuItemCompare.setEnabled(true);
                    menuItemLoadFile.setEnabled(false);
                    mainFrame.validate();
                    mainFrame.repaint();
                    JOptionPane.showMessageDialog(mainFrame, "База контролей загружена и актуальна",
                            "Загрузка заввершена", JOptionPane.INFORMATION_MESSAGE);


                } catch (FileFormatException e1) {

                    JOptionPane.showMessageDialog(mainFrame, e1.getMessage(), "Ошибка  обработки Файла"
                            , JOptionPane.ERROR_MESSAGE);
                }


            }


        }
    }

    class ActionCompare implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            RunCompare worker = new RunCompare();
            worker.execute();


        }
    }

}



