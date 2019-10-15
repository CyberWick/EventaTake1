package com.eventa1.eventatake1;

import java.util.List;

public interface RegistreEventAsyn {
    void onFirebaseLoadSuccess(List<RegistreEvent> list);
    void onFirebaseLoadFail(String message);
}
