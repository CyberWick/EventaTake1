package com.eventa1.eventatake1;

import java.util.List;

public interface RegisterLoad {
    void onFirebaseLoadSuccess(List<Register> list);
    void onFirebaseLoadFail(String message);
}
