package gov.vghtc.ebedsidecardspring.patient.repository.cpoe;

import java.util.List;
import java.util.Map;

//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.vghtc.vghtcspring.jpa.repository.cpoe.CpoeDummyRepository;

@Repository
public interface NurseBoardNativeRepository extends CpoeDummyRepository {

	
	
		@Query(value = """
				SELECT HCASENO 
				FROM common.adm_bed a
				WHERE a.HHISNUM = :hhisnum
				""", nativeQuery = true)
		List<Map<String, Object>> getHcaseno(@Param("hhisnum") String hhisnum);
		
        // Get EWS by caseno
        @Query(value = """
                SELECT stat_dt, hcaseno, tot_sc, nurbed, status
                FROM ews_score t1
                WHERE EXISTS (
                    SELECT 1
                    FROM ews_score t2
                    WHERE t1.hhisnum = t2.hhisnum
                    AND t1.stat_dt = (
                        SELECT MAX(stat_dt)
                        FROM ews_score t3
                        WHERE t2.hhisnum = t3.hhisnum
                    )
                )
                AND t1.hcaseno = :hcaseno
                """, nativeQuery = true)
        List<Map<String, Object>> getEwsScore(@Param("hcaseno") String hcaseno);

        @Query(value = """
                SELECT a.HNURSTAT, a.HBEDNO, a.HBEDSTAT, a.HCASENO, a.HHISNUM, ' ' AS hnamec, a.HCURSVCL, a.hsex, p.hbldtype, p.hpatisol,
                (TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')) - TO_NUMBER(SUBSTR(p.HBIRTHDT, 1, 4))) AS age, c.hadmdt, p.hnameu,
                n.hvdocnm, n.hrdocnm, w.label_id, w.epd_size
                FROM common.adm_bed a
                LEFT JOIN common.pat_basic p ON a.hhisnum = p.hhisnum
                LEFT JOIN common.pat_adm_case c ON a.hhisnum = c.hhisnum
                LEFT JOIN common.nowadm n ON a.hcaseno = n.caseno
                JOIN cpoe.epaper_wifi_pair w ON a.HNURSTAT = w.HNURSTAT AND a.HBEDNO = w.hbedno
                WHERE a.hcaseno = :hcaseno AND w.mode_type <> 'N'
                ORDER BY a.HNURSTAT, a.HBEDNO
                """, nativeQuery = true)
        List<Map<String, Object>> getBedInfo(@Param("hcaseno") String hcaseno);



        // 病人語言處理
        @Query(value = """
                        SELECT t.chinese, t.taiwanese, t.hakka, t.otherlanguage
                        FROM vghtc.nursingnotedata t
                        WHERE t.ENCOUNTERID = :hcaseno
                        """, nativeQuery = true)
        Map<String, Object> getNursingNoteData(@Param("hcaseno") String hcaseno);

        // 病人DNR處理
//        @Query(value = "SELECT COUNT(1) as cnt FROM common.dnrroot WHERE DNRSTAt = 'A' AND hhisnum = :hhisnum", nativeQuery = true)
//        int getDnrCount(@Param("hhisnum") String hhisnum);

        // 病人ADR處理
//        @Query(value = "SELECT COUNT(1) as cnt FROM cpoe.ALLERGYLOG WHERE STATUS <> 'D' AND ALLERGY_TYPE = '3' AND hhisnum = :hhisnum", nativeQuery = true)
//        int getAllergyCount(@Param("hhisnum") String hhisnum);
        
        //過敏跟DNR
        @Query(value = """
                SELECT a.hcaseno AS hcaseno, NVL(d.dnr_count, 0) AS dnr_count, NVL(al.allergy_count, 0) AS allergy_count 
                FROM common.adm_bed a 
                LEFT JOIN (SELECT d.hhisnum, COUNT(*) AS dnr_count 
                           FROM common.dnrroot d 
                           WHERE d.DNRSTAt = 'A' 
                           GROUP BY d.hhisnum) d ON a.hhisnum = d.hhisnum 
                LEFT JOIN (SELECT a.hhisnum, COUNT(*) AS allergy_count 
                           FROM cpoe.ALLERGYLOG a 
                           WHERE a.STATUS <> 'D' AND a.ALLERGY_TYPE = '3' 
                           GROUP BY a.hhisnum) al ON a.hhisnum = al.hhisnum 
                WHERE a.hcaseno = :hcaseno
                """, nativeQuery = true)
        List<Map<String, Object>> getDnrAllCount(@Param("hcaseno") String hcaseno);


        
        
