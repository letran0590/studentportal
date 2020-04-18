package com.example.studentportal.enumeration;

public enum Role {
    STUDENT(1),
    TUTOR(2),
    STAFF(3);

    private int value;

    Role(int value) {
        this.value = value;

    }

    public static Role getValue(int id) {
        for (Role visibility : Role.values()) {
            if (visibility.value == id) {
                return visibility;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
