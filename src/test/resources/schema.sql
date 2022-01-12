CREATE SCHEMA collhbrs;
--
-- Name: col_seq_address_id; Type: SEQUENCE; Schema: collhbrs; Owner: houde2s
--

CREATE SEQUENCE collhbrs.col_seq_address_id
    START WITH 10000000
    INCREMENT BY 1
    MINVALUE 10000000
    MAXVALUE 19999999
    CACHE 1;

--
-- Name: col_seq_advertisement_id; Type: SEQUENCE; Schema: collhbrs; Owner: houde2s
--

CREATE SEQUENCE collhbrs.col_seq_advertisement_id
    START WITH 30000000
    INCREMENT BY 1
    MINVALUE 30000000
    MAXVALUE 39999999
    CACHE 1;

--
-- Name: col_seq_application_id; Type: SEQUENCE; Schema: collhbrs; Owner: houde2s
--

CREATE SEQUENCE collhbrs.col_seq_application_id
    START WITH 60000000
    INCREMENT BY 1
    MINVALUE 60000000
    MAXVALUE 69999999
    CACHE 1;



--
-- Name: col_seq_company_id; Type: SEQUENCE; Schema: collhbrs; Owner: houde2s
--

CREATE SEQUENCE collhbrs.col_seq_company_id
    START WITH 40000000
    INCREMENT BY 1
    MINVALUE 40000000
    MAXVALUE 49999999
    CACHE 1;


--
-- Name: col_seq_message_id; Type: SEQUENCE; Schema: collhbrs; Owner: houde2s
--

CREATE SEQUENCE collhbrs.col_seq_message_id
    START WITH 50000000
    INCREMENT BY 1
    MINVALUE 50000000
    MAXVALUE 59999999
    CACHE 1;


--
-- Name: col_seq_user_id; Type: SEQUENCE; Schema: collhbrs; Owner: houde2s
--

CREATE SEQUENCE collhbrs.col_seq_user_id
    START WITH 20000000
    INCREMENT BY 1
    MINVALUE 20000000
    MAXVALUE 29999999
    CACHE 1;

--
-- Name: col_tab_address; Type: TABLE; Schema: collhbrs; Owner: houde2s
--

CREATE TABLE collhbrs.col_tab_address (
                                          address_id bigint DEFAULT nextval('collhbrs.col_seq_address_id'::regclass) NOT NULL,
                                          postal_code character varying(5) NOT NULL,
                                          city character varying(16) NOT NULL,
                                          country character varying(16) NOT NULL,
                                          street character varying(16) NOT NULL,
                                          house_number character varying(4) NOT NULL
);



--
-- Name: col_tab_application; Type: TABLE; Schema: collhbrs; Owner: houde2s
--

CREATE TABLE collhbrs.col_tab_application (
                                              application_id bigint DEFAULT nextval('collhbrs.col_seq_application_id'::regclass) NOT NULL,
                                              user_id bigint NOT NULL,
                                              advertisement_id bigint NOT NULL,
                                              headline character varying(128) NOT NULL,
                                              text character varying(4096) NOT NULL,
                                              date date
);


--
-- Name: col_tab_company; Type: TABLE; Schema: collhbrs; Owner: houde2s
--

CREATE TABLE collhbrs.col_tab_company (
                                          company_id bigint DEFAULT nextval('collhbrs.col_seq_company_id'::regclass) NOT NULL,
                                          company_description character varying(1024) NOT NULL,
                                          homepage character varying(32),
                                          fax_number character varying(12),
                                          company_name character varying(32) NOT NULL,
                                          address_id bigint NOT NULL,
                                          mail_address character varying(32),
                                          phone_number character varying(12)
);



--
-- Name: col_tab_contact_person; Type: TABLE; Schema: collhbrs; Owner: houde2s
--

CREATE TABLE collhbrs.col_tab_contact_person (
                                                 user_id bigint NOT NULL,
                                                 company_id bigint NOT NULL,
                                                 role character varying(16)
);


--
-- Name: col_tab_job_advertisement; Type: TABLE; Schema: collhbrs; Owner: houde2s
--

CREATE TABLE collhbrs.col_tab_job_advertisement (
                                                    temporary_employment boolean NOT NULL,
                                                    type_of_employment character varying(16) NOT NULL,
                                                    working_hours numeric(2,0) DEFAULT 0 NOT NULL,
                                                    requirements character varying(1024),
                                                    address_id bigint NOT NULL,
                                                    start_of_work date,
                                                    end_of_work date,
                                                    contact_person_id bigint NOT NULL,
                                                    job_description character varying(1024),
                                                    salary integer,
                                                    job_title character varying(64) NOT NULL,
                                                    advertisement_id bigint DEFAULT nextval('collhbrs.col_seq_advertisement_id'::regclass) NOT NULL
);


--
-- Name: col_tab_message; Type: TABLE; Schema: collhbrs; Owner: houde2s
--

CREATE TABLE collhbrs.col_tab_message (
                                          message_id bigint DEFAULT nextval('collhbrs.col_seq_message_id'::regclass) NOT NULL,
                                          sender_id bigint NOT NULL,
                                          recipient_id bigint NOT NULL,
                                          content character varying NOT NULL,
                                          subject character varying NOT NULL,
                                          date date NOT NULL,
                                          read boolean DEFAULT false NOT NULL
);

--
-- Name: col_tab_settings; Type: TABLE; Schema: collhbrs; Owner: houde2s
--

CREATE TABLE collhbrs.col_tab_settings (
                                           user_id bigint NOT NULL,
                                           notification_is_enabled boolean NOT NULL
);



--
-- Name: col_tab_student; Type: TABLE; Schema: collhbrs; Owner: houde2s
--

