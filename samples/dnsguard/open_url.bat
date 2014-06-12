@echo off

for /l %%x in (1, 1, 10000) do (
	
	start firefox
	timeout 5
	
	for /l %%x in (1, 1, 10) do (
		start http://www.randomwebsite.com/cgi-bin/random.pl
		timeout 2
	)
	
	timeout 5
	taskkill /im firefox.exe
	timeout 5
)

