SET foreign_key_checks = 0;
drop table if exists caseLaw;
drop table if exists caseLawDocumentPart;

drop table if exists caseLawSubject;
drop table if exists caseLaw_law;
drop table if exists consolidation ;
drop table if exists documentBookMark ;
drop table if exists law;
drop table if exists lawDocumentPart;
drop table if exists legalResearch;
drop table if exists legalResearch_documentBookMark;
drop table if exists proposition ;
drop table if exists textElement;
drop table if exists user;
drop table if exists hibernate_sequences;
drop table if exists law_proposition;
drop table if exists lawdocumentpart_lawdocumentpart;
drop table if exists law_consolidation;

drop table if exists lawdocumentpart_consolidation;
drop table if exists caselawdocumentpart_lawdocumentpart;
drop table if exists lawdocumentpart_proposition;

drop table if exists documentpart;

SET foreign_key_checks = 1;