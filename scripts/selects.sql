-- 1) Select in select - Pacienti a počet jejich rezervací
select Name, Surname, BookingCount = (select count(*) from Booking B where B.PacientId = P.Id) from Pacient P

-- 2) Select ve from - Lidi co byly někdy na testu a vyšel pozitivní (tzn. lidi co prodělali covid)
SELECT DISTINCT Name, PositiveGuys.Id
FROM (SELECT Id
	  FROM Booking
	  WHERE IsPositive = 1) AS PositiveGuys, Pacient
WHERE Pacient.Id = PositiveGuys.Id

-- 3) Vnořený select ve where - všichni doktoři, kteří nemají pro daný den žádnou rezervaci.
select Id, D.Name, D.Surname from Doctor D 
where Id not in (
	select DTT.DoctorId from Booking B 
	join DoctorToTest DTT on DTT.Id = B.DoctorToTestId
	where B.StartsAt > '2020-12-29' and B.StartsAt < '2020-12-30'
	group by DTT.DoctorId
)

-- 4) Group by - počet rezervací všech doktorů pro daný den, kteří mají alespoň jednu
select DTT.DoctorId, count(DTT.DoctorId) from Booking B 
join DoctorToTest DTT on DTT.Id = B.DoctorToTestId
where B.StartsAt > '2020-12-29' and B.StartsAt < '2020-12-30'
group by DTT.DoctorId

-- 5) Množinová operace -> všechny místa v liberci a všechny místa, kde se provádí covid testy
select L.Name, L.City, L.Street from Location L where City like 'Liberec' 
intersect
select L.Name, L.City, L.Street from Location L where Id in (
	select D.LocationId from Test T 
	join DoctorToTest DTT on DTT.TestId = T.Id
	join Doctor D on DTT.DoctorId = D.Id 
	where T.Name like 'PCR' or T.Name like 'Antigen'
)

-- 6) left join - seznam všech testů a jací doktoři je provádí (pro počet by se mohl použít group by obdobně jako u bodu 4)
-- Left join používám protože chci vidět i testy, které nikdo neposkytuje
select T.Name as 'Název testu', D.Name, D.Surname from Test T 
left join DoctorToTest DTT on DTT.TestId = T.Id
left join Doctor D on D.Id = DTT.DoctorId
