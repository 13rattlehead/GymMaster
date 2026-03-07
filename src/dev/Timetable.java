package dev;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

import dev.TrainingSession.DayOfWeek;

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
}