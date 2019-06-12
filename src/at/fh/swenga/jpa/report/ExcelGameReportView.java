package at.fh.swenga.jpa.report;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;


import at.fh.swenga.jpa.model.GameModel;

public class ExcelGameReportView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// change the file name
		response.setHeader("Content-Disposition", "attachment; filename=\"report.xls\"");

		List<GameModel> games = (List<GameModel>) model.get("games");

		// ------------------------------------------------------
		// APACHE POI Documenations and examples:
		// https://poi.apache.org/spreadsheet/index.html
		// ------------------------------------------------------

		// create a worksheet
		Sheet sheet = workbook.createSheet("Game Report");

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColorPredefined.BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		font.setBold(true);
		font.setColor(HSSFColorPredefined.WHITE.getIndex());
		style.setFont(font);

		// create a new row in the worksheet
		Row headerRow = sheet.createRow(0);

		// create a new cell in the row
		Cell cell0 = headerRow.createCell(0);
		cell0.setCellValue("ID");
		cell0.setCellStyle(style);

		// create a new cell in the row
		Cell cell1 = headerRow.createCell(1);
		cell1.setCellValue("Name");
		cell1.setCellStyle(style);

		// create a new cell in the row
		Cell cell2 = headerRow.createCell(2);
		cell2.setCellValue("Developer");
		cell2.setCellStyle(style);

		// create multiple rows with game data
		int rowNum = 1;
		for (GameModel game : games) {
			// create the row data
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(game.getId());
			row.createCell(1).setCellValue(game.getName());
			row.createCell(2).setCellValue(game.getDeveloper());
		}

		// adjust column width to fit the content
		sheet.autoSizeColumn((short) 0);
		sheet.autoSizeColumn((short) 1);
		sheet.autoSizeColumn((short) 2);

	}

}
