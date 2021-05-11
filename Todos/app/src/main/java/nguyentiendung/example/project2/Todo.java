package nguyentiendung.example.project2;

public class Todo {
    String title;
    String content;
    Boolean isCheck;

    public Todo() {};
    public Todo(String title, String content) {
        this.title = title;
        this.content = content;
        this.isCheck = false;
    };

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

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }
}
