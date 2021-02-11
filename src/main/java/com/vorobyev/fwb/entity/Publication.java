package com.vorobyev.fwb.entity;

import java.util.Calendar;

public class Publication {
    private long id;
    private String title;
    private String summary;
    private String content;
    private String previewImagePath;
    private String publisherNickname;
    private Calendar calendar;

    public Publication() {
    }

    public Publication(long id, String title, String summary, String content, String previewImagePath, String publisherNickname, Calendar calendar) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.previewImagePath = previewImagePath;
        this.publisherNickname = publisherNickname;
        this.calendar = calendar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPreviewImagePath() {
        return previewImagePath;
    }

    public void setPreviewImagePath(String previewImagePath) {
        this.previewImagePath = previewImagePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublisherNickname() {
        return publisherNickname;
    }

    public void setPublisherNickname(String publisherNickname) {
        this.publisherNickname = publisherNickname;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Publication that = (Publication) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (previewImagePath != null ? !previewImagePath.equals(that.previewImagePath) : that.previewImagePath != null)
            return false;
        if (publisherNickname != null ? !publisherNickname.equals(that.publisherNickname) : that.publisherNickname != null)
            return false;
        return calendar != null ? calendar.equals(that.calendar) : that.calendar == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (previewImagePath != null ? previewImagePath.hashCode() : 0);
        result = 31 * result + (publisherNickname != null ? publisherNickname.hashCode() : 0);
        result = 31 * result + (calendar != null ? calendar.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Publication{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", summary='").append(summary).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", previewImagePath='").append(previewImagePath).append('\'');
        sb.append(", publisherNickname='").append(publisherNickname).append('\'');
        sb.append(", calendar=").append(calendar);
        sb.append('}');
        return sb.toString();
    }
}
