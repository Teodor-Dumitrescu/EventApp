package general;

import java.util.Comparator;

public class EventComparatorByDate implements Comparator<Event> {

    @Override
    public int compare(Event t1, Event t2) {
        String[] dateA = t1.getLocation().getDate().split("-");
        String[] dateB = t2.getLocation().getDate().split("-");

        //check years
        int yearA = Integer.parseInt(dateA[2]);
        int yearB = Integer.parseInt(dateB[2]);

        if(yearA != yearB){
            return Integer.compare(yearA, yearB);
        }

        //years are equal => check months
        int monthA = Integer.parseInt(dateA[1]);
        int monthB = Integer.parseInt(dateB[1]);

        if(monthA != monthB) {
            return Integer.compare(monthA, monthB);
        }

        //years and months are equal => check days
        int dayA = Integer.parseInt(dateA[0]);
        int dayB = Integer.parseInt(dateB[0]);

        return Integer.compare(dayA, dayB);

    }
}