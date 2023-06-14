package com.evkhaled.taskapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "property_views")
public class PropertyView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "property_id")
    private Long propertyId;

    @NotNull
    private String user;

    @Column(name = "view_count")
    private Integer count;

    public PropertyView(@NotNull Long propertyId, @NotNull String user, Integer count) {
        this.propertyId = propertyId;
        this.user = user;
        this.count = count;
    }

    public PropertyView() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(@NotNull Long propertyId) {
        this.propertyId = propertyId;
    }

    public @NotNull String getUser() {
        return user;
    }

    public void setUser(@NotNull String user) {
        this.user = user;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