        // 預防跌倒
        @Query(value = """
        	    SELECT CASE WHEN SCORE > 0 THEN 'Y' ELSE 'N' END AS fall_yn
        	    FROM (
        	        SELECT SUM(EVENT_VALUE) AS SCORE
        	        FROM VGHTC.NURSEVALUATIONDATA
        	        WHERE EVENT_ID = (
        	            SELECT EVENT_ID
        	            FROM (
        	                SELECT E.EVENT_ID
        	                FROM VGHTC.NURSEVALUATIONEVENT E
        	                WHERE E.ENCOUNTER_ID = (
        	                    SELECT A.HCASENO
        	                    FROM common.ADM_BED A
        	                    WHERE A.HCASENO = :hcaseno
        	                ) AND E.TYPE = 'fall'
        	                ORDER BY E.OCCUR_DATE DESC
        	            ) WHERE ROWNUM = 1
        	        )
        	    )
        	    """, nativeQuery = true)
        	String getFallRisk(@Param("hcaseno") String hcaseno);


        // 禁治療&禁食禁水
        @Query(value = """
                        SELECT treatment, fasting, bed, evd, NVL(SUBSTR(shift, 1, INSTR(shift, '|') - 1), shift) AS shift 
                        FROM vghtc.nurepaperconfig
                        WHERE status = 'Y' AND encounter_id = 'I' AND hcaseno = :hcaseno
                        """, nativeQuery = true)
        Map<String, Object> getNurePaperConfig(@Param("hcaseno") String hcaseno);

     // 處理管路資料
        @Query(value = """
            SELECT t.hcaseno, t.hhisnum, t.tube, t.nb, t.deep, t.scheduled_date, b.hnurstat, b.hbedno, b.hnamec, t.ondate, t.ontm, t.id,
                   SUBSTR(SUBSTR(TRIM(t.changdate), -15), 1, 10) AS changdate,
                   CASE
                       WHEN t.tube IN ('CVP', 'CVC', 'Double Luman CVC', 'Double Lumen CVC', 'Double Luman Hemodialysis Catheter',
                                        'Double Lumen Hemodialysis Catheter', 'Double-Lumen',
                                        'Edward volume view (PiCCO)', 'hickman', 'Hickman (Double lumen)', 'Hickman (Single lumen)',
                                        'Hickman(Hemodialysis Catheter)', 'Port Needle(角針)', 'Swan-Ganz', 'Swan-Ganz (CCO)',
                                        'Swan-Ganz (PiCCO)', 'Triple-Luman CVC', 'Triple-Lumen CVC', 'UAC(Umbilical arterial catheter)', 'UVC(Umbilical vein catheter)') THEN '中心導管'
                       WHEN t.tube IN ('Endo.', '塑膠氣切', 'Tracheostomy Tube',
                                        'Tracheostomy Tube - BIVONA TTS Adjustable', 'Tracheostomy Tube - PORTEX Adjustable',
                                        'Tracheostomy Tube -BIVONA TTS', 'Tracheostomy Tube -SHILEY', 'Tracheostomy Tube -SHILEY CFN',
                                        'Tracheostomy Tube -SHILEY FEN') THEN '氣切'
                       WHEN t.tube IN ('3 Way Foley', 'Foley', 'Silicon Foley', 'Silicone Foley') THEN '導尿管'
                       WHEN t.tube IN ('PICC') THEN 'PICC'
                       WHEN t.tube IN ('Endo.', 'Endotracheal Tube') THEN '氣管內管'
                       ELSE '0'
                   END AS bundleyn
            FROM vghtc.nursshifttube t
            JOIN common.adm_bed b ON t.hhisnum = b.hhisnum
            WHERE (t.removedate IS NULL OR t.removedate = '')
              AND (t.otflag IS NULL OR t.otflag = '')
              AND t.tube IN ('CVP', 'CVC', 'Double Luman CVC', 'Double Lumen CVC', 'Double Luman Hemodialysis Catheter',
                             'Double Lumen Hemodialysis Catheter', 'Double-Lumen',
                             'Edward volume view (PiCCO)', 'hickman', 'Hickman (Double lumen)', 'Hickman (Single lumen)',
                             'Hickman(Hemodialysis Catheter)', 'PICC', 'Port Needle(角針)', 'Swan-Ganz', 'Swan-Ganz (CCO)',
                             'Swan-Ganz (PiCCO)', 'Triple-Luman CVC', 'Triple-Lumen CVC', 'UAC(Umbilical arterial catheter)', 'UVC(Umbilical vein catheter)',
                             'Endo.', '塑膠氣切', 'Endotracheal Tube', 'Tracheostomy Tube',
                             'Tracheostomy Tube - BIVONA TTS Adjustable', 'Tracheostomy Tube - PORTEX Adjustable',
                             'Tracheostomy Tube -BIVONA TTS', 'Tracheostomy Tube -SHILEY', 'Tracheostomy Tube -SHILEY CFN',
                             'Tracheostomy Tube -SHILEY FEN', '3 Way Foley', 'Foley', 'Silicon Foley', 'Silicone Foley')
              AND t.hcaseno = :hcaseno
            """, nativeQuery = true)
        List<Map<String, Object>> getNursShiftTube(@Param("hcaseno") String hcaseno);

