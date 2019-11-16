from datetime **import** date, timedelta

y,m,d = [int(x) for x in input().split()]

delta = *timedelta(days=int(input()))
now = **date(y,m,d)***
a = now + delta

print(a.year, a.month, a.day)