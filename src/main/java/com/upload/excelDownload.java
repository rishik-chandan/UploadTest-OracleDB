package com.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

public class excelDownload {

	public static void main(String downloadPath) {
		new excelDownload().export(downloadPath);
	}

	public void export(String downloadPath) {
		
		//Generating Database contents as Excel file on server disk.
 		
		String serverExcelFilePath = downloadPath + File.separator + "Rishik-WebApp-Employee-export.xlsx";

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "FROM Employee";
			List<Employee> employees = session.createQuery(hql, Employee.class).getResultList();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Employee");

			writeHeaderLine(sheet);

			writeDataLines(employees, workbook, sheet);

			FileOutputStream outputStream = new FileOutputStream(serverExcelFilePath);
			workbook.write(outputStream);
			workbook.close();

		} catch (IOException e) {
			System.out.println("File IO error:");
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	private void writeHeaderLine(XSSFSheet sheet) {

		Row headerRow = sheet.createRow(0);

		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue("ID");

		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("Name");

		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("Address");

		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("Email");

		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("Phone No");

		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("State");

		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("Place");
	}

	private void writeDataLines(List<Employee> employees, XSSFWorkbook workbook, XSSFSheet sheet) {
		int rowCount = 1;

		for (Employee employee : employees) {
			String ID = employee.getId();
			String Name = employee.getName();
			String Address = employee.getAddress();
			String Email_ID = employee.getEmail();
			double Phone_No = employee.getPhNo();
			String State = employee.getState();
			String Place = employee.getPlace();

			Row row = sheet.createRow(rowCount++);

			int columnCount = 0;

			Cell cell = row.createCell(columnCount++);
			cell.setCellValue(ID);

			cell = row.createCell(columnCount++);
			cell.setCellValue(Name);

			cell = row.createCell(columnCount++);
			cell.setCellValue(Address);

			cell = row.createCell(columnCount++);
			cell.setCellValue(Email_ID);

			cell = row.createCell(columnCount++);
			cell.setCellValue(Phone_No);

			cell = row.createCell(columnCount++);
			cell.setCellValue(State);

			cell = row.createCell(columnCount++);
			cell.setCellValue(Place);

		}
	}


}