        // np資料處理
        @Query(value = """
                        SELECT HGMDNO, HGDOCNM, HGMDNO2, HGDOCNM2 FROM common.pat_adm_case b
                        INNER JOIN COMMON.PAT_ADM_DOCTOR a ON a.hcaseno = b.HCASENO
                        WHERE a.hdocdate = (SELECT MAX(hdocdate) FROM COMMON.PAT_ADM_DOCTOR WHERE hcaseno = b.HCASENO)
                        AND b.hcaseno = :hcaseno
                        """, nativeQuery = true)
        Map<String, Object> getPatAdmDoctor(@Param("hcaseno") String hcaseno);
       

        // 主責護理師處理
//        @Query(value = """
//                        SELECT c.nuid, b.namec, c.classtype, c.hbedno FROM cpoe.nurs_class c
//                        JOIN common.psbasic_vghtc b ON c.nuid = b.cardno
//                        WHERE c.adddate = :today 
//                        AND c.hnurstat = :hnurstat
//                        AND (INSTR(c.hbedno, :hbedno) > 0 
//                        AND (
//                            (SUBSTR(c.hbedno, INSTR(c.hbedno, :hbedno) - 1, 1) = ',' OR INSTR(c.hbedno, :hbedno) = 1)
//                        AND SUBSTR(c.hbedno, INSTR(c.hbedno, :hbedno) + LENGTH(:hbedno), 1) = ','
//                            OR INSTR(c.hbedno, :hbedno) + (LENGTH(:hbedno) - 1) = LENGTH(c.hbedno)
//                        ))
//                        AND (TO_CHAR(SYSDATE, 'hh24mi') BETWEEN SUBSTR(c.class_detail, 1, 4) AND SUBSTR(c.class_detail, 6, 4))
//                        """, nativeQuery = true)
//        List<Map<String, Object>> getNursClassDetail(@Param("today") String today, @Param("hnurstat") String hnurstat,
//                        @Param("hbedno") String hbedno);
        
        @Query(value = """
                SELECT c.nuid, b.namec, c.classtype, c.hbedno 
				FROM cpoe.nurs_class c
				JOIN common.psbasic_vghtc b ON c.nuid = b.cardno
				JOIN common.ADM_BED ab ON ab.HCASENO = :hcaseno
				WHERE c.adddate = '20240723'
				AND (
				    c.hbedno = ab.HBEDNO 
				    OR INSTR(c.hbedno, ',' || ab.HBEDNO || ',') > 0
				    OR INSTR(c.hbedno, ab.HBEDNO || ',') = 1
				    OR INSTR(c.hbedno, ',' || ab.HBEDNO) = LENGTH(c.hbedno) - LENGTH(ab.HBEDNO)
				)
				AND (TO_CHAR(SYSDATE, 'hh24mi') BETWEEN SUBSTR(c.class_detail, 1, 4) AND SUBSTR(c.class_detail, 6, 4))
                """, nativeQuery = true)
List<Map<String, Object>> getNursClassDetail(@Param("hcaseno") String hcaseno);
        
        
        //epaperwifi table
        @Query(value = """
                SELECT KEY1_IMAGE_URL
				FROM EPAPER_WIFI_PAIR e
				JOIN common.ADM_BED a ON e.HBEDNO = a.HBEDNO
				WHERE a.HCASENO = :hcaseno
				
                """, nativeQuery = true)
List<Map<String, Object>> getEpaperwifi(@Param("hcaseno") String hcaseno);



        


        }



