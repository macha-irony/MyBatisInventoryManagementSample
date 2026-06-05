//package com.example.demo.controller;
//
//import java.io.IOException;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import com.example.demo.form.OrderForm;
//import com.example.demo.service.ExcelService;
//
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequiredArgsConstructor // Lombokでコンストラクタ注入
//public class ExcelDownloadController {
//    
//    private final ExcelService excelService;
//
//    @GetMapping("/download/shipping-label")
//    public ResponseEntity<byte[]> download(OrderForm form) throws IOException {
//        byte[] excelData = excelService.createShippingLabel(form);
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "attachment; filename=shipping_label.xlsx");
//        
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(excelData);
//    }
//}