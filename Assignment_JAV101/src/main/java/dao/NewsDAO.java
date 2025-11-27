package dao;

import entity.News;
import utils.XJdbc;
import java.util.List;

public class NewsDAO {
	// Lấy bản tin mới nhất
	private static final String SELECT_ALL = "SELECT * FROM NEWS ORDER BY PostedDate DESC";
	private static final String INSERT_SQL = "INSERT INTO NEWS (Id, Title, Content, Image, PostedDate, Author, ViewCount, CategoryId, Home) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE NEWS SET Title = ?, Content = ?, Image = ?, PostedDate = ?, Author = ?, "
			+ "ViewCount = ?, CategoryId = ?, Home = ? WHERE Id = ?";
	private static final String DELETE_SQL = "DELETE FROM NEWS WHERE Id = ?";
	private static final String SELECT_BY_ID = "SELECT * FROM NEWS WHERE Id = ?";

	public List<News> getAll() {
		return XJdbc.getBeanList(News.class, SELECT_ALL);
	}

	public News findById(String id) {
		return XJdbc.getSingleBean(News.class, SELECT_BY_ID, id);
	}

	public boolean insert(News n) {
		// 1. Kiểm tra Id đã tồn tại chưa
		News existing = XJdbc.getSingleBean(News.class, SELECT_BY_ID, n.getId());
		if (existing != null) {
			// Nếu đã có bản tin với Id này thì không cho thêm
			return false;
		}
		// 2. Nếu chưa có thì tiến hành insert
		int rows = XJdbc.executeUpdate(INSERT_SQL, n.getId(), n.getTitle(), n.getContent(), n.getImage(),
				n.getPostedDate(), n.getAuthor(), n.getViewCount(), n.getCategoryId(), n.isHome());

		return rows > 0;
	}

	public void update(News n) {
		XJdbc.executeUpdate(UPDATE_SQL, n.getTitle(), n.getContent(), n.getImage(), n.getPostedDate(), n.getAuthor(),
				n.getViewCount(), n.getCategoryId(), n.isHome(), n.getId());
	}

	public void delete(String id) {
		XJdbc.executeUpdate(DELETE_SQL, id);
	}

}
