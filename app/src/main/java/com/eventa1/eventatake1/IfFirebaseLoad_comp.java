package com.eventa1.eventatake1;

import java.util.HashMap;
import java.util.List;

public interface IfFirebaseLoad_comp {
    void onFirebaseLoadSuccess(List<String> compList, HashMap<String,CompClass> hashMap);
    void onFirebaseLoadFail(String message);
}
