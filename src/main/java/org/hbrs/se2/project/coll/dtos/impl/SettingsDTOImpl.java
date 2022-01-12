package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.SettingsDTO;

public class SettingsDTOImpl implements SettingsDTO {
    private int id;
    private boolean notificationIsEnabled;

    public void setId(int id) { this.id = id; }

    public void setNotificationIsEnabled(boolean notificationIsEnabled) {
        this.notificationIsEnabled = notificationIsEnabled;
    }

    @Override
    public int getId() { return id; }

    @Override
    public boolean getNotificationIsEnabled() { return notificationIsEnabled; }
}
