package com.av.ui;

import com.av.domain.Control;
import com.av.domain.ControlLine;
import com.av.ui.renders.DateTableCellRender;
import com.av.utils.ImageIconUtils;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vasiljev.alexey
 * Date: 22.05.14
 * Time: 12:40
 */
public class ErrorControlsPanel extends JPanel {
    JPanel botom = new JPanel();
    private Logger log = LogManager.getLogger(ErrorControlsPanel.class);
    private JScrollPane leftPanel;
    private DateTableCellRender dateTableCellRender = new DateTableCellRender();

    public ErrorControlsPanel(Map<Control, Integer> controls) {

        super();


        init();
        final SimpleModel model = new SimpleModel(controls);

        log.debug("model count = " + model.getRowCount());

        final JTable table = new JTable(model);


        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(27);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        final JTextArea text = new JTextArea();

        text.setLineWrap(true);

        ;

        TableRowSorter<TableModel> sorter = new TableRowSorter(model);
        table.setRowSorter(sorter);

        table.getColumnModel().getColumn(6).setCellRenderer(dateTableCellRender);
        table.getColumnModel().getColumn(7).setCellRenderer(dateTableCellRender);


        // table.setDefaultRenderer(ImageTableCell.class , new ImageTableCellRenderer());
        JSplitPane splitMain = new JSplitPane();
        splitMain.setOneTouchExpandable(true);
        splitMain.setDividerSize(5);
        splitMain.setSize(new Dimension(1000, 1000));
        splitMain.setDividerLocation(0.25);


        JSplitPane vertSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);

        leftPanel = new JScrollPane();
        leftPanel.setPreferredSize(new Dimension(100, 800));
        final JPanel topPanel = new JPanel();

        topPanel.add(new JLabel(""));

        topPanel.setLayout(new MigLayout("wrap 2", "[][][grow, fill]", "[][][][grow, fill]"));

        topPanel.setPreferredSize(new Dimension(200, 200));

        vertSplit.setTopComponent(topPanel);
        // botom.setLayout(new MigLayout("wrap 1, debug", "[][][grow, fill]", "[][][][grow, fill]"));
        botom.add(new JLabel(""));
        botom.setAutoscrolls(true);
        vertSplit.setBottomComponent(botom);
        vertSplit.setDividerSize(5);
        vertSplit.setOneTouchExpandable(true);
        vertSplit.setDividerLocation(0.39);


        JScrollPane pn = new JScrollPane(table);


