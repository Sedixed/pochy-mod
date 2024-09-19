package net.sedixed.pochy.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum PochyVariant {
    BROWN(0),
    BLACK(1),
    GREY(2);

    private static final PochyVariant[] BY_ID = Arrays.stream(values())
            .sorted(Comparator.comparingInt(PochyVariant::getId))
            .toArray(PochyVariant[]::new);

    private final int id;

    PochyVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PochyVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
