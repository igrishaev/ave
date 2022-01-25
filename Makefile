
sub-install:
	lein sub install

repl-core:
	lein sub -s ave-core with-profile +test repl


repl-reitit:
	lein sub -s ave-router-reitit with-profile +test repl
