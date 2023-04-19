package rs.raf.projekat1.rafplaner.model;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

import rs.raf.projekat1.rafplaner.HelperFunctions;

public class Task {

        private String title;
        private String description;
        private Date date;
        private int startTime; // in minutes
        private int endTime;
        private Priority priority;

        private int id;

        public Task() {
        }

        public Task(int id, String title, String description, Date date, int startTime, int endTime, Priority priority) {
                this.id = id;
                this.title = title;
                this.description = description;
                this.date = date;
                this.startTime = startTime;
                this.endTime = endTime;
                this.priority = priority;
            }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = HelperFunctions.trimDate(date);
        }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Priority getPriority() {
            return priority;
        }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(date, task.date) && Objects.equals(startTime, task.startTime) && Objects.equals(endTime, task.endTime) && priority == task.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, date, startTime, endTime, priority, id);
    }

    public boolean isFinished(){
        return new Date().after(new Date(date.getTime() + (long) endTime *60*1000));
    }
}
