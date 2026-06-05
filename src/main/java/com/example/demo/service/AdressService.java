package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.AdressResponce;

import tools.jackson.databind.ObjectMapper;

@Service
public class AdressService {
	@Autowired
    RestTemplate restTemplate;
	
	public AdressResponce getAddressByZip(String zipCode) {
        String url = "https://zipcloud.ibsnet.co.jp/api/search?zipcode=" + zipCode;    
        // APIの結果をJSONで取得して加工する処理（ここでは簡易的に）
        // ※実際には返ってきたJSONを解析して、都道府県＋市区町村＋町域を連結します
        // 1. まず文字列として取得
        String jsonResponse = restTemplate.getForObject(url, String.class);
        
        try {
            // 2. ObjectMapper を使って手動でクラスに変換
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonResponse, AdressResponce.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // または適宜エラーハンドリング
        }
    }
}
