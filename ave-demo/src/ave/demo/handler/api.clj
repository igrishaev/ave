(ns ave.demo.handler.api
  (:require
   [integrant.core :as ig]))


(defmethod ig/init-key ::*
  [_ _]
  (fn [ctx]
    {:status 200
     :body {:foo 42}}))
