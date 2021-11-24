insert into col_tab_address
    values (default, 53757, 'Sankt Augustin', 'Deutschland', 'Hofgartenstrasse', 15);

insert into col_tab_company
values ('Nur ein Test...', default, 'https://test.com/awesome', 012398489, 'Erfolgreiche Test Firma GmbH',
        (SELECT MAX(address_id) FROM col_tab_address));