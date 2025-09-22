import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

public class Frame extends JFrame {

    private LocalTime time = LocalTime.now();


    private JPanel panel;
    private JPanel panelNorth;
    private JPanel panelPreCentral;
    private JPanel panelCentral;

    // Panel NORTH
    private JTextArea event;
    private JScrollPane scrollEvent;
        private JComboBox<Integer> year;
        private JComboBox<String> month;
        private JComboBox<Integer> day;
        private JComboBox<Integer> hour;
        private JComboBox<Integer> minutes;
        private JComboBox<Integer> priority;
        private JButton add;

        // Panel PRECENTRAL
        private JButton refresh;
        private JComboBox<String> filter;
        private JComboBox<String> order;

        // Panel CENTRAL
        private JPanel pastEvents;
        private JPanel currentEvents;
        private JPanel futureEvents;
        private JScrollPane scrollPastEvents;
        private JScrollPane scrollCurrentEvents;
        private JScrollPane scrollFutureEvents;

    public Frame(){

            panel = new JPanel();
            panel.setLayout(new BorderLayout());
        add(panel);

        // Panel NORTH

        panelNorth = new JPanel();
        panel.add(panelNorth, "North");

        panelNorth.add(new JLabel("Event:"));

        event = new JTextArea(10, 30);
        event.setLineWrap(true);
        event.setWrapStyleWord(true);
        scrollEvent = new JScrollPane(event);
        scrollEvent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollEvent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelNorth.add(scrollEvent);

        panelNorth.add(new JLabel("Year:"));

        year = new JComboBox<>();
        LocalDate today = LocalDate.now();
        for(int i = today.getYear(); i < today.getYear() + 100; i++) year.addItem(i);
        panelNorth.add(year);

        panelNorth.add(new JLabel("Month:"));

        month = new JComboBox<>();
        insertMonths();
        month.setSelectedIndex(today.getMonthValue() - 1);
        panelNorth.add(month);

        panelNorth.add(new JLabel("Day:"));

        day = new JComboBox<>();
        for(int i = 1; i <= 31; i++) day.addItem(i);
        day.setSelectedIndex(today.getDayOfMonth() - 1);
        panelNorth.add(day);

        panelNorth.add(new JLabel("Hours:"));

        hour = new JComboBox<>();
        for(int i = 0; i <= 23; i++) hour.addItem(i);
        hour.setSelectedIndex(time.getHour());
        panelNorth.add(hour);

        panelNorth.add(new JLabel("Minutes:"));

        minutes = new JComboBox<>();
        for(int i = 0; i <= 59; i++) minutes.addItem(i);
        minutes.setSelectedIndex(time.getMinute());
        panelNorth.add(minutes);

        panelNorth.add(new JLabel("Priority:"));

        priority = new JComboBox<>();
        for(int i = 1; i <= 5; i++) priority.addItem(i);
        panelNorth.add(priority);
    
        add = new JButton("add");
        add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae){
                if(ae.getSource() == add){
                    if(event.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Empty event.");
                    }
                    else{
                        try {

                            FileWriter fw = new FileWriter("saves.csv", true);
                            Date date = new Date((int) year.getSelectedItem(),
                                                    (String) month.getSelectedItem(),
                                                    (int) day.getSelectedItem(),
                                                    (int) hour.getSelectedItem(),
                                                    (int) minutes.getSelectedItem());
                            fw.write(event.getText() + ";" + date.getDate() + ";" + priority.getSelectedItem() + "\n");
                            fw.close();
                            reorderEvents();
                        } catch (IOException e) {
                            System.out.println("Error while saving: " + e.getMessage());
                        }
                    }
                }
            }

        });
        panelNorth.add(add);

        // Panel PRECENTRAL
        panelPreCentral = new JPanel();
        panel.add(panelPreCentral, "Center");
        refresh = new JButton("refresh/reorder");
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                if(ae.getSource() == refresh){
                    reorderEvents();
                    JOptionPane.showMessageDialog(null, "Events refreshed.");
                }
            }
        });
        panelPreCentral.add(refresh);
        panelPreCentral.add(new JLabel("Filter:"));
        filter = new JComboBox<>();
        filter.addItem("date");
        filter.addItem("priority");
        panelPreCentral.add(filter);
        panelPreCentral.add(new JLabel("Order:"));
        order = new JComboBox<>();
        order.addItem("ascending");
        order.addItem("descending");
        panelPreCentral.add(order);

        // Panel CENTRAL

        panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(3, 3));
        panel.add(panelCentral, "South");

        panelCentral.add(new JLabel("Past events"));
        panelCentral.add(new JLabel("Current events"));
        panelCentral.add(new JLabel("Future events"));

        pastEvents = new JPanel();
        currentEvents = new JPanel();
        futureEvents = new JPanel();
        pastEvents.setLayout(new BoxLayout(pastEvents, BoxLayout.Y_AXIS));
        currentEvents.setLayout(new BoxLayout(currentEvents, BoxLayout.Y_AXIS));
        futureEvents.setLayout(new BoxLayout(futureEvents, BoxLayout.Y_AXIS));

        reorderEvents();

        scrollPastEvents = new JScrollPane(pastEvents);
        scrollPastEvents.setPreferredSize(new Dimension(200, 5 * 30));
        scrollCurrentEvents = new JScrollPane(currentEvents);
        scrollCurrentEvents.setPreferredSize(new Dimension(200, 5 * 30));
        scrollFutureEvents = new JScrollPane(futureEvents);
        scrollFutureEvents.setPreferredSize(new Dimension(200, 5 * 30));

        panelCentral.add(scrollPastEvents);
        panelCentral.add(scrollCurrentEvents);
        panelCentral.add(scrollFutureEvents);

        panelCentral.add(new JLabel("Made entirely by Michele Mattevi©"));
        panelCentral.add(new JLabel(""));
        panelCentral.add(new JLabel(""));


    }

    public void insertMonths(){
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

    public void reorderEvents(){
        try{
            pastEvents.removeAll();
            pastEvents.revalidate();
            pastEvents.repaint(); 
            currentEvents.removeAll();
            currentEvents.revalidate();
            currentEvents.repaint();
            futureEvents.removeAll();
            futureEvents.revalidate();
            futureEvents.repaint();

            ArrayList<String> names = new ArrayList<>();
            ArrayList<Date> dates = new ArrayList<>();
            ArrayList<Integer> priorities = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader("saves.csv"));
            String row;
            while ((row = br.readLine()) != null) {
                String[] parti = row.split(";");
                String name = parti[0];
                String date = parti[1];
                int priority = Integer.parseInt(parti[2]);
                names.add(name);
                dates.add(new Date(date));
                priorities.add(priority);
            }

            if((filter.getSelectedItem()).equals("date")){
                if((order.getSelectedItem()).equals("ascending")){

                    String tempName;
                    Date tempDate;
                    int tempPriority;
                    boolean swapped;
                    for (int i = 0; i < dates.size() - 1; i++) {
                        swapped = false;
                        for (int j = 0; j < dates.size() - i - 1; j++) {
                            if (dates.get(j).evaluateDate(dates.get(j + 1), true) == 2) {
                                tempName = names.get(j);
                                tempDate = dates.get(j);
                                tempPriority = priorities.get(j);
                                names.set(j, names.get(j + 1));
                                names.set(j + 1, tempName);
                                dates.set(j, dates.get(j + 1));
                                dates.set(j + 1, tempDate);
                                priorities.set(j, priorities.get(j + 1));
                                priorities.set(j + 1, tempPriority);
                                swapped = true;
                            }
                        }
                        if (swapped == false) {
                            break;
                        }
                    }
                }else{
                    String tempName;
                    Date tempDate;
                    int tempPriority;
                    boolean swapped;
                    for (int i = 0; i < dates.size() - 1; i++) {
                        swapped = false;
                        for (int j = 0; j < dates.size() - i - 1; j++) {
                            if (dates.get(j).evaluateDate(dates.get(j + 1), true) == 0) {
                                tempName = names.get(j);
                                tempDate = dates.get(j);
                                tempPriority = priorities.get(j);
                                names.set(j, names.get(j + 1));
                                names.set(j + 1, tempName);
                                dates.set(j, dates.get(j + 1));
                                dates.set(j + 1, tempDate);
                                priorities.set(j, priorities.get(j + 1));
                                priorities.set(j + 1, tempPriority);
                                swapped = true;
                            }
                        }
                        if (swapped == false) {
                            break;
                        }
                    }
                }
            }else{
                if((order.getSelectedItem()).equals("ascending")){
                    String tempName;
                    Date tempDate;
                    int tempPriority;
                    boolean swapped;
                    for (int i = 0; i < dates.size() - 1; i++) {
                        swapped = false;
                        for (int j = 0; j < priorities.size() - i - 1; j++) {
                            if (priorities.get(j) > priorities.get(j + 1)) {
                                tempName = names.get(j);
                                tempDate = dates.get(j);
                                tempPriority = priorities.get(j);
                                names.set(j, names.get(j + 1));
                                names.set(j + 1, tempName);
                                dates.set(j, dates.get(j + 1));
                                dates.set(j + 1, tempDate);
                                priorities.set(j, priorities.get(j + 1));
                                priorities.set(j + 1, tempPriority);
                                swapped = true;
                            }
                        }
                        if (swapped == false) {
                            break;
                        }
                    }
                }else{
                    String tempName;
                    Date tempDate;
                    int tempPriority;
                    boolean swapped;
                    for (int i = 0; i < dates.size() - 1; i++) {
                        swapped = false;
                        for (int j = 0; j < priorities.size() - i - 1; j++) {
                            if (priorities.get(j) < priorities.get(j + 1)) {
                                tempName = names.get(j);
                                tempDate = dates.get(j);
                                tempPriority = priorities.get(j);
                                names.set(j, names.get(j + 1));
                                names.set(j + 1, tempName);
                                dates.set(j, dates.get(j + 1));
                                dates.set(j + 1, tempDate);
                                priorities.set(j, priorities.get(j + 1));
                                priorities.set(j + 1, tempPriority);
                                swapped = true;
                            }
                        }
                        if (swapped == false) {
                            break;
                        }
                    }
                }
            }

            for(int i = 0; i < names.size(); i++){
                JButton button = new JButton(dates.get(i).getWellWrittenDate() + " ; " +  priorities.get(i) + " ; " + names.get(i));
                button.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ae){
                        if(ae.getSource() == button){
                            JButton clickedButton = (JButton)(ae.getSource());
                            String buttonContent = clickedButton.getText();
                            String[] parts = buttonContent.split(" ; ");
                            if (parts.length != 3) {
                                throw new IllegalArgumentException("Date format not valid: " + buttonContent);
                            }

                            Event e = new Event(parts[2], parts[0], Integer.parseInt(parts[1]), Frame.this);
                            e.pack();
                            e.setVisible(true);
                            e.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            e.setTitle(parts[2]);
                            e.setLocationRelativeTo(null);

                        }
                    }
                });
                switch(dates.get(i).evaluateDate()){
                    case 0:
                        pastEvents.add(button);
                        break;
                    case 1:
                        currentEvents.add(button);
                        break;
                    case 2:
                        futureEvents.add(button);
                        break;
                }
            }

            br.close();
        }catch(IOException e){
            System.out.println("Error while reading: " + e.getMessage());
        }
    }

}
