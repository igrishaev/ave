
sub-install:
	lein sub install

repl-ave-core:
	lein sub -s ave-core with-profile +test repl
