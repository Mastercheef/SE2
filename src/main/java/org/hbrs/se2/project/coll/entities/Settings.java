package org.hbrs.se2.project.coll.entities;

import javax.persistence.*;

@Entity
@Table( name ="col_tab_settings" , schema = "collhbrs" )
public class Settings {

    private int id;
    private User user;
    private boolean notificationIsEnabled;

    @Id
    @Column(name = "user_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Basic
    @Column(name = "notification_is_enabled")
    public boolean getNotificationIsEnabled() { return notificationIsEnabled; }
    public void setNotificationIsEnabled(boolean notificationIsEnabled) {
        this.notificationIsEnabled = notificationIsEnabled;
    }

}
