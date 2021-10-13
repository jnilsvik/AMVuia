Select toolName, toolCategory
from Tool;

select toolName 
from Tool as t, Booking as b
where t.toolID = b.toolID and not b.startDate < CURRENT_DATE() < b.endDate;

select firstName + ' ' +  lastName as 'Name', count(*) as 'number of borrows'
from AMVUser as u, Booking as b 
where u.userID = b.userID
group by b.userID;

select toolName, startDate
from Tool as t, Booking as b
where t.toolID = b.toolID and b.userID = (select userID from Booking as b group by b.userID order by count(*) desc limit 1)
Order by startDate DESC;
