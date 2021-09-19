package com.will_d.yogadesign.worktoday;

public interface ItemTouchHelperListener {

    boolean onItemMove(int from_position, int to_position);
    void onItemSwipe(int position);
}
