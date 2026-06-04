package com.example.demo.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SlackNotifier {
	// 先ほど取得したURLをここに設定
    private final String WEBHOOK_URL = "https://hooks.slack.com/services/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";

    public void sendNotification(String message) {
        RestTemplate restTemplate = new RestTemplate();
        
        // Slackは {"text": "メッセージ内容"} という形式で受け取る
        Map<String, String> payload = new HashMap<>();
        payload.put("text", message);
        
        // POSTリクエストを送信
        restTemplate.postForEntity(WEBHOOK_URL, payload, String.class);
    }
}
