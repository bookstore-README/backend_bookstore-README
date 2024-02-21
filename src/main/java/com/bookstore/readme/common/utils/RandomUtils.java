package com.bookstore.readme.common.utils;

import java.util.Random;

public class RandomUtils {

    private static final String[] first = new String[] {"익명"};

    public String RandomNickname() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        sb.append("익명 ");

        // 앞 대문자 영어 4글자
        for(int i = 0; i < 4; i++) {
            sb.append((char)(random.nextInt(26) + 65));
        }

        sb.append(random.nextInt(5000));

        return sb.toString();
    }

}
