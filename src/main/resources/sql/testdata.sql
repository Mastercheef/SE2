insert into col_tab_address
values (10000000, 53757, 'Sankt Augustin', 'Deutschland', 'Hofgartenstrasse', 15);

insert into col_tab_company
values ('Nur ein Test...', default, 'https://test.com/awesome', 012398489, 'Erfolgreiche Test Firma GmbH',
        (SELECT MAX(address_id) FROM col_tab_address));

insert into col_tab_user
    values(to_date('05 Dec 2000', 'DD Mon YYYY'), 'password', '192345', (SELECT MAX(address_id) FROM col_tab_address), 'Herr', 'Professor', 'Frederick', 'Behringer', 'mail', default);