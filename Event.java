import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Event extends JFrame{

    private Frame mainFrame;
    private String event;
    private Date date;
    private int priority;

    private JPanel panel;
    private JPanel panelNorth;
    private JPanel panelSouth;

    // Panel NORTH
    private JTextArea eventA;
    private JScrollPane scrollEvent;
    private JComboBox<Integer> year;
    private JComboBox<String> month;
    private JComboBox<Integer> day;
    private JComboBox<Integer> hours;
    private JComboBox<Integer> minutes;
    private JComboBox<Integer> priorityA;

    // Panel SOUTH

    private JButton cancel;
    private JButton modify;
    private JButton delete;

    public Event(String event, String data, int priority, Frame mainFrame){
        this.event = event;
        this.date = new Date(data, true);
        this.priority = priority;
        this.mainFrame = mainFrame;

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        // Panel NORTH

        panelNorth = new JPanel();
        panel.add(panelNorth, "North");

        panelNorth.add(new JLabel("Event:"));

        eventA = new JTextArea(10, 30);
        eventA.setText(this.event);
        eventA.setLineWrap(true);
        eventA.setWrapStyleWord(true);
        scrollEvent = new JScrollPane(eventA);
        scrollEvent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollEvent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelNorth.add(scrollEvent);

        panelNorth.add(new JLabel("Year:"));

        year = new JComboBox<>();
        for(int i = this.date.getYear(); i < this.date.getYear() + 100; i++) year.addItem(i);
        panelNorth.add(year);

        panelNorth.add(new JLabel("Month:"));

        month = new JComboBox<>();
        inserisciMesi();
        month.setSelectedIndex(this.date.getMonthValue() - 1);
        panelNorth.add(month);

        panelNorth.add(new JLabel("Day:"));

        day = new JComboBox<>();
        for(int i = 1; i <= 31; i++) day.addItem(i);
        day.setSelectedIndex(this.date.getDayOfMonth() - 1);
        panelNorth.add(day);

        panelNorth.add(new JLabel("Hours:"));

        hours = new JComboBox<>();
        for(int i = 0; i <= 23; i++) hours.addItem(i);
        hours.setSelectedIndex(this.date.getHour());
        panelNorth.add(hours);

        panelNorth.add(new JLabel("Minutes:"));

        minutes = new JComboBox<>();
        for(int i = 0; i <= 59; i++) minutes.addItem(i);
        minutes.setSelectedIndex(this.date.getMinute());
        panelNorth.add(minutes);

        panelNorth.add(new JLabel("Priority:"));

        priorityA = new JComboBox<>();
        for(int i = 1; i <= 5; i++) priorityA.addItem(i);
        priorityA.setSelectedItem(this.priority);
        panelNorth.add(priorityA);

        // Panel SOUTH

        panelSouth = new JPanel();
        panelSouth.setLayout(new GridLayout(2, 3));
        panel.add(panelSouth, "South");

        cancel = new JButton("cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                if(ae.getSource() == cancel){
                    Event.this.dispose();
                }
            }
        });
        panelSouth.add(cancel);

        modify = new JButton("modify");
        modify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                if(ae.getSource() == modify){
                    if(eventA.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Empty event.");
                    }else{
                        try {
                            int x = -1;
                            ArrayList<String> names = new ArrayList<>();
                            ArrayList<Date> dates = new ArrayList<>();
                            ArrayList<Integer> priorities = new ArrayList<>();

                            // PRIMA LETTURA: trova indice x della riga da modificare
                            BufferedReader br1 = new BufferedReader(new FileReader("saves.csv"));
                            String row;
                            while ((row = br1.readLine()) != null) {
                                String[] parts = row.split(";");
                                names.add(parts[0]);
                                dates.add(new Date(parts[1]));
                                priorities.add(Integer.parseInt(parts[2]));
                            }
                            br1.close();

                            for (int i = 0; i < names.size(); i++) {
                                if (names.get(i).equals(Event.this.event) &&
                                    dates.get(i).evaluateDate(Event.this.date) &&
                                    priorities.get(i) == Event.this.priority) {
                                    x = i;
                                    break;
                                }
                            }

                            if (x != -1) {
                                // Create new updated Date
                                Date dataTemp = new Date(
                                    (int) Event.this.year.getSelectedItem(),
                                    (String) Event.this.month.getSelectedItem(),
                                    (int) Event.this.day.getSelectedItem(),
                                    (int) Event.this.hours.getSelectedItem(),
                                    (int) Event.this.minutes.getSelectedItem()
                                );

                                ArrayList<String> rows = new ArrayList<>();

                                // SECOND READ: re-read everything and modify only row x
                                BufferedReader br2 = new BufferedReader(new FileReader("saves.csv"));
                                int y = 0;
                                while ((row = br2.readLine()) != null) {
                                    if (y == x) {
                                        rows.add(Event.this.eventA.getText() + ";" + dataTemp.getDate() + ";" + Event.this.priorityA.getSelectedItem());
                                    } else {
                                        rows.add(row);
                                    }
                                    y++;
                                }
                                br2.close();

                                // WRITING: overwrite file with updated rows
                                BufferedWriter bw = new BufferedWriter(new FileWriter("saves.csv"));
                                for (String r : rows) {
                                    bw.write(r);
                                    bw.newLine();
                                }
                                bw.close();

                                JOptionPane.showMessageDialog(null, "Event modified successfully!");
                                mainFrame.reorderEvents();
                                Event.this.dispose();

                            } else {
                                JOptionPane.showMessageDialog(null, "Event not found.");
                            }

                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                }
            }
        });
        panelSouth.add(modify);

        delete = new JButton("delete");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                if(ae.getSource() == delete){
                    String fileName = "saves.csv";
                    String rowToDelete = Event.this.event + ";" + Event.this.date.getDate() + ";" + Event.this.priority;

                    List<String> rows = new ArrayList<>();

                    // 1. Read all rows
                    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                        String row;
                        while ((row = br.readLine()) != null) {
                            if (!row.equals(rowToDelete)) {
                                rows.add(row);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error while reading: " + e.getMessage());
                    }

                    // 2. Overwrite file with filtered rows
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
                        for (String row : rows) {
                            bw.write(row);
                            bw.newLine();
                        }
                        bw.close();
                        JOptionPane.showMessageDialog(null, "Event deleted successfully!");
                        mainFrame.reorderEvents();
                        Event.this.dispose();
                    } catch (IOException e) {
                        System.out.println("Error while writing: " + e.getMessage());
                    }
                }
            }
        });
        panelSouth.add(delete);

        panelSouth.add(new JLabel("Made entirely by Michele Mattevi©"));
        panelSouth.add(new JLabel(""));
        panelSouth.add(new JLabel(""));

    }

    private void inserisciMesi(){
        month.addItem("January");
        month.addItem("February");
        month.addItem("March");
        month.addItem("April");
        month.addItem("May");
        month.addItem("June");
        month.addItem("July");
        month.addItem("August");
        month.addItem("September");
        month.addItem("October");
        month.addItem("November");
        month.addItem("December");
    }
    
}
