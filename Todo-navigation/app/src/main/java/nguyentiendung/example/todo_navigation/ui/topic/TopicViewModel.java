package nguyentiendung.example.todo_navigation.ui.topic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TopicViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TopicViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}