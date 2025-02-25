package com.side.daangn.util;

import java.util.List;
import java.util.Random;

public class NickNameUtil {

    private static final List<String> VERBS = List.of(
            "달리는", "웃는", "노래하는", "춤추는", "날아가는", "뛰는", "구르는", "빛나는", "반짝이는", "기다리는",
            "헤엄치는", "미소짓는", "놀라는", "즐거운", "활짝 웃는", "말하는", "속삭이는", "고요한", "눈부신", "포근한",
            "기뻐하는", "잔잔한", "빛바랜", "거침없는", "행복한", "슬퍼하는", "우아한", "힘찬", "여유로운", "용감한"
    );

    private static final List<String> NOUNS = List.of(
            "호랑이", "여우", "늑대", "토끼", "곰", "고양이", "강아지", "다람쥐", "사슴", "기린",
            "펭귄", "부엉이", "독수리", "코끼리", "수달", "너구리", "하마", "코알라", "치타", "고래",
            "돌고래", "사자", "악어", "판다", "원숭이", "공룡", "거북이", "햄스터", "두더지", "올빼미"
    );

    private static final Random RANDOM = new Random();


    public static String randonName(){
        String verb = VERBS.get(RANDOM.nextInt(VERBS.size()));
        String noun = NOUNS.get(RANDOM.nextInt(NOUNS.size()));
        return verb + " " + noun;
    }







}
