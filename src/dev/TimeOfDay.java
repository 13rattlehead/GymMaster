package dev;

public class TimeOfDay implements Comparable<TimeOfDay> {

    @Override
    public int compareTo(TimeOfDay o) {
        if (this.hours != o.hours) {
            return this.hours - o.hours;
        }
        return this.minutes - o.minutes;
    }

    //часы (от 0 до 23)
    private int hours;
    //минуты (от 0 до 59)
    private int minutes;

    public TimeOfDay(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }
}