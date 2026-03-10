package test;

import dev.*;
import dev.DayOfWeek;
import dev.Group.Age;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertNotNull(mondaySessions);
        assertEquals(1, mondaySessions.size());

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertNull(tuesdaySessions);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertNotNull(mondaySessions);
        assertEquals(1, mondaySessions.size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertNotNull(thursdaySessions);
        assertEquals(2, thursdaySessions.size());

        // Проверить порядок - сначала 13:00, потом 20:00
        List<TimeOfDay> sortedTimes = new ArrayList<>(thursdaySessions.keySet());
        assertEquals(2, sortedTimes.size());
        assertEquals(new TimeOfDay(13, 0), sortedTimes.get(0));
        assertEquals(new TimeOfDay(20, 0), sortedTimes.get(1));

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertNull(tuesdaySessions);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> monday1300Sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertNotNull(monday1300Sessions);
        assertEquals(1, monday1300Sessions.size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> monday1400Sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertNotNull(monday1400Sessions);
        assertEquals(0, monday1400Sessions.size());
    }

    @Test
    void testGetCountByCoachesSingleCoachMultipleSessions() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 90);

        Coach coach = new Coach("Иванов", "Иван", "Сергеевич");

        TrainingSession session1 = new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession session2 = new TrainingSession(group2, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(1, result.size());
        assertEquals(coach, result.get(0).getCoach());
        assertEquals(2, result.get(0).getCountTraining());
    }

    @Test
    void testGetCountByCoachesMultipleCoachesSameTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coach1 = new Coach("Иванов", "Иван", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Петр", "Сергеевич");
        Coach coach3 = new Coach("Сидоров", "Сидор", "Алексеевич");

        TrainingSession session1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession session2 = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession session3 = new TrainingSession(group, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(3, result.size());

        // Проверяем, что все тренинги отсортированы по убыванию количества
        assertEquals(1, result.get(0).getCountTraining());
        assertEquals(1, result.get(1).getCountTraining());
        assertEquals(1, result.get(2).getCountTraining());

        // Проверяем, что тренинги отсортированы по фамилии (в порядке возрастания)
        assertEquals(coach1, result.get(0).getCoach()); // Иванов
        assertEquals(coach2, result.get(1).getCoach()); // Петров
        assertEquals(coach3, result.get(2).getCoach()); // Сидоров
    }

    @Test
    void testGetCountByCoachesWithDifferentDaysAndTimes() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coach1 = new Coach("Иванов", "Иван", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Петр", "Сергеевич");

        TrainingSession session1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession session2 = new TrainingSession(group, coach1,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));
        TrainingSession session3 = new TrainingSession(group, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));
        TrainingSession session4 = new TrainingSession(group, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(16, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);
        timetable.addNewTrainingSession(session4);

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());

        // Проверяем, что тренинги отсортированы по убыванию количества
        assertEquals(coach1, result.get(0).getCoach());
        assertEquals(3, result.get(0).getCountTraining());

        assertEquals(coach2, result.get(1).getCoach());
        assertEquals(1, result.get(1).getCountTraining());
    }


}
