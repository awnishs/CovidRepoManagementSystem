package com.terrapay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.terrapay.domain.H2CreateExample;
import com.terrapay.h2Util.H2JDBCUtils;

public class CovidCasesRepoManagement {

	private static final String INSERT_PATIENT_DETAILS = "INSERT INTO covid_repo"
			+ "  (Aadhaar_Id, Name, State, city, Pincode, Status) VALUES " + " (?, ?, ?, ?, ?, ?);";

	private static final String UPDATE_PATIENT_STATUS = "Update covid_repo SET Status = '{Status}' where Aadhaar_Id = '{Aadhaar_Id}';";

	private static final String SEARCH_PATIENT = "Select * from covid_repo where {SearchCriteria}='{value}';";

	public static void main(String[] args) throws Exception {

		H2CreateExample createTableExample = new H2CreateExample();
		createTableExample.createTable();

		int choice = 0;
		int internalChoice = 0;

		CovidCasesRepoManagement covidCasesRepoManagement = new CovidCasesRepoManagement();

		covidCasesRepoManagement.mainMenu(choice, internalChoice);
	}

	private void mainMenu(int choice, int internalChoice) {

		Scanner n = new Scanner(System.in);

		if (choice == 0) {
			System.out.println("Press 1 to add a patient " + "\n" + "\n"
					+ "Press 2 to update status of an existing patient" + "\n" + "\n" + "Press 3 to search patients");

			choice = n.nextInt();
		}

		else if (choice != 0 && internalChoice == 0) {
			System.out.println("Press 1 to add a patient " + "\n" + "\n"
					+ "Press 2 to update status of an existing patient" + "\n" + "\n" + "Press 3 to search patients");

			choice = n.nextInt();

		}

		String entry = "";
		switch (choice) {

		case 1:
			try (Connection connection = H2JDBCUtils.getConnection();

					PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PATIENT_DETAILS)) {

				System.out.println("Aadhaar Id : ");
				entry = n.next();
				preparedStatement.setString(1, entry);

				System.out.println("Name : ");
				entry = n.next();
				preparedStatement.setString(2, entry);
				System.out.println("State : ");
				entry = n.next();
				preparedStatement.setString(3, entry);

				System.out.println("City : ");
				entry = n.next();
				preparedStatement.setString(4, entry);

				System.out.println("Pincode : ");
				entry = n.next();
				preparedStatement.setString(5, entry);

				System.out.println("Status : ");
				entry = n.next();
				preparedStatement.setString(6, entry);

				preparedStatement.executeUpdate();

				System.out.println("Patient Details added successfully! \n" + "Press 1 to add another patient \n"
						+ "Press 0 for main menu \n");

				internalChoice = n.nextInt();

				mainMenu(choice, internalChoice);

			} catch (SQLException e) {

				// print SQL exception information
				H2JDBCUtils.printSQLException(e);
			}
			break;
		case 2:

			String updatePatineStatus = UPDATE_PATIENT_STATUS;

			System.out.println("Aadhaar Id : ");
			entry = n.next();
			updatePatineStatus = updatePatineStatus.replace("{Aadhaar_Id}", entry);

			System.out.println("Status : ");
			entry = n.next();
			updatePatineStatus = updatePatineStatus.replace("{Status}", entry);

			try (Connection connection = H2JDBCUtils.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(updatePatineStatus)) {

				preparedStatement.executeUpdate();

				System.out.println("Patient Status Updated successfully! \n" + "Press 1 to update another patient \n"
						+ "Press 0 for main menu \n");

				internalChoice = n.nextInt();

				mainMenu(choice, internalChoice);

			} catch (SQLException e) {

				H2JDBCUtils.printSQLException(e);
			}
			break;

		case 3:

			String searchPatient = SEARCH_PATIENT;

			System.out.println("Search Criteria : ");
			entry = n.next();
			searchPatient = searchPatient.replace("{SearchCriteria}", entry);

			System.out.println("Value : ");
			entry = n.next();
			searchPatient = searchPatient.replace("{value}", entry);
			try (Connection connection = H2JDBCUtils.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(searchPatient)) {

				System.out.println(searchPatient);

				ResultSet rs = preparedStatement.executeQuery();
				System.out.println("Aadhaar_Id Name State city Pincode Status");
				while (rs.next()) {
					System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " "
							+ rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
				}
				System.out.println("Press 1 to Search again \n" + "Press 0 for main menu \n");
				internalChoice = n.nextInt();

				mainMenu(choice, internalChoice);

			} catch (SQLException e) {

				H2JDBCUtils.printSQLException(e);
			}
			break;
		default:
			System.out.println("Wrong choice..");

		}

		n.close();
	}

}
