package general;

import java.util.Comparator;

public class EventComparatorByPrice implements Comparator<Event> {

    @Override
    public int compare(Event t1, Event t2) {
        return Float.compare(t1.getStandardTicketPrice(),t2.getStandardTicketPrice());
    }
}
