package dao;

import entity.Category;
import utils.XJdbc;
import java.util.List;

public class CategoryDAO {

    private static final String SELECT_ALL = "SELECT * FROM CATEGORIES ORDER BY Name";
    private static final String INSERT_SQL = "INSERT INTO CATEGORIES (Id, Name) VALUES (?, ?)";
    private static final String UPDATE_SQL = "UPDATE CATEGORIES SET Name = ? WHERE Id = ?";
    private static final String DELETE_SQL = "DELETE FROM CATEGORIES WHERE Id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM CATEGORIES WHERE Id = ?";

    public List<Category> getAll() {
        return XJdbc.getBeanList(Category.class, SELECT_ALL);
    }

    public Category findById(String id) {
        return XJdbc.getSingleBean(Category.class, SELECT_BY_ID, id);
    }

    public void insert(Category c) {
        XJdbc.executeUpdate(INSERT_SQL, c.getId(), c.getName());
    }

    public void update(Category c) {
        XJdbc.executeUpdate(UPDATE_SQL, c.getName(), c.getId());
    }

    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    public boolean exists(String id) {
        String sql = "SELECT COUNT(*) FROM CATEGORIES WHERE Id = ?";
        Object result = XJdbc.getValue(sql, id); // hoặc tạo hàm mới
        Long count = result == null ? 0L : (Long) result;
        return count > 0;
    }

    // SINH ID TỰ ĐỘNG SIÊU THÔNG MINH
    public String generateId(String name) {
        String[] words = name.trim().split("\\s+");
        StringBuilder base = new StringBuilder();

        // Lấy chữ cái đầu mỗi từ → viết hoa
        for (String word : words) {
            if (!word.isEmpty()) {
                base.append(Character.toUpperCase(word.charAt(0)));
            }
        }

        String id = base.toString();
        if (id.isEmpty()) id = "CAT";

        // Kiểm tra trùng
        if (!exists(id)) {
            return id; // không trùng → dùng luôn
        }

        // Bị trùng → thử kiểu nâng cao: chữ đầu + chữ cuối của từ đầu + chữ đầu từ tiếp
        if (words.length >= 1) {
            String firstWord = words[0].toUpperCase();
            char first = firstWord.charAt(0);
            char last = firstWord.charAt(firstWord.length() - 1);

            if (words.length >= 2) {
                char secondFirst = words[1].toUpperCase().charAt(0);
                id = "" + first + last + secondFirst; // ví dụ: Thông tin → TgT
            } else {
                id = "" + first + last + "X"; // nếu chỉ 1 từ → thêm X
            }
        }

        // Nếu vẫn trùng → thêm số
        int i = 1;
        String temp = id;
        while (exists(temp)) {
            temp = id + i;
            i++;
        }
        return temp;
    }
}