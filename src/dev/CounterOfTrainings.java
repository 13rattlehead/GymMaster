package dev;

public class CounterOfTrainings {
    private Coach coach;
    private int countTraining;

    public CounterOfTrainings(Coach coach, int countTraining) {
        this.coach = coach;
        this.countTraining = countTraining;
    }

    public Coach getCoach() {
        return this.coach;
    }

    public int getCountTraining() {
        return this.countTraining;
    }

    public void setCountTraining(int countTraining) {
        this.countTraining = countTraining;
    }

    public void incrementCountTraining() {
        this.countTraining++;
    }
}
