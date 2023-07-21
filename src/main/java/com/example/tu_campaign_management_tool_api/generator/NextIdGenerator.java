package com.example.tu_campaign_management_tool_api.generator;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class NextIdGenerator implements IdentifierGenerator {

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

    @Override
    public String generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return this.generate();
    }
}
