package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderDto;
import com.example.demo.mapper.OrderMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelService {
	private final OrderMapper orderMapper;
	
	public byte[] createShippingLabel(Integer orderId) throws IOException {
		// 1. Service内でDBから最新データを取得
	    OrderDto order = orderMapper.findByOrderId(orderId);        
	    if (order == null) throw new RuntimeException("注文データが見つかりません");
	    
	    ClassPathResource res = new ClassPathResource("shipping_template.xlsx");
	    try (Workbook workbook = new XSSFWorkbook(res.getInputStream())) {
	        Sheet sheet = workbook.getSheetAt(0);
	        
	        // 2. ヘルパーメソッドで値をセット
	        setCellValue(sheet, 1, 1, order.getId());
	        // 今後項目が増えてもここに書き足すだけ！
	        
	     // 確認：setCellValue の後にちゃんと値が入っているかデバッグログを出してみる
	        Integer value = order.getId();
	        System.out.println("DEBUG: 書き込む名前は " + value); 
	        System.out.println("DEBUG: 書き込むIDは " + order.getId()); 
	        setCellValue(sheet, 1, 1, value);

	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        workbook.write(bos);
	        return bos.toByteArray();
	    }
    }
	// 存在しない行・セルを考慮した安全な書き込みメソッド
	private void setCellValue(Sheet sheet, int rowIdx, int colIdx, Integer value) {
	    Row row = sheet.getRow(rowIdx) != null ? sheet.getRow(rowIdx) : sheet.createRow(rowIdx);
	    Cell cell = row.getCell(colIdx) != null ? row.getCell(colIdx) : row.createCell(colIdx);
	    
	    
	    
	    cell.setCellValue(value != null ? value : 0);
	    
	    
	}
}
