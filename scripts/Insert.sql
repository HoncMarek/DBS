INSERT INTO Pacient(Name, Surname, PersonalIdentifier)
VALUES
    ('František', 'Novák', '001017/3834'),
    ('Pepa', 'Novotný', '001017/3845'),
    ('Marek', 'Nový', '001017/3856'),
    ('Karel', 'Modrý', '001017/3867'),
	('Martin', 'Červený', '001017/3878'),
	('Bruno', 'Zelený', '001017/3889'),
	('Pavel', 'Fialový', '001017/4934'),
	('Jirka', 'Béžový', '001017/6034'),
	('Jaroslav', 'Černý', '001017/7134'),
	('David', 'Bílý', '001017/8234')

INSERT INTO [Location](Name, City, Street, OpenedFrom, OpenedTo)
VALUES 
	('KNL', 'Liberec', 'Nádražní 5', '7:00', '13:00'),
	('Soukromá klinika','Liberec', 'Husova 5', '8:00', '13:00'),
	('Veøejná klinika','Liberec', 'Zeyerova 5', '9:00', '15:00'),
	('nemjbc','Jablonec nad Nisou', 'Nádražní 69', '5:00', '10:00'),
	('Soukromá klinika JBC','Jablonec nad Nisou', 'Nová 7', '7:00', '13:00'),
	('Poboèka KNL - JBC','Jablonec nad Nisou', 'Liberecká 7', '11:00', '13:00'),
	('Poboèka KNL - Turnov','Turnov', 'Nádražní 7', '4:00', '8:00'),
	('První turnovská nemocnice','Turnov', 'Liberecká 3', '14:00', '20:00'),
	('nemtur','Turnov', 'Stará 34', '7:00', '13:00'),
	('nemcl','Česká Lípa', 'Nádražní 16', '7:00', '13:00')

INSERT INTO Doctor(Name, Surname, Title, LocationId)
VALUES 
	-- Ten select není sice 100%, protože když budou mít 2 lokace stejná jména tak to vezme první, ale jedná se jenom o testovací data.
	('Marek', 'Honc', 'Doc', (Select top 1 Id from [Location] where Name Like 'KNL')),
	('Bruno', 'Pfohl', 'Mgr', (Select top 1 Id from [Location] where Name Like 'KNL')),
	('Martin', 'Hájek', 'Mgr', (Select top 1 Id from [Location] where Name Like 'nemtur')),
	('Natálie', 'Novotná', 'Mgr', (Select Id from [Location] where Name Like 'Soukromá klinika')),
	('Karolína', 'Hustá', 'Mgr', (Select top 1 Id from [Location] where Name Like 'Veøejná klinika')),
	('Lucie', 'Drsná', 'Doc', (Select top 1 Id from [Location] where Name Like 'nemjbc')),
	('Frajer', 'Sprajer', 'Mgr', (Select top 1 Id from [Location] where Name Like 'Soukromá klinika JBC')),
	('Eva', 'Evička', 'Mgr', (Select top 1 Id from [Location] where Name Like 'Poboèka KNL - JBC')),
	('Pepa', 'Doktor', 'Mgr', (Select top 1 Id from [Location] where Name Like 'nemcl')),
	('Ahmed', 'Arthur', 'Mgr', (Select top 1 Id from [Location] where Name Like 'KNL'))

INSERT INTO Test(Name, Description, Price, Length)
VALUES 
	-- Nejsem doktor - neruším za pøesnost údajù ale pro testování postaèující.
	('PCR', 'Covid - test', 2256, 10),
	('Antigen', 'Covid - test - antigenní', 0, 10),
	('Alzheimer', null, 1500, 30),
	('Krevní test', 'Odběr krve', 250, 5),
	('Těhotenský test', null, 500, 10),
	('HIV', null, 1500, 45),
	('Wegener', null, 2000, 60),
	('Rakovina', 'Vyšetøení podle podezdøení', 5000, 120),
	('Žlučové kameny', null, 900, 15),
	('Roztroušená skleróza', null, 15000, 10)

