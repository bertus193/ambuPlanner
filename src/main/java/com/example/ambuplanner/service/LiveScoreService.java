package com.example.ambuplanner.service;

import org.springframework.stereotype.Component;

@Component
public class LiveScoreService {

    public int getScore() {

        return 1;
    }

    private int initialScores() {
        return 0;
    }

}