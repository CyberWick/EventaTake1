package com.eventa1.eventatake1;

import java.util.HashMap;
import java.util.List;

public interface IfFirebaseLoad_comp {
    void onFirebaseLoadSuccess(List<String> compList, HashMap<String, Compete> hashMap,String status);
    void onFirebaseLoadFail(String message);
}
