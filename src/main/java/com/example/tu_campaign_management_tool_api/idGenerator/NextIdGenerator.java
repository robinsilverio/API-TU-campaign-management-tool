package com.example.tu_campaign_management_tool_api.idGenerator;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

public class NextIdGenerator {

    @Getter
    @Setter
    protected AtomicLong currentId = new AtomicLong(1);
    private int startingStringLength = 6;


    public String generate() {
        long id = currentId.getAndIncrement();
        if (hasReachedTheMaximum(id)) {
            startingStringLength++;
            id = 1;
            this.currentId = new AtomicLong(1);
        }
        return String.format("%0" + startingStringLength + "d", id);
    }

    private boolean hasReachedTheMaximum(long id) {
        boolean hasReached = false;
        if (id == (long) Math.pow(10, startingStringLength) - 1) {
            hasReached = true;
        }
        return hasReached;
    }
}
