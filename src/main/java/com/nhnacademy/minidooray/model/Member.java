package com.nhnacademy.minidooray.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {
    private String id;
//    private String email;

    public static Member of(Account account) {
        return new Member(account.id());
    }
}
