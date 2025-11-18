package controller;

import java.io.IOException;
import java.net.URI;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/calculate", "/calculate/add", "/calculate/sub",  "/bai2"})
public class bai2 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("message", "Nhập số và chọn phép tính");
		req.getRequestDispatcher("/bai2.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String aInput = req.getParameter("a");
		String bInput = req.getParameter("b");
		
		if ( aInput.isBlank() || bInput.isBlank() ) {
			req.setAttribute("message", "Bạn chưa nhập 1 trong 2 tham số");
			req.getRequestDispatcher("/bai2.jsp").forward(req, resp);
			return;
		}
		
		String uri = req.getRequestURI();
		
		int a = 0, b = 0;
		
		try {
			a = Integer.parseInt(aInput);
			b = Integer.parseInt(bInput);
			
			if ( uri.contains("add") ) {
				int num = a + b;
				req.setAttribute("message", "Bạn đã chọn dấu '+', kết quả là " + num);
			}
			
			if ( uri.contains("sub") ) {
				int num = a - b;
				req.setAttribute("message", "Bạn đã chọn dấu '-', kết quả là " + num);
			}
			
			req.getRequestDispatcher("/bai2.jsp").forward(req, resp);
			return;
		} catch (NumberFormatException ex) {
			req.setAttribute("message", "Bạn phải nhập số, chữ là sai bét");
			req.getRequestDispatcher("/bai2.jsp").forward(req, resp);
			return;
		}
		
		
	}	
	
	
}
