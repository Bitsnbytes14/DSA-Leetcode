# Write your MySQL query statement below
select a.name as employee
from employee as a
join employee as b
on a.managerId = b.id
where a.salary > b.salary;