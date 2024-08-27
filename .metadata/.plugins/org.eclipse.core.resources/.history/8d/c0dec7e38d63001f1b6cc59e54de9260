package gov.vghtc.ebedsidecardspring.patient.repository.cpoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ImageUploadRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateEpaperStatus(String bgImageUrl, String labelId) {
        String sql = """
            UPDATE epaper_wifi_pair 
            SET mode_type='S', s_updatecheck='2', s_updatelevel='3', setkey='', KEY1_IMAGE_URL = ?
            WHERE mode_type<>'N' AND label_id = ?
        """;

        jdbcTemplate.update(sql, bgImageUrl, labelId);
    }
}
