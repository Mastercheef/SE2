-- fbehri2s, 18.11.2021

-- Drop Section
-- ____________

drop view col_view_student_profile;

-- View Section
-- ____________

create view col_view_student_profile as
	select u.salutation, u.title, u.first_name, u.surname, u.mail_address, u.phone_number,
       		a.postal_code, a.city, a.countrie, a.street, a.house_number
	from collhbrs.col_tab_user u
	    inner join collhbrs.col_tab_address a on u.address_id = a.address_id
        inner join collhbrs.col_tab_student s on u.user_id = s.user_id;

