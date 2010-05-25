(ns clj-gae-repl
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use compojure.core
        ring.util.servlet
        ring.util.response
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers)
  (:require [compojure.route :as route]))

;----------------------------------------------------------------
; main-html
;----------------------------------------------------------------

(defn render-get-main [expr]
  (html
    (doctype :html4)
    [:html
     [:head
      [:title "Clojure REPL on Google App Engine"]
      (include-css "/static/main.css")]
     [:body
      [:h1 "Clojure REPL"]
      (form-to [:get "/"]
        [:p {:id "prompt"} "Input a clojure expression:"]
        (text-area "expr"
                   (or expr "(+ 1 1)"))
        (submit-button "Submit"))
      (if (not (nil? expr))
        [:div
         [:p "Result:"]
         [:br] [:hr] [:br]
         [:pre (str (eval (read-string expr)))]]
        [:br])
      ]]))

;----------------------------------------------------------------
; Routes + Service
;----------------------------------------------------------------

(defroutes main-routes
  (GET "/" [expr] (render-get-main expr))
  ; this is only necessary in development mode.  in GAE deployment,
  ; the servlet mapping will automatically map /static/ files.
  (GET "/static/main.css" [] (file-response "war/static/main.css"))
  (route/not-found "Page not found"))

; for GAE:
(defservice main-routes)

