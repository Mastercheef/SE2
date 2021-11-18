-- Drop Section
-- ____________

drop view col_view_student_profile;

-- View Section
-- ____________

create view col_view_student_profile as
	select u.salutation, u.title, u.first_name, u.surname, u.mail_address, u.phone_number,
       		a.postal_code, a.city, a.countrie, a.street, a.house_number
	from col_tab_user u inner join col_tab_address a on u.address_id = a.address_id;

create view col_view_job_advertisement as
	select 
	from col_tab_job_advertisement a inner join col_tab_contact_person_id on 

create view col_view_company as
	select *
	from col_tab_