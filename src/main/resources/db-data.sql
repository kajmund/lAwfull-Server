SET foreign_key_checks = 0;
insert into user (eMail, password, userName, userRole, activeLegalResearch_id) values("erik@2xper.se", "d7b23beeb8000f3cf11aa4ae8dd55d36e0552648", "erik", 4, 1);
insert into legalResearch (description, title, user_id) values("Standard utredning", "Standard", 1);
SET foreign_key_checks = 1;