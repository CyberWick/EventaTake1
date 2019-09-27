package com.eventa1.eventatake1;

import java.util.List;

public interface IfFirebaseLoad {
    void onFirebaseLoadSuccess(List<EventsInfo> list);
    void onFirebaseLoadFail(String message);
}
