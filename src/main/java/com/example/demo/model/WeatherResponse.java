package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//APIから不要なデータが来ても無視するように設定
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
	// JSONの "main" オブジェクトに対応
    public Main main;
    public String name;
    
    // JSONの "weather" 配列に対応
    public List<Weather> weather;
    
    // 気温などが入る内部クラス
    public static class Main {
        public double temp;
    }

    // 天気名が入る内部クラス
    public static class Weather {
        public String main; // 天気の概要（Clouds, Rainなど）
        public String icon; // 天気アイコンのコード
        public String description;
    }
}
