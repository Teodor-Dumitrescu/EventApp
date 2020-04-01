package MDS;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

    public int compare(Event event1, Event event2) {
        if(event1.startDate.getYear() < event2.startDate.getYear())
            return -1;
        else if(event1.startDate.getYear() > event2.startDate.getYear())
            return 1;
        else {
            if (event1.startDate.getMonth() < event2.startDate.getMonth())
                return -1;
            else if (event1.startDate.getMonth() > event2.startDate.getMonth())
                return 1;
            else {
                if(event1.startDate.getDay() < event2.startDate.getDay())
                    return -1;
                else if(event1.startDate.getDay() > event2.startDate.getDay())
                    return 1;
                else
                    return  0;
            }
        }
    }
}
