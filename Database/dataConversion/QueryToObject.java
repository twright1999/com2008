package dataConversion;
import Accounts.*;
import Utility.*;
import java.sql.*;
/**
 * 
 * @author Rokas
 * For converting query results to an Object, so that it could be used
 * in GUI display.
 * Should be static?
 */
public final class QueryToObject {
	
	public static Student rowToStudent(ResultSet res) {
		while(res.next()) {
			String accountId = res.getString("userId");
			String name = res.getString("name");
			String password = res.getString("password");
			String title = res.getString("title");
			/*How to get degree from Student?
			 *  By linking Degree and Student tables?
			 */
			String email = res.getString("email");
			String tutor = res.getString("tutor");
			//Pull periodOfStudy from PeriodOfStudy table
		}
		Student student = new Student(accountId, name, password ,title, degree,
				email, tutor, periodOfStudy);
		return student;
	}
	
	public static Degree rowToDegree(ResultSet res) {
		while(res.next()) {
			
		}
		Degree degree = new Degree(/*Parameters*/);
		return degree;
	}
	
	public static PeriodOfStudy rowToPeriod(ResultSet res) {
		while(res.next()) {
			char label = res.getString("label").charAt(0); //converting to char
			char levelOfStudy = res.getString("level").charAt(0);
			String startDate = res.getString("start-date"); //Isn't it type Date?
			String endDate = res.getString("end-date"); 
			PeriodOfStudy poS =
					new PeriodOfStudy(label, levelOfStudy, startDate, endDate);
		}
		
		return poS;
	}
}
