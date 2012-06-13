DELETE FROM joinedLawyerCounty WHERE lawyer_id LIKE 1;
DELETE FROM joinedLawyerCounty WHERE lawyer_id LIKE 2;
DELETE FROM joinedLawyerCounty WHERE lawyer_id LIKE 3;
DELETE FROM joinedLawyerCounty WHERE lawyer_id LIKE 4;

DELETE FROM joinedLawyerArea WHERE lawyer_id LIKE 1;
DELETE FROM joinedLawyerArea WHERE lawyer_id LIKE 2;
DELETE FROM joinedLawyerArea WHERE lawyer_id LIKE 3;
DELETE FROM joinedLawyerArea WHERE lawyer_id LIKE 4;

DELETE FROM Lawyer WHERE id LIKE 1;
DELETE FROM Lawyer WHERE id LIKE 2;
DELETE FROM Lawyer WHERE id LIKE 3;
DELETE FROM Lawyer WHERE id LIKE 4;

DELETE FROM County WHERE id LIKE 1;
DELETE FROM County WHERE id LIKE 2;
DELETE FROM County WHERE id LIKE 3;

DELETE FROM LegalArea WHERE id LIKE 1;
DELETE FROM LegalArea WHERE id LIKE 2;
DELETE FROM LegalArea WHERE id LIKE 3;
DELETE FROM LegalArea WHERE id LIKE 4;
DELETE FROM LegalArea WHERE id LIKE 5;

INSERT INTO Lawyer (id, name, email, phone, address, standing, insurance, barnumber, presentation) VALUES ('1','lawyer 1','lawyer.1@email.com','111-111-1111','111 foo st','good','yes','007', 'true');
INSERT INTO Lawyer (id, name, email, phone, address, standing, insurance, barnumber, presentation) VALUES ('2','lawyer 2','lawyer.2@email.com','222-222-2222','222 foo st','good','yes','002', 'false');
INSERT INTO Lawyer (id, name, email, phone, address, standing, insurance, barnumber, presentation) VALUES ('3','lawyer 3','lawyer.3@email.com','333-333-3333','333 foo st','good','yes','003','false');
INSERT INTO Lawyer (id, name, email, phone, address, standing, insurance, barnumber, presentation) VALUES ('4','lawyer 4','lawyer.4@email.com','444-444-4444','444 foo st','good','yes','004', 'true');

INSERT INTO County (id, name) VALUES ('1', 'county1');
INSERT INTO County (id, name) VALUES ('2', 'county2');
INSERT INTO County (id, name) VALUES ('3', 'county3');

INSERT INTO LegalArea (id, name) VALUES ('1', 'area1');
INSERT INTO LegalArea (id, name) VALUES ('2', 'area2');
INSERT INTO LegalArea (id, name) VALUES ('3', 'area3');
INSERT INTO LegalArea (id, name) VALUES ('4', 'area4');
INSERT INTO LegalArea (id, name) VALUES ('5', 'area5');


INSERT INTO joinedLawyerCounty (lawyer_id, county_id) VALUES ('1','1');
INSERT INTO joinedLawyerCounty (lawyer_id, county_id) VALUES ('1','2');
INSERT INTO joinedLawyerCounty (lawyer_id, county_id) VALUES ('1','3');
INSERT INTO joinedLawyerCounty (lawyer_id, county_id) VALUES ('2','2');
INSERT INTO joinedLawyerCounty (lawyer_id, county_id) VALUES ('2','3');
INSERT INTO joinedLawyerCounty (lawyer_id, county_id) VALUES ('3','1');
INSERT INTO joinedLawyerCounty (lawyer_id, county_id) VALUES ('3','2');
INSERT INTO joinedLawyerCounty (lawyer_id, county_id) VALUES ('4','1');
INSERT INTO joinedLawyerCounty (lawyer_id, county_id) VALUES ('4','3');

INSERT INTO joinedLawyerArea (lawyer_id, area_id) VALUES ('1','1');
INSERT INTO joinedLawyerArea (lawyer_id, area_id) VALUES ('1','2');
INSERT INTO joinedLawyerArea (lawyer_id, area_id) VALUES ('1','3');
INSERT INTO joinedLawyerArea (lawyer_id, area_id) VALUES ('1','4');
INSERT INTO joinedLawyerArea (lawyer_id, area_id) VALUES ('1','5');
INSERT INTO joinedLawyerArea (lawyer_id, area_id) VALUES ('2','1');
INSERT INTO joinedLawyerArea (lawyer_id, area_id) VALUES ('2','2');
INSERT INTO joinedLawyerArea (lawyer_id, area_id) VALUES ('3','2');
INSERT INTO joinedLawyerArea (lawyer_id, area_id) VALUES ('3','3');
INSERT INTO joinedLawyerArea (lawyer_id, area_id) VALUES ('4','4');
INSERT INTO joinedLawyerArea (lawyer_id, area_id) VALUES ('4','5');