CREATE TABLE collhbrs.col_tab_student (
                                          student_description character varying(2048),
                                          website character varying(32),
                                          graduation character varying(32),
                                          skills character varying(1024),
                                          interests character varying(512),
                                          user_id bigint NOT NULL,
                                          subject_field character varying(32)
);


--
-- Name: col_tab_user; Type: TABLE; Schema: collhbrs; Owner: houde2s
--

CREATE TABLE collhbrs.col_tab_user (
                                       user_id bigint DEFAULT nextval('collhbrs.col_seq_user_id'::regclass) NOT NULL,
                                       first_name character varying(16) NOT NULL,
                                       last_name character varying(16) NOT NULL,
                                       address_id bigint NOT NULL,
                                       date_of_birth date NOT NULL,
                                       password character varying(255) NOT NULL,
                                       phone_number character varying(12) NOT NULL,
                                       salutation character varying(10) NOT NULL,
                                       title character varying(16) NOT NULL,
                                       mail_address character varying(32) NOT NULL,
                                       type character varying(2)
);


--
-- Name: col_tab_address col_pk_address_id; Type: CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_address
    ADD CONSTRAINT col_pk_address_id PRIMARY KEY (address_id);


--
-- Name: col_tab_job_advertisement col_pk_advertisement_id; Type: CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_job_advertisement
    ADD CONSTRAINT col_pk_advertisement_id PRIMARY KEY (advertisement_id);


--
-- Name: col_tab_application col_pk_application_id; Type: CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_application
    ADD CONSTRAINT col_pk_application_id PRIMARY KEY (application_id);


--
-- Name: col_tab_company col_pk_company_id; Type: CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_company
    ADD CONSTRAINT col_pk_company_id PRIMARY KEY (company_id);


--
-- Name: col_tab_contact_person col_pk_cp_user_id; Type: CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_contact_person
    ADD CONSTRAINT col_pk_cp_user_id PRIMARY KEY (user_id);


--
-- Name: col_tab_message col_pk_message_id; Type: CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_message
    ADD CONSTRAINT col_pk_message_id PRIMARY KEY (message_id);


--
-- Name: col_tab_student col_pk_s_user_id; Type: CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_student
    ADD CONSTRAINT col_pk_s_user_id PRIMARY KEY (user_id);


--
-- Name: col_tab_settings col_pk_se_user_id; Type: CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_settings
    ADD CONSTRAINT col_pk_se_user_id PRIMARY KEY (user_id);


--
-- Name: col_tab_user col_pk_u_user_id; Type: CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_user
    ADD CONSTRAINT col_pk_u_user_id PRIMARY KEY (user_id);


--
-- Name: col_tab_application col_fk_a_advertisement_id; Type: FK CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_application
    ADD CONSTRAINT col_fk_a_advertisement_id FOREIGN KEY (advertisement_id) REFERENCES collhbrs.col_tab_job_advertisement(advertisement_id);


--
-- Name: col_tab_application col_fk_a_user_id; Type: FK CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_application
    ADD CONSTRAINT col_fk_a_user_id FOREIGN KEY (user_id) REFERENCES collhbrs.col_tab_student(user_id);


--
-- Name: col_tab_company col_fk_c_address_id; Type: FK CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_company
    ADD CONSTRAINT col_fk_c_address_id FOREIGN KEY (address_id) REFERENCES collhbrs.col_tab_address(address_id);


--
-- Name: col_tab_job_advertisement col_fk_contact_person_id; Type: FK CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_job_advertisement
    ADD CONSTRAINT col_fk_contact_person_id FOREIGN KEY (contact_person_id) REFERENCES collhbrs.col_tab_contact_person(user_id);


--
-- Name: col_tab_contact_person col_fk_cp_company_id; Type: FK CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_contact_person
    ADD CONSTRAINT col_fk_cp_company_id FOREIGN KEY (company_id) REFERENCES collhbrs.col_tab_company(company_id);


--
-- Name: col_tab_contact_person col_fk_cp_user_id; Type: FK CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_contact_person
    ADD CONSTRAINT col_fk_cp_user_id FOREIGN KEY (user_id) REFERENCES collhbrs.col_tab_user(user_id);


--
-- Name: col_tab_job_advertisement col_fk_j_address_id; Type: FK CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_job_advertisement
    ADD CONSTRAINT col_fk_j_address_id FOREIGN KEY (address_id) REFERENCES collhbrs.col_tab_address(address_id);


--
-- Name: col_tab_message col_fk_m_r_user_id; Type: FK CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_message
    ADD CONSTRAINT col_fk_m_r_user_id FOREIGN KEY (recipient_id) REFERENCES collhbrs.col_tab_user(user_id);


--
-- Name: col_tab_message col_fk_m_s_user_id; Type: FK CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_message
    ADD CONSTRAINT col_fk_m_s_user_id FOREIGN KEY (sender_id) REFERENCES collhbrs.col_tab_user(user_id);


--
-- Name: col_tab_student col_fk_s_user_id; Type: FK CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_student
    ADD CONSTRAINT col_fk_s_user_id FOREIGN KEY (user_id) REFERENCES collhbrs.col_tab_user(user_id);


--
-- Name: col_tab_settings col_fk_se_user_id; Type: FK CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_settings
    ADD CONSTRAINT col_fk_se_user_id FOREIGN KEY (user_id) REFERENCES collhbrs.col_tab_user(user_id);


--
-- Name: col_tab_user col_fk_u_address_id; Type: FK CONSTRAINT; Schema: collhbrs; Owner: houde2s
--

ALTER TABLE ONLY collhbrs.col_tab_user
    ADD CONSTRAINT col_fk_u_address_id FOREIGN KEY (address_id) REFERENCES collhbrs.col_tab_address(address_id);


--
-- PostgreSQL database dump complete
--
