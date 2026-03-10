package dev;

import java.util.*;
import dev.DayOfWeek;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();


    public void addNewTrainingSession(TrainingSession trainingSession) {

        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> dayTable = timetable.get(day);

        if (dayTable == null) {
            dayTable = new TreeMap<>();
            timetable.put(day, dayTable);
        }

        List<TrainingSession> sessionsAtTime = dayTable.get(time);

        if (sessionsAtTime == null) {
            sessionsAtTime = new ArrayList<>();
            dayTable.put(time, sessionsAtTime);
        }

        sessionsAtTime.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(
            DayOfWeek dayOfWeek,
            TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, List<TrainingSession>> dayTable = timetable.get(dayOfWeek);

        if (dayTable == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> sessions = dayTable.get(timeOfDay);

        if (sessions == null) {
            return new ArrayList<>();
        }

        return sessions;
    }

    public List<CounterOfTrainings> getCountByCoaches() {

        Map<Coach, Integer> counters = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            TreeMap<TimeOfDay, List<TrainingSession>> dayTable = timetable.get(day);
            if (dayTable == null) {
                continue;
            }
            for (TimeOfDay time : dayTable.keySet()) {
                List<TrainingSession> sessions = dayTable.get(time);
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    counters.put(coach, counters.getOrDefault(coach, 0) + 1);
                }
            }
        }
        List<CounterOfTrainings> countersList = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry: counters.entrySet()) {
            countersList.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        countersList.sort((c1, c2) -> {
            int compareCount = Integer.compare(c2.getCountTraining(), c1.getCountTraining());

            if (compareCount != 0) {
                return compareCount;
            }

            return c1.getCoach().getSurname().compareTo(c2.getCoach().getSurname());
        });
        return countersList;
    }

}