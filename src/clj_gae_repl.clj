(ns clj-gae-repl
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use compojure.core
        ring.util.servlet
        ring.adapter.jetty
        hiccup.core)
  (:require [compojure.route :as route]))

;----------------------------------------------------------------
; main-html
;----------------------------------------------------------------

(defn main-html []
  (html
    [:html
     [:head
      [:title "Title"]]
     [:body
      [:h1 "clj-gae-repl"]]]
    ))

;----------------------------------------------------------------
; Routes + Service
;----------------------------------------------------------------

(defroutes main-routes
  (GET "/" [] (main-html))
  (route/not-found "Page not found"))

; for GAE:
(defservice main-routes)

; for local/REPL development:

(defmacro async [expr]
  `(.start (Thread. (fn [] ~expr))))

(defn cgr-start-jetty []
  (async (run-jetty main-routes {:port 9000})))


