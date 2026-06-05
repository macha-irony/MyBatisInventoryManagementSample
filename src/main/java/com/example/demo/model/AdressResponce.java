package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdressResponce {
	public List<AddressData> results; //
	public String status; //APIのステータス
	public String message; //APIのエラーメッセージ（エラーがある
	
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	//①なぜ二回同じアノテーションを書いている？
    public static class AddressData {
        public String address1; // 都道府県
        public String address2; // 市区町村
        public String address3; // 町域
    }
}
