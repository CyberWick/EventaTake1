package com.eventa1.eventatake1;

import java.util.List;

public interface BookedLoad {
    void onFirebaseLoadSuccess(List<BookedEvents> list);
    void onFirebaseLoadFail(String message);
}
