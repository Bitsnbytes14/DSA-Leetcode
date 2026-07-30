# Write your MySQL query statement below
select unique_id,name
from employees as e
left join employeeuni as a
on e.id = a.id
