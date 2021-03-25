package com.vorobyev.fwb.model.entity;

import java.util.Calendar;

public class Commend {
    private long id;
    private long publicationId;
    private String body;
    private Calendar date;
    private String publisherName;
    private String publisherImage;
    private int likesCount;
    private boolean liked;
    private String publicationTitle;

    public Commend(long publicationId, String body, String publisherName, Calendar date) {
        this.publicationId = publicationId;
        this.body = body;
        this.publisherName = publisherName;
        this.date = date;
    }

    public Commend(long id, long publicationId, String body, Calendar date, String publisherName, String publisherImage, int likesCount, boolean liked) {
        this.id = id;
        this.publicationId = publicationId;
        this.body = body;
        this.date = date;
        this.publisherName = publisherName;
        this.publisherImage = publisherImage;
        this.likesCount = likesCount;
        this.liked = liked;
    }

    public Commend() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(long publicationId) {
        this.publicationId = publicationId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherImage() {
        return publisherImage;
    }

    public void setPublisherImage(String publisherImage) {
        this.publisherImage = publisherImage;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public void setPublicationTitle(String publicationTitle) {
        this.publicationTitle = publicationTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commend commend = (Commend) o;

        if (id != commend.id) return false;
        if (publicationId != commend.publicationId) return false;
        if (likesCount != commend.likesCount) return false;
        if (liked != commend.liked) return false;
        if (body != null ? !body.equals(commend.body) : commend.body != null) return false;
        if (date != null ? !date.equals(commend.date) : commend.date != null) return false;
        if (publisherName != null ? !publisherName.equals(commend.publisherName) : commend.publisherName != null)
            return false;
        return publisherImage != null ? publisherImage.equals(commend.publisherImage) : commend.publisherImage == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (publicationId ^ (publicationId >>> 32));
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (publisherName != null ? publisherName.hashCode() : 0);
        result = 31 * result + (publisherImage != null ? publisherImage.hashCode() : 0);
        result = 31 * result + likesCount;
        result = 31 * result + (liked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Commend{");
        sb.append("id=").append(id);
        sb.append(", publicationId=").append(publicationId);
        sb.append(", body='").append(body).append('\'');
        sb.append(", date=").append(date);
        sb.append(", publisherName='").append(publisherName).append('\'');
        sb.append(", publisherImage='").append(publisherImage).append('\'');
        sb.append(", likesCount=").append(likesCount);
        sb.append(", liked=").append(liked);
        sb.append('}');
        return sb.toString();
    }
}
