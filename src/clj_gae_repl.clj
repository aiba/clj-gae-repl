(ns clj-gae-repl
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use compojure.core
        ring.util.servlet
        ring.adapter.jetty
        ring.util.response
        hiccup.core
        hiccup.page-helpers)
  (:require [compojure.route :as route]))

;----------------------------------------------------------------
; main-html
;----------------------------------------------------------------

(defn main-html []
  (html
    (doctype :html4)
    [:html
     [:head
      [:title "Clojure REPL on Google App Engine"]
      (include-css "/static/main.css")]
     [:body
      [:h1 "Clojure REPL on Google App Engine"]]]
    ))

;----------------------------------------------------------------
; Routes + Service
;----------------------------------------------------------------

(defroutes main-routes
  (GET "/" [] (main-html))
  ; this is only necessary in development mode.  in GAE deployment,
  ; the servlet mapping will automatically map /static/ files.
  (GET "/static/main.css" [] (file-response "war/static/main.css"))
  (route/not-found "Page not found"))

; for GAE:
(defservice main-routes)

; for local/REPL development:

(defmacro async [expr]
  `(.start (Thread. (fn [] ~expr))))

(defn cgr-start-jetty []
  (async (run-jetty main-routes {:port 9000})))


