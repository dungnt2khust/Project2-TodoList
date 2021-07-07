package nguyentiendung.example.todo_navigation;

public class Todo {
    int id;
    String title;
    String content;
    boolean isCheck;
    boolean favourite;
    String date;
    String location;
    String time;
    String topic;
    int topic_id;

    public Todo(int id, String title, String content, boolean isCheck, boolean favourite, String date, String location, String time, String topic, int topic_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isCheck = isCheck;
        this.favourite = favourite;
        this.date = date;
        this.location = location;
        this.time = time;
        this.topic = topic;
        this.topic_id = topic_id;
    }

    public Todo() {};

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

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
