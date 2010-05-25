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
      [:title "Evaluate a Clojure Expression"]
      (include-css "/static/main.css")]
     [:body
      [:h1 "Evaluate a Clojure Expression"]
      (form-to [:get "/"]
        (text-area "expr" (or expr "(+ 1 1)"))
        [:br]
        (submit-button "Submit"))
      (if (not (nil? expr))
        [:div
         [:div {:id "result-label"} "Result:"]
         [:div {:id "result"}
          (str (eval (read-string expr)))]]
        "")
      [:div {:id "footer"}
       (link-to "http://github.com/aiba/clj-gae-repl"
                "Source code to this app")]]]))

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

