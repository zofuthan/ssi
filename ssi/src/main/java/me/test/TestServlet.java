package me.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class TestServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			DataSource source = (DataSource) req.getServletContext().getAttribute("source");
			Connection conn = source.getConnection();
			Statement s = conn.createStatement();
/*			s.execute("INSERT INTO CODE_CITY VALUES('010','北京')");
			s.execute("INSERT INTO CODE_CITY VALUES('020','天津')");
			conn.commit();*/
			ResultSet r = s.executeQuery("select * from CODE_CITY");
			resp.setCharacterEncoding("UTF-8");
			while (r.next()) {
				resp.getWriter().print(r.getMetaData().getColumnLabel(2) + ":" + r.getString(2) + "\n");
			}
			s.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().print("this is a post test!");
	}

	private static final long serialVersionUID = 2288250475678566867L;

}
