package com.eventa1.eventatake1;

import java.util.List;

public interface HostFirebase {
    void onFirebaseLoadSuccess(List<Register> list);
    void onFirebaseLoadFail(String message);
}
