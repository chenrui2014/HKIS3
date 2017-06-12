package com.huake.hkis.hkis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen on 2017/6/10.
 */

public interface OnFragmentListener {
    void onFragmentAction(Map<String,String> params, Class intentClass);
}
