package com.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.google.gson.Gson;

@WebServlet(name = "Controller", urlPatterns = { "/uploadFile", "/formSubmit", "/downloadExcel", "/viewDB" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "uploads";
	private static final String DOWNLOAD_DIRECTORY = "downloads";

	public Controller() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath(); // get action from webpages.

		System.out.println("Action is : " + action);
		switch (action) {
		case "/uploadFile":
			fileUpload(request, response);
			break;

		case "/formSubmit":
			formUpload(request, response);
			break;

		case "/downloadExcel":
			downloadExcel(request, response);
			break;
		case "/viewDB":
			viewDB(request, response);
			break;
		default:
			response.sendRedirect("unknownError.jsp");
			break;

		}

	}

	public void viewDB(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Create a Hibernate SessionFactory object using the HibernateUtil 
		Session session = HibernateUtil.getSessionFactory().openSession();
        
		 // Use CriteriaBuilder API to create CriteriaQuery
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = cb.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        criteriaQuery.select(root);
        
        // Create a TypedQuery object to execute the CriteriaQuery and retrieve the result as a list
        List<Employee> employees = session.createQuery(criteriaQuery).getResultList();
        
        // Convert the list of Employee objects to a JSON array using Gson library
        Gson gson = new Gson();
        String jsonData = gson.toJson(employees);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonData);
        
        // Close the Hibernate Session and SessionFactory objects
        session.close();
    }


	public boolean validateExcelFile(Sheet sheet) {
		// Check if the sheet contains only 7 columns
		if (sheet.getRow(0).getLastCellNum() != 7) {
			System.out.println("Excel sheet does not have 7 Columns");
			return false;
		} else {
			System.out.println("Validation : Excel Sheet has 7 columns");
		}

		// Verify the column headers and cells
		String[] expectedHeaders = { "ID", "Name", "Address", "Email ID", "Phone No", "State", "Place" };
		Row headerRow = sheet.getRow(0);
		for (int i = 0; i < 7; i++) {
			Cell cell = headerRow.getCell(i);

			if (cell != null && cell.getCellType() != CellType.BLANK && cell.getCellType() != CellType._NONE
					&& cell.getStringCellValue() != null && !cell.getStringCellValue().equals(expectedHeaders[i])) {
				System.out.println("headerRow Validator : " + expectedHeaders[i] + " is missing from the Columns");
				return false;
			} else if (cell != null && cell.getCellType() != CellType.BLANK && cell.getCellType() != CellType._NONE) {
				System.out.println("headerRow Validator : " + expectedHeaders[i] + " exists");
			} else {
				System.out.println("headerRow Validator : " + expectedHeaders[i] + " is blank or empty");
				return false;
			}
		}

		// Validate the content of each row
		int counter = 0;
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row.getLastCellNum() != 7) {
				System.out.println("Content of Each row not proper");
				return false;
			}
			counter++;
		}

		System.out.println("Validation : Performed on " + counter + " rows");

		// If we reach this point, then the format is correct
		return true;
	}

	public static void ExcelWriter(String uploadPath, String fileName, Sheet sheet, HttpServletRequest request,
			HttpServletResponse response) {

		HashMap<String, Employee> employeeIds = new HashMap<>();
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

				Employee employee = new Employee();

				if (row.getCell(0).getCellType() == CellType.NUMERIC) {
					employee.setId(String.valueOf((int) row.getCell(0).getNumericCellValue()));
				} else {

					System.out
							.println("Skipping Entry " + i + " : First cell is supposed to have Numeric value of IDs.");
					System.out.println("Found type: " + row.getCell(0).getCellType());
					continue; // Skip that entry
				}

				if (row.getCell(1).getCellType() == CellType.NUMERIC) {
					System.out.println(
							"Skipping Entry " + i + " : Second Cell is supposed to have Names, Not Numeric values.");
					System.out.println("Found type: " + row.getCell(1).getCellType());
					continue; // Skip that entry
				} else {
					employee.setName(row.getCell(1).getStringCellValue());
				}

				if (row.getCell(2).getCellType() == CellType.NUMERIC) {
					System.out.println(
							"Skipping Entry " + i + " : Third Cell is supposed to have Address, Not Numeric values.");
					System.out.println("Found type: " + row.getCell(2).getCellType());
					continue; // Skip that entry
				} else {
					employee.setAddress(row.getCell(2).getStringCellValue());
				}

				if (row.getCell(3).getCellType() == CellType.NUMERIC) {
					System.out.println("Skipping Entry " + i
							+ " : Fourth cell is supposed to have Email Address, Not Numeric values");
					System.out.println("Found type: " + row.getCell(3).getCellType());
					continue; // Skip that entry
				} else {
					employee.setEmail(row.getCell(3).getStringCellValue());
				}

				if (row.getCell(4).getCellType() == CellType.NUMERIC) {
					employee.setPhNo(row.getCell(4).getNumericCellValue());
				} else {
					System.out.println("Skipping Entry " + i
							+ " : Fifth cell is supposed to have Numeric Values for Phone number.");
					System.out.println("Found type: " + row.getCell(4).getCellType());
					continue; // Skip that entry
				}

				if (row.getCell(5).getCellType() == CellType.NUMERIC) {
					System.out.println(
							"Skipping Entry " + i + " : Sixth Cell is supposed to have State, Not Numeric values.");
					System.out.println("Found type: " + row.getCell(5).getCellType());
					continue; // Skip that entry
				} else {
					employee.setState(row.getCell(5).getStringCellValue());
				}

				if (row.getCell(6).getCellType() == CellType.NUMERIC) {
					System.out.println(
							"Skipping Entry " + i + " : Seventh Cell us supposed to have Place, Not Numeric values");
					System.out.println("Found type: " + row.getCell(6).getCellType());
					continue; // Skip that entry
				} else {
					employee.setPlace(row.getCell(6).getStringCellValue());
				}

				if (row.getCell(6).getCellType() == CellType.NUMERIC) {
					System.out.println(
							"Skipping Entry " + i + " : Seventh Cell is supposed to have Place, Not Numeric values");
					System.out.println("Found type: " + row.getCell(6).getCellType());
					continue; // Skip that entry
				} else {
					employee.setPlace(row.getCell(6).getStringCellValue());
				}

				// Check if the employee already exists in the database
				String query = "FROM Employee WHERE id = :id";
				Query<Employee> empQuery = session.createQuery(query, Employee.class);
				empQuery.setParameter("id", employee.getId());
				List<Employee> existingEmployees = empQuery.list();

				if (!existingEmployees.isEmpty()) {
					System.out.println("Duplicate entry found for ID: " + employee.getId());
					request.getRequestDispatcher("fileExists.jsp").forward(request, response);
					continue;
				}

				if (employeeIds.containsKey(employee.getId())) {
					System.out.println("Duplicate entry found for ID: " + employee.getId());
					request.getRequestDispatcher("fileExists.jsp").forward(request, response);
					continue;
				} else {
					employeeIds.put(employee.getId(), employee);
					session.save(employee);
					System.out.println("Record number " + i + " : Ready");
				}
			}

			session.getTransaction().commit();
			session.close();
			request.getRequestDispatcher("successfulUpload.jsp").forward(request, response);
			return;
			// HibernateUtil.shutdown(); //Shutdown SessionFactory
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void formUpload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("ID");
		String name = request.getParameter("Name");
		String email = request.getParameter("Email");
		double phone = Double.parseDouble(request.getParameter("Phone"));
		String address = request.getParameter("Address");
		String state = request.getParameter("State");
		String place = request.getParameter("Place");

		// Create a Employee object with the form data
		Employee user = new Employee();
		user.setId(id);
		user.setName(name);
		user.setAddress(address);
		user.setEmail(email);
		user.setPhNo(phone);
		user.setState(state);
		user.setPlace(place);

		// Save the User object to the database using Hibernate
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();

			// check if the object already exists in the database
			Employee existingUser = session.get(Employee.class, id);
			if (existingUser != null) {
				// handle the case where the object already exists
				System.out.println("User already exists in the database.");
				response.sendRedirect("userExists.jsp");
				return;
			}

			// continue with saving the User object to the database
			transaction = session.beginTransaction();
			System.out.println("Saving single Form item.");
			session.save(user);
			transaction.commit();
			response.sendRedirect("successfulUpload.jsp");
		} catch (Exception ex) {
			ex.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
				response.sendRedirect("txFailure.jsp");
			}
			return;
		} finally {
			session.close();
		}
	}

	public void fileUpload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Getting File and File name.
		Part filePart = null;
		try {
			filePart = request.getPart("file");

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ServletException e1) {
			e1.printStackTrace();
		}
		String fileName = null;
		if (filePart != null) {

			fileName = filePart.getSubmittedFileName();

		}
		System.out.println(fileName + " is the name of the selected file");
		String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
		File uploadDir = new File(uploadPath);

		// If upload directory doesn't exist, make it.
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		// Writing file to local disk
		for (Part part : request.getParts()) {
			fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
			part.write(uploadPath + File.separator + fileName);
		}

		FileInputStream file = new FileInputStream(new File(uploadPath + File.separator + fileName));
		try {
			Workbook workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheetAt(0);

			if (validateExcelFile(sheet) == true) {
				ExcelWriter(uploadPath, fileName, sheet, request, response);
			} else {
				System.out.println("File validation failure.");
				response.sendRedirect("fileFailure.jsp");

			}

		} catch (InvalidFormatException e) {
			System.out.println(
					"The selected file has an .xls or .xlsx format but is not a valid Excel file. Please select a valid Excel file.");
			e.printStackTrace();
			response.sendRedirect("invalidExcel.jsp");
		} catch (IOException e) {
			System.out.println("An error occurred while processing the Excel file. Please try again.");
			e.printStackTrace();
			response.sendRedirect("invalidExcel.jsp");
		}
	}

	public void downloadExcel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// If download directory doesn't exist inside server storage, make it.
		String downloadPath = getServletContext().getRealPath("") + DOWNLOAD_DIRECTORY;
		File downloadDir = new File(downloadPath);
		if (!downloadDir.exists()) {
			downloadDir.mkdir();
		}

		// Calling separate class to handle generation of Excel File to server local
		// storage.
		com.upload.excelDownload.main(downloadPath);

		// Get PrintWriter object
		PrintWriter out = response.getWriter();

		// File name of file which was generated
		String excelName = "Rishik-WebApp-Employee-export.xlsx";

		// Set the content type and header of the response.
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + excelName + "\"");

		// Get FileInputStream object to identify the path
		FileInputStream inputStream = new FileInputStream(downloadPath + File.separator + excelName);

		// Loop through the document and write into the
		// output.
		int in;
		while ((in = inputStream.read()) != -1) {
			out.write(in);
		}

		// Close FileInputStream and PrintWriter object
		inputStream.close();
		out.close();
	}

}
