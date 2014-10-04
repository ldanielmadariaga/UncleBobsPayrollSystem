package businessObjects;

import java.util.Hashtable;
import java.util.Set;

public class PayrollDatabase {

	private static Hashtable<Integer, Employee> employees = new Hashtable<Integer, Employee>();
	private static Hashtable<Integer, Employee> unionMembers = new Hashtable<Integer, Employee>();

	public static void addEmployee(int empId, Employee employee) {
		employees.put(empId, employee);
	}

	public static Employee getEmployee(int empId) {
		return employees.get(empId);
	}

	public static Set<Integer> getAllEmployeeIds() {
		return employees.keySet();
	}

	public static void deleteEmployee(int empId) {
		employees.remove(empId);
	}

	public static void addUnionMember(int memberId, Employee employee) {
		unionMembers.put(memberId, employee);
	}

	public static Employee getUnionMember(int memberId) {
		return unionMembers.get(memberId);
	}

	public static void removeUnionMember(int memberId) {
		unionMembers.remove(memberId);
	}

}
