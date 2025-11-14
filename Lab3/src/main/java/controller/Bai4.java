package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bai4")
public class Bai4 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		Map<String, Object> map = new HashMap<>(); 
		map.put("title", "Đây là tiêu đề của bản tin"); 
		map.put("content", "Nội dung bản tin thường rất dài, ví dụ như cổ phiếu FPT tuột về thấp, Bitcoin thì rớt từ $107.000 về $94.000 khiến Trí mất 15tr trong đêm"); 
		req.setAttribute("item", map);
		req.getRequestDispatcher("bai4.jsp").forward(req, resp);
	}
	
}
