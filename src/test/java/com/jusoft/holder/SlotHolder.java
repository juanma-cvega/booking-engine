package com.jusoft.holder;

import com.jusoft.slot.SlotResource;

import java.util.ArrayList;
import java.util.List;

public class SlotHolder {

    public SlotResource slotCreated;
    public SlotResource slotFetched;
    public List<SlotResource> slotsCreated = new ArrayList<>();
    public List<SlotResource> slotsFetched = new ArrayList<>();

}
