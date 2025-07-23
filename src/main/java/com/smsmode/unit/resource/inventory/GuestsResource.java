package com.smsmode.unit.resource.inventory;

import lombok.Data;

import java.util.List;

@Data
public class GuestsResource {
    private int adults;
    private List<Child> children;

    @Data
    public static class Child {
        private int age;
        private int quantity;
    }
}
