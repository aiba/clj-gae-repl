(ns clj-gae-repl
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use compojure.core
        ring.util.servlet)
  (:require [compojure.route :as route]))

;----------------------------------------------------------------
; main-html
;----------------------------------------------------------------

(defn main-html []
  (str "<html><body><h1>clj-gae-repl</h1></body></html>"))

;----------------------------------------------------------------
; Routes + Service
;----------------------------------------------------------------

(defroutes example
  (GET "/" [] (main-html))
  (route/not-found "Page not found"))

(defservice example)

