import java.time.LocalDate;

public class Date {
    
    private final int year;
    private final String month;
    private final int day;
    private final int hours;
    private final int minutes;

    public Date(int year, String month, int day, int hours, int minutes){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hours = hours;
        this.minutes = minutes;
    }    

    public Date(String s){
        
        String[] parts = s.split("/");

        if (parts.length != 5) {
            throw new IllegalArgumentException("Date format not valid: " + s);
        }

        day = Integer.parseInt(parts[0]);
        month = parts[1];
        year = Integer.parseInt(parts[2]);
        hours = Integer.parseInt(parts[3]);
        minutes = Integer.parseInt(parts[4]);

    }

    public Date(String s, boolean boh){
        String[] parts = s.split(" ");

        if (parts.length != 4) {
            throw new IllegalArgumentException("Date format not valid: " + s);
        }

        day = Integer.parseInt(parts[0]);
        month = parts[1];
        year = Integer.parseInt(parts[2]);
        hours = Integer.parseInt(String.valueOf(parts[3].charAt(0))) * 10 + Integer.parseInt(String.valueOf(parts[3].charAt(1)));
        minutes = Integer.parseInt(String.valueOf(parts[3].charAt(3))) * 10 + Integer.parseInt(String.valueOf(parts[3].charAt(4)));

    }

    public String getDate(){
        return day + "/" + month + "/" + year + "/" + hours + "/" + minutes;
    }

    public String getWellWrittenDate(){
        String hour;
        String minute;
        if(hours < 10) hour = "0" + hours;
        else hour = "" + hours;
        if(minutes < 10) minute = "0" + minutes;
        else minute = "" + minutes;

        return day + " " + month + " " + year + " " + hour + ":" + minute;
    }

    public int evaluateDate(){
        LocalDate today = LocalDate.now();
        if(year < today.getYear()) return 0;
        if(year > today.getYear()) return 2;
        if(evaluateMese() < today.getMonthValue()) return 0;
        if(evaluateMese() > today.getMonthValue()) return 2;
        if(day < today.getDayOfMonth()) return 0;
        if(day > today.getDayOfMonth()) return 2;
        return 1;
    }

    public boolean evaluateDate(Date date){
        if(minutes != date.getMinute()) return false;
        if(hours != date.getHour()) return false;
        if(day != date.getDayOfMonth()) return false;
        if(evaluateMese() != date.getMonthValue()) return false;
        if(year != date.getYear()) return false;
        return true;
    }

    public int evaluateDate(Date date, boolean temp){
        if(year < date.getYear()) return 0;
        if(year > date.getYear()) return 2;
        if(evaluateMese() < date.getMonthValue()) return 0;
        if(evaluateMese() > date.getMonthValue()) return 2;
        if(day < date.getDayOfMonth()) return 0;
        if(day > date.getDayOfMonth()) return 2;
        if(hours < date.getHour()) return 0;
        if(hours > date.getHour()) return 2;
        if(minutes < date.getMinute()) return 0;
        if(minutes > date.getMinute()) return 2;
        return 1;
    }

    public int getYear(){
        return year;
    }

    public int getMonthValue(){
        return evaluateMese();
    }

    public int getDayOfMonth(){
        return day;
    }

    public int getHour(){
        return hours;
    }

    public int getMinute(){
        return minutes;
    }

    private int evaluateMese(){
        switch(month){
            case "January": return 1;
            case "February": return 2;
            case "March": return 3;
            case "April": return 4;
            case "May": return 5;
            case "June": return 6;
            case "July": return 7;
            case "August": return 8;
            case "September": return 9;
            case "October": return 10;
            case "November": return 11;
        }
        return 12;
    }

}
