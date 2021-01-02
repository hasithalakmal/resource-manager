package com.smile24es.resource.management.beans.to;

import java.util.List;

public class AddResourceTo {
    private List<String> categories;
    private List<String> events;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }
}
