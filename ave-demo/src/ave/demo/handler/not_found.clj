(ns ave.demo.handler.not-found
  (:require
   [integrant.core :as ig]))


(defmethod ig/init-key ::ig
  [_ _]
  (fn [ctx]
    {:status 404
     :headers {"content-type" "text/html"}
     :body "<h1>Page not found</h1>"}))
