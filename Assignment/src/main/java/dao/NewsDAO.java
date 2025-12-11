package dao;

import entity.Category;
import entity.News;
import utils.XJdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	//Này cho cái guest 
	private static final String SELECT_HOME = "SELECT * FROM NEWS WHERE Home = 1 ORDER BY PostedDate DESC";
	// Này cho 5 tin mới nhất
	private static final String SELCT_NEWS_LATEST_5 = "SELECT * FROM NEWS ORDER BY PostedDate DESC LIMIT 5";
	// Để tạm load
	private static final String SELECT_ALL_CATE = "SELECT * FROM CATEGORIES ORDER BY Name";
	
	//Count nè
	private static final String COUNT_ALL_SQL = "SELECT COUNT(*) as count FROM NEWS";

	// Tìm theo nhiều cột nếu searchBy = "all" và ép kiểu utf ko là báo lỗi latina
	private static final String SEARCH_ALL =
		    "SELECT N.* FROM NEWS N " +
		    "WHERE CAST(Id AS CHAR CHARACTER SET utf8mb4) LIKE ? " +
		    "OR CAST(Title AS CHAR CHARACTER SET utf8mb4) LIKE ? " +
		    "OR CAST(Author AS CHAR CHARACTER SET utf8mb4) LIKE ? " +
		    "OR CAST(Content AS CHAR CHARACTER SET utf8mb4) LIKE ? " +
		    "OR CAST(CategoryId AS CHAR CHARACTER SET utf8mb4) LIKE ? " +
		    "ORDER BY PostedDate DESC";

	//Này cũng vậy ép kiểu và seach theo id hoặc content 
	private static final String SEARCH_NEWS =
		    "SELECT N.* FROM NEWS N " +
		    "WHERE CAST(Id AS CHAR CHARACTER SET utf8mb4) LIKE ? " +
		    "OR CAST(Content AS CHAR CHARACTER SET utf8mb4) LIKE ? " +
		    "ORDER BY PostedDate DESC";


	// Tìm theo thể loại tin tức
	private static final String SEARCH_CATE =
	    "SELECT N.* FROM NEWS N WHERE CategoryId = ? ORDER BY PostedDate DESC";
	
	// Tìm theo thể loại tin tức và id hoặc content
		private static final String searchByCategoryAndKeyword =
				"SELECT N.* FROM NEWS N " +
		                 "WHERE CategoryId = ? " +
		                 "AND (CAST(Id AS CHAR CHARACTER SET utf8mb4) LIKE ? " +
		                 "OR CAST(Title AS CHAR CHARACTER SET utf8mb4) LIKE ? " +
		                 "OR CAST(Author AS CHAR CHARACTER SET utf8mb4) LIKE ? " +
		                 "OR CAST(Content AS CHAR CHARACTER SET utf8mb4) LIKE ?) " +
		                 "ORDER BY PostedDate DESC";
	
	public List<News> searchNews(String searchBy, String keyword) {
	    String likeKeyword = "%" + keyword + "%";

	    if ("all".equals(searchBy)) {
	        return XJdbc.getBeanList(News.class, SEARCH_ALL,
	                likeKeyword, likeKeyword, likeKeyword, likeKeyword, likeKeyword);
	    } else {
	        // nếu searchBy là cột cụ thể
	        String sql = "SELECT N.* FROM NEWS N WHERE " + searchBy + " LIKE ? ORDER BY PostedDate DESC";
	        return XJdbc.getBeanList(News.class, sql, likeKeyword);
	    }
	}

	public List<News> searchByCategory(String categoryId) {
	    return XJdbc.getBeanList(News.class, SEARCH_CATE, categoryId);
	}

	public List<News> searchByCategoryAndKeyword(String categoryId, String keyword) {
	    String likeKeyword = "%" + keyword + "%";
	    return XJdbc.getBeanList(News.class, searchByCategoryAndKeyword,
	            categoryId, likeKeyword, likeKeyword, likeKeyword, likeKeyword);
	}
	
	public int countAll() throws SQLException {
		ResultSet rs = XJdbc.executeQuery(COUNT_ALL_SQL);
		if ( rs.next() ) {			
			return rs.getInt(1);
		}
		return 0;
	}
	
	// Cái này để tạm
	public List<Category> getAllCate() {
		return XJdbc.getBeanList(Category.class, SELECT_ALL_CATE);
	}

	public List<News> getAll() {
		return XJdbc.getBeanList(News.class, SELECT_ALL);
	}
	
	public List<News> getHomeNews() {
	    return XJdbc.getBeanList(News.class, SELECT_HOME);
	}
	
	public List<News> getLatestNews() {
        return XJdbc.getBeanList(News.class, SELCT_NEWS_LATEST_5);
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
	
	public void incrementViewCount(String newsId) {
	    String sql = "UPDATE NEWS SET ViewCount = ViewCount + 1 WHERE Id = ?";
	    try {
	        XJdbc.executeUpdate(sql, newsId);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
