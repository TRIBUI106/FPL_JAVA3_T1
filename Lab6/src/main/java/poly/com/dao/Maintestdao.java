package poly.com.dao;

public class Maintestdao {

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

		DeparmentsDao Dedao = new DeparmentsDao();
		
		//Dedao.insertDepartment("006", "Phòng CNTT", "Phòng công nghệ thông tin");
		
		//Dedao.deleteDepartment("005");
		//Dedao.updateDepartment("001", "Lê Anh Tú 123", "Lê Anh Tú");
		//Dedao.findDepartmentById("007");
		//Dedao.addDepartmentStore("007", "Đàm Vĩnh Hưng", "VH");
		Dedao.printAllDepartments();
	}

}
