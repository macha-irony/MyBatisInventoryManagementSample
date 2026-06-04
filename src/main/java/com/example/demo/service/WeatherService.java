package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.WeatherResponse;
@Service
public class WeatherService {
	@Autowired // これだけで、さっき作った RestTemplate が注入される！
	private RestTemplate restTemplate;

	public WeatherResponse getCurrentWeather() {
		// あなたのAPIキーを入力してください
		String apiKey = "a7c74027597f691213c607bd93df26ac"; 
		// 末尾に &lang=ja を追加するだけ！
		String url = "https://api.openweathermap.org/data/2.5/weather?q=Osaka&units=metric&lang=ja&appid=" + apiKey;

		// APIを叩いて、WeatherResponseクラスに変換してもらう
		return restTemplate.getForObject(url, WeatherResponse.class);    
	}
}
