-- 1) Select in select - Pacienti a počet jejich rezervací
select P.*, [Count] = (select count(*) from Booking B where B.PacientId = P.Id) from Pacient P

-- 2) Select ve from - Lidi co byly někdy na testu a vyšel pozitivní (tzn. lidi co prodělali covid)
SELECT DISTINCT Pacient.*
FROM (SELECT Id
	  FROM Booking
	  WHERE IsPositive = 1) AS PositiveGuys, Pacient
WHERE Pacient.Id = PositiveGuys.Id

-- 3) Vnořený select ve where - všichni doktoři, kteří nemají pro daný den žádnou rezervaci.
select D.* from Doctor D 
where Id not in (
	select distinct DTT.DoctorId from Booking B 
	join DoctorToTest DTT on DTT.Id = B.DoctorToTestId
	where B.StartsAt > '2020-12-29' and B.StartsAt < '2020-12-30'
)

-- 4) Group by - počet rezervací všech doktorů pro daný den, kteří mají alespoň jednu
select DTT.DoctorId, [Count] = count(DTT.DoctorId) from Booking B 
join DoctorToTest DTT on DTT.Id = B.DoctorToTestId
where B.StartsAt > '2020-12-29' and B.StartsAt < '2020-12-30'
group by DTT.DoctorId

-- 5) Množinová operace -> všechny místa v liberci a všechny místa, kde se provádí covid testy
select L.* from Location L where City like 'Liberec' 
intersect
select L.* from Location L where Id in (
	select D.LocationId from Test T 
	join DoctorToTest DTT on DTT.TestId = T.Id
	join Doctor D on DTT.DoctorId = D.Id 
	where T.Name like 'PCR' or T.Name like 'Antigen'
)

-- 6) left join - seznam všech testů a jací doktoři je provádí (pro počet by se mohl použít group by obdobně jako u bodu 4)
-- Left join používám protože chci vidět i testy, které nikdo neposkytuje
select T.Id, T.Name as 'TestName', T.Description, T.Price, T.Length, D.Name, D.* from Test T 
left join DoctorToTest DTT on DTT.TestId = T.Id
left join Doctor D on D.Id = DTT.DoctorId
