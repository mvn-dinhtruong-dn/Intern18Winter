package asiantech.internship.summer.recycler_view.model;

public class TimelineItem {
    private int mAvatar;
    private int mPicture;
    private int mCountLike;
    private String mComment;
    private String mUsername;

    public TimelineItem(int avatar, String username, int picture, int countLike, String comment) {
        this.mAvatar = avatar;
        this.mUsername = username;
        this.mPicture = picture;
        this.mCountLike = countLike;
        this.mComment = comment;
    }

    public int getAvatar() {
        return mAvatar;
    }

    public void setAvatar(int avatar) {
        this.mAvatar = avatar;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public int getPicture() {
        return mPicture;
    }

    public void setPicture(int picture) {
        this.mPicture = picture;
    }

    public int getCountLike() {
        return mCountLike;
    }

    public void setCountLike(int countLike) {
        this.mCountLike = countLike;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        this.mComment = comment;
    }
}
