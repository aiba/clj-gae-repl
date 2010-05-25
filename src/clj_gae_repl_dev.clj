(ns clj-gae-repl-dev
  (:use clj-gae-repl
        ring.adapter.jetty))

; for local/REPL development:

(defmacro async [expr]
  `(.start (Thread. (fn [] ~expr))))

(defn cgr-start-jetty []
  (async (run-jetty main-routes {:port 9000})))


