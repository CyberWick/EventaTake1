package com.eventa1.eventatake1;

import java.util.List;

public interface Book2userLoad {
    void onFirebaseLoadSuccess(List<BookedEvents2user> list);
    void onFirebaseLoadFail(String message);
}
