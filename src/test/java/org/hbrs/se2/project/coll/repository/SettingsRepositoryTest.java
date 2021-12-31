package org.hbrs.se2.project.coll.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY )
@Sql( {"/schema.sql" , "/data.sql"})
class SettingsRepositoryTest {

    @Autowired
    SettingsRepository settingsRepository;

    @Test
    void findSettingsByIdNotNull() {
        assertNotNull(settingsRepository.findSettingsById(20000000));
    }

    @Test
    void findSettingsByIdNull() {
        assertNull(settingsRepository.findSettingsById(20001230));
    }

    @Test
    void findSettingsByIdTrue() {
        assertTrue(settingsRepository.findSettingsById(20000000).getNotificationIsEnabled());
    }
    @Test
    void findSettingsByIdFalse() {
        assertFalse(settingsRepository.findSettingsById(20000002).getNotificationIsEnabled());
    }


}