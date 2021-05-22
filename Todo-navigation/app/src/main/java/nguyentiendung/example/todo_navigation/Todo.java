package nguyentiendung.example.todo_navigation;

public class Todo {
    int id;
    String title;
    String content;
    boolean isCheck;
    boolean favourite;

    public Todo(int id, String title, String content, boolean isCheck, boolean favourite) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isCheck = isCheck;
        this.favourite = favourite;
    }

    public Todo() {};

    public boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
