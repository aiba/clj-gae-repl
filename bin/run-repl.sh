#!/bin/bash -e

CP=""

# add clojure source code
CP="$CP:./src"

# add lein deps
for f in ./war/WEB-INF/lib/*.jar; do
  CP=$CP:$f
done

# add jline for REPL
CP=$CP:/aidb/lib/jline-0.9.94/jline-0.9.94.jar

# run the REPL
java -cp $CP jline.ConsoleRunner clojure.main

# Note: after repl is running, do:
#
#   (use 'foo)
#
#   ; after changing clojure source code in src/ directory and saving the file:
#
#   (use 'foo :reload)
#




