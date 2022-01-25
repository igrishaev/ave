(ns ave.demo.handler.hello
  (:require
   [integrant.core :as ig]))


(defmethod ig/init-key ::*
  [_ _]
  (fn [ctx]
    {:status 200
     :headers {"content-type" "text/html"}
     :body "<h1>Hello World!</h1>"}))