        splitMain.setTopComponent(pn);
        splitMain.setBottomComponent(vertSplit);
        splitMain.setPreferredSize(new Dimension(getWidth() - 20, 600));


        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {


                if (table.getSelectedRow() != -1) {

                    botom.remove(botom.getComponent(0));
                    botom.add(new JLabel(""));


                    int r = table.getSelectedRow();
                    int c = table.getSelectedColumn();

                    int t = table.convertRowIndexToModel(r);

                    Control ct = model.getControlAt(t);

                    if (ct != null) {
                        text.setText(model.getControlAt(t).toString());
                    }
                    topPanel.setLayout(new MigLayout());

                    topPanel.remove(topPanel.getComponent(0));

                    topPanel.setPreferredSize(getParent().getPreferredSize());

                    topPanel.add(getLinePanel(ct), "dock west , growy");


                    topPanel.getParent().getParent().validate();
                    topPanel.getParent().getParent().repaint();


                }

            }
        });


        add(splitMain);


    }


    private void init() {

        setSize(new Dimension(1200, 800));


    }


    private JScrollPane getLinePanel(final Control control) {


        final JTable lineTable = new JTable();


        final LineModel lineModel = new LineModel(control);

        lineTable.setModel(lineModel);

        TableRowSorter<TableModel> sorter = new TableRowSorter(lineModel);
        lineTable.setRowSorter(sorter);
        lineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        lineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lineTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        lineTable.getColumnModel().getColumn(0).setPreferredWidth(27);
        lineTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        lineTable.getColumnModel().getColumn(2).setPreferredWidth(30);
        lineTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        lineTable.getColumnModel().getColumn(4).setPreferredWidth(200);
        lineTable.getColumnModel().getColumn(5).setPreferredWidth(200);
        lineTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        lineTable.getColumnModel().getColumn(7).setPreferredWidth(100);

        lineTable.getColumnModel().getColumn(16).setCellRenderer(dateTableCellRender);
        lineTable.getColumnModel().getColumn(17).setCellRenderer(dateTableCellRender);
        // lineTable.setPreferredSize(new Dimension(1000, 300));
        JScrollPane sp = new JScrollPane(lineTable);
        sp.setPreferredSize(getParent().getPreferredSize());


        lineTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                int r = lineTable.getSelectedRow();


                int t = lineTable.convertRowIndexToModel(r);


                Control c = lineModel.getCtr();


                String str = c.getErrStr(lineModel.getLineNum(t));
                botom.remove(botom.getComponent(0));

                JEditorPane editor = new JEditorPane("text/html",
                        str);
                editor.setEditable(false);

                JScrollPane pnl = new JScrollPane(editor);
                // pnl.setPreferredSize(new Dimension(200 , 300));

                botom.add(pnl);

                pnl.setPreferredSize(new Dimension(botom.getWidth() - 20, botom.getHeight() - 10));

                //  pnl.setPreferredSize(getParent().getPreferredSize());


                validate();
                repaint();
                //   botom.add(new JLabel(control.))

            }
        });

        // sp.setMinimumSize(new Dimension(925 , 300));
        //  sp.setMaximumSize(new Dimension(1000 , 800));
        //  sp.setPreferredSize(new Dimension(925 , 300));
        // sp.add(lineTable);
        // lineTable.setBorder(new LineBorder(Color.BLACK));
        //  panel.add(sp);


        // panel.setBorder(new LineBorder(Color.BLUE));


        return sp;

    }


    class SimpleModel extends AbstractTableModel {
        private static final int COLUMN_COUNT = 8;
        private ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
        private ImageIcon iconPlus;
        private ImageIcon iconMinus;

        public SimpleModel(Map<Control, Integer> map) {


            iconPlus = ImageIconUtils.getImigeIcon("img/add2.gif", ErrorControlsPanel.class);
            iconMinus = ImageIconUtils.getImigeIcon("img/del.gif", ErrorControlsPanel.class);

            for (Map.Entry<Control, Integer> entry : map.entrySet()) {

                ArrayList<Object> datarow = new ArrayList<Object>();

                // datarow.add(entry.getKey().getEnddate());

                for (int i = 0; i < (COLUMN_COUNT + 1); i++) {
                    switch (i) {
                        case COLUMN_COUNT:
                            datarow.add(entry.getKey());
                            break;
                        case 7:
                            datarow.add(entry.getKey().getEnddate());
                            break;
                        case 6:
                            datarow.add(entry.getKey().getStartdate());
                            break;
                        case 5:
                            datarow.add(entry.getKey().getEnabled_flag());
                            break;
                        case 4:
                            datarow.add(entry.getKey().getDocTypeCode());
                            break;
                        case 3:
                            datarow.add(entry.getKey().getDocGroupCode());
                            break;
                        case 2:
                            datarow.add(entry.getKey().getName());
                            break;
                        case 1:
                            datarow.add(entry.getKey().getCode());
                            break;
                        case 0: {
                            if (entry.getValue() == 0) {
                                datarow.add(iconPlus);
                            } else datarow.add(iconMinus);

                        }

                    }
                }

                data.add(datarow);
            }


        }


        @Override
        public String getColumnName(int column) {

            switch (column) {
                case 0:
                    return "+/-";
                case 1:
                    return "Идентификатор";
                case 2:
                    return "Наименование";
                case 3:
                    return "Код группы";
                case 4:
                    return "Код типа";
                case 5:
                    return "Активна";
                case 6:
                    return "Действует с";
                case 7:
                    return "Действует по";
                default:
                    return "Что-то";
            }
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMN_COUNT;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {


            return (data.get(rowIndex)).get(columnIndex);


        }


        public Control getControlAt(int rowIndex) {


            return (Control) data.get(rowIndex).get(COLUMN_COUNT);
        }


        @Override
        public Class getColumnClass(int columnIndex) {


            switch (columnIndex) {

                case 0:
                    return Icon.class;
                case 1:
                    return String.class;

                default:
                    return Object.class;
            }
        }
    }

    class LineModel extends AbstractTableModel {
        private static final int COLUMN_COUNT = 19;
        private ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
        private ImageIcon iconPlus;
        private ImageIcon iconMinus;
        private Control ctr;

        public LineModel(Control ctr) {

            this.ctr = ctr;

            iconPlus = ImageIconUtils.getImigeIcon("img/add2.gif", ErrorControlsPanel.class);
            iconMinus = ImageIconUtils.getImigeIcon("img/del.gif", ErrorControlsPanel.class);

            if (log.isDebugEnabled() && ctr == null) {
                log.debug("ctr = null");
            }


            for (ControlLine line : ctr.getLines().values()) {


                ArrayList<Object> datarow = new ArrayList<Object>();


                for (int i = 0; i < COLUMN_COUNT; i++) {
                    switch (i) {
                        case 19:
                            datarow.add(line.getDescription());
                            break;
                        case 18:
                            datarow.add(line.getUsernameUpdated());
                            break;
                        case 17:
                            datarow.add(line.getEndDateActive());
                            break;
                        case 16:
                            datarow.add(line.getStartDateActive());
                            break;
                        case 15:
                            datarow.add(line.getCondWhereClauseOtch());
                            break;
                        case 14:
                            datarow.add(line.getCondWhereClauseR());
                            break;
                        case 13:
                            datarow.add(line.getCondWhereClause());
                            break;
                        case 12:
                            datarow.add(line.getSccMessageCode());
                            break;
                        case 11:
                            datarow.add(line.getTmrMssageCode());
                            break;
                        case 10:
                            datarow.add(line.getMessageCode());
                            break;
                        case 9:
                            datarow.add(line.getControlType());
                            break;
                        case 8:
                            datarow.add(line.getIndVal());
                            break;
                        case 7:
                            datarow.add(line.getIndType());
                            break;
                        case 6:
                            datarow.add(line.getCheckOperation());
                            break;
                        case 5:
                            datarow.add(line.getDocSQL());
                            break;

                        case 4:
                            datarow.add(line.getDocTabType());
                            break;

                        case 3:
                            datarow.add(line.getNumKC());
                            break;
                        case 2:
                            datarow.add(line.getEnabledFlag());
                            break;
                        case 1:
                            datarow.add(line.getNum());
                            break;
                        case 0: {
                            if (!line.isErrorFlag()) {
                                datarow.add(iconPlus);
                            } else datarow.add(iconMinus);

                        }

                    }
                }

                data.add(datarow);
            }


        }

        Control getCtr() {
            return ctr;
        }

        @Override
        public String getColumnName(int column) {

            switch (column) {
                case 19:
                    return "Описание проверки";
                case 18:
                    return "Автор посл. изменения";
                case 17:
                    return "Действует по";
                case 16:
                    return "Действует с";
                case 15:
                    return "Доп. условие для ОТЧ";
                case 14:
                    return "Условие выполнения проверки правой части";
                case 13:
                    return "Условие выполнения проверки";
                case 12:
                    return "Текст сообщения об успешном прохождении";
                case 11:
                    return "Сообщение для ошибки TOO_MANY_ROWS";
                case 10:
                    return "Сообщение при невыполнении";
                case 9:
                    return "Тип контроля";
                case 8:
                    return "Значение показателя";
                case 7:
                    return "Тип показателя";
                case 6:
                    return "Оператор";
                case 5:
                    return "SQL";
                case 4:
                    return "Источник";
                case 3:
                    return "Номер КС";
                case 2:
                    return "Акт";
                case 1:
                    return "Шаг";
                case 0:
                    return "+/-";

                default:
                    return "Что-то";
            }
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMN_COUNT;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {


            return (data.get(rowIndex)).get(columnIndex);


        }

        public int getLineNum(int rowIndex) {


            return (Integer) (data.get(rowIndex)).get(1);
        }

      /*  public ControlLine getControlAt(int rowIndex) {


            return (ControlLine) data.get(rowIndex).get(2);
        }*/


        @Override
        public Class getColumnClass(int columnIndex) {


            switch (columnIndex) {

                case 0:
                    return Icon.class;
                case 1:
                    return Number.class;
                case 16:
                    return Date.class;
                case 17:
                    return Date.class;
                default:
                    return String.class;
            }
        }
    }

}
