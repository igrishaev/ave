(ns ave.demo.handler.api
  (:require
   [integrant.core :as ig]))


(defmethod ig/init-key ::ig
  [_ _]
  (fn [ctx]
    {:status 200
     :body {:foo 42}}))