INSERT INTO DoctorToTest(TestId, DoctorId)
VALUES
-- Ten select není sice 100%, protože když podmínce vyhoví více záznamù vezme se první, ale jedná se jenom o testovací data.
	((Select top 1 Id from Test where Name Like 'PCR'), (Select top 1 Id from Doctor where SurName Like 'Honc')),
	((Select top 1 Id from Test where Name Like 'Antigen'), (Select top 1 Id from Doctor where SurName Like 'Honc')),
	((Select top 1 Id from Test where Name Like 'Alzheimer'), (Select top 1 Id from Doctor where SurName Like 'Pfohl')),
	((Select top 1 Id from Test where Name Like 'Krevní test'), (Select top 1 Id from Doctor where SurName Like 'Pfohl')),
	((Select top 1 Id from Test where Name Like 'PCR'), (Select top 1 Id from Doctor where SurName Like 'Hájek')),
	((Select top 1 Id from Test where Name Like 'Alzheimer'), (Select top 1 Id from Doctor where SurName Like 'Hájek')),
	((Select top 1 Id from Test where Name Like 'Roztroušená skleróza'), (Select top 1 Id from Doctor where SurName Like 'Novotná')),
	((Select top 1 Id from Test where Name Like 'HIV'), (Select top 1 Id from Doctor where SurName Like 'Hustá')),
	((Select top 1 Id from Test where Name Like 'Wegener'), (Select top 1 Id from Doctor where SurName Like 'Drsná')),
	((Select top 1 Id from Test where Name Like 'Rakovina'), (Select top 1 Id from Doctor where SurName Like 'Sprajer'))

Insert INTO Booking(DoctorToTestId, PacientId, StartsAt, Paid, IsPositive)
VALUES
	((Select top 1 Id from DoctorToTest where TestId = (Select top 1 Id from Test where Name Like 'PCR') and DoctorId = (Select top 1 Id from Doctor where SurName Like 'Honc')),
		(Select top 1 Id from Pacient where PersonalIdentifier like '001017/3834'), '2020-12-29 11:40:00', 0, 0),
	((Select top 1 Id from DoctorToTest where TestId = (Select top 1 Id from Test where Name Like 'Antigen') and DoctorId = (Select top 1 Id from Doctor where SurName Like 'Honc')),
		(Select top 1 Id from Pacient where PersonalIdentifier like '001017/3834'), '2020-12-29 11:50:00', 1, 0),
	((Select top 1 Id from DoctorToTest where TestId = (Select top 1 Id from Test where Name Like 'Alzheimer') and DoctorId = (Select top 1 Id from Doctor where SurName Like 'Pfohl')),
		(Select top 1 Id from Pacient where PersonalIdentifier like '001017/3845'), '2020-12-29 10:30:00', 1, 0),
	((Select top 1 Id from DoctorToTest where TestId = (Select top 1 Id from Test where Name Like 'Krevní test') and DoctorId = (Select top 1 Id from Doctor where SurName Like 'Pfohl')),
		(Select top 1 Id from Pacient where PersonalIdentifier like '001017/3856'), '2020-12-29 11:00:00', 0, 1),
	((Select top 1 Id from DoctorToTest where TestId = (Select top 1 Id from Test where Name Like 'PCR') and DoctorId = (Select top 1 Id from Doctor where SurName Like 'Hájek')),
		(Select top 1 Id from Pacient where PersonalIdentifier like '001017/3867'), '2020-12-29 9:40:00', 0, 1),
	((Select top 1 Id from DoctorToTest where TestId = (Select top 1 Id from Test where Name Like 'Alzheimer') and DoctorId = (Select top 1 Id from Doctor where SurName Like 'Hájek')),
		(Select top 1 Id from Pacient where PersonalIdentifier like '001017/3878'), '2020-12-29 9:50:00', 1, 1),
	((Select top 1 Id from DoctorToTest where TestId = (Select top 1 Id from Test where Name Like 'PCR') and DoctorId = (Select top 1 Id from Doctor where SurName Like 'Honc')),
		(Select top 1 Id from Pacient where PersonalIdentifier like '001017/3889'), '2020-12-30 8:30:00', 0, 0),
	((Select top 1 Id from DoctorToTest where TestId = (Select top 1 Id from Test where Name Like 'PCR') and DoctorId = (Select top 1 Id from Doctor where SurName Like 'Honc')),
		(Select top 1 Id from Pacient where PersonalIdentifier like '001017/4934'), '2020-12-29 8:40:00', 1, 1),
	((Select top 1 Id from DoctorToTest where TestId = (Select top 1 Id from Test where Name Like 'Roztroušená skleróza') and DoctorId = (Select top 1 Id from Doctor where SurName Like 'Novotná')),
		(Select top 1 Id from Pacient where PersonalIdentifier like '001017/6034'), '2020-12-29 8:00:00', 0, 0),
	((Select top 1 Id from DoctorToTest where TestId = (Select top 1 Id from Test where Name Like 'Rakovina') and DoctorId = (Select top 1 Id from Doctor where SurName Like 'Sprajer')),
		(Select top 1 Id from Pacient where PersonalIdentifier like '001017/7134'), '2020-12-29 7:40:00', 0, 1